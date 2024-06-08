package julielerche.capstone.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import julielerche.capstone.dynamodb.models.Asset;
import julielerche.capstone.dynamodb.models.AssetType;
import julielerche.capstone.dynamodb.models.Encounter;
import julielerche.capstone.dynamodb.models.Monster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.AssertJUnit.assertEquals;

public class EncounterDaoTest {
    @Mock
    private DynamoDBMapper mapper;

    @InjectMocks
    private EncounterDao encounterDao;

    @BeforeEach
    void setUp() {
        openMocks(this);
        encounterDao = new EncounterDao(mapper);
    }

    @Test
    public void saveEncounter_givenNormalEncounter_savesToTable() {
        //given
        String assetType = "MONSTER";
        Integer assetId = 1;
        String name = "Dust Bunny";
        String description = "easy";
        Integer healthOrCost = 10;

        Asset monster = new Monster();
        monster.setAssetType(AssetType.valueOf(assetType));
        monster.setAssetId(assetId);
        monster.setName(name);
        monster.setDescription(description);
        monster.setHealthOrCost(healthOrCost);

        Encounter encounter = new Encounter();
        encounter.setUserId("one");
        encounter.setMonsterList(List.of(monster));

        //when
        encounterDao.saveEncounter(encounter);

        //then
        verify(mapper, times(1)).save(any());
    }

    @Test
    public void loadEncounter_givenUserId_loadsFromTable() {
        //given
        String assetType = "MONSTER";
        Integer assetId = 1;
        String name = "Dust Bunny";
        String description = "easy";
        Integer healthOrCost = 10;

        Asset monster = new Monster();
        monster.setAssetType(AssetType.valueOf(assetType));
        monster.setAssetId(assetId);
        monster.setName(name);
        monster.setDescription(description);
        monster.setHealthOrCost(healthOrCost);

        Encounter encounter = new Encounter();
        encounter.setUserId("one");
        encounter.setMonsterList(List.of(monster));
        when(mapper.load(Encounter.class, "one")).thenReturn(encounter);

        //when
        Encounter result = encounterDao.loadEncounter("one");

        //then
        verify(mapper, times(1)).load(Encounter.class, "one");
        assertEquals(encounter, result);
    }
}