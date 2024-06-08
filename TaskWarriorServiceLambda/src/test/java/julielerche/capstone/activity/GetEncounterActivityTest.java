package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.GetEncounterRequest;
import julielerche.capstone.activity.results.GetEncounterResult;
import julielerche.capstone.converters.AssetToOtherTypesConverter;
import julielerche.capstone.dynamodb.EncounterDao;
import julielerche.capstone.dynamodb.models.AssetFromTable;
import julielerche.capstone.dynamodb.models.Encounter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.AssertJUnit.assertEquals;


public class GetEncounterActivityTest {
    @Mock
    private EncounterDao encounterDao;

    private GetEncounterActivity getEncounterActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        getEncounterActivity = new GetEncounterActivity(encounterDao);
    }

    @Test
    public void handleRequest_givenUserId_returnsCorrectEncounter() {
        //given
        AssetFromTable asset = new AssetFromTable();
        asset.setName("Dust Bunny");
        asset.setAssetId(1);
        asset.setAssetType("MONSTER");
        asset.setDescription("easy");
        asset.setHealthOrCost(10);
        Encounter encounter = new Encounter();
        encounter.setUserId("one");
        encounter.setMonsterList(List.of(new AssetToOtherTypesConverter()
                .convertAssetToAssigned(asset)));
        when(encounterDao.loadEncounter("one")).thenReturn(encounter);

        GetEncounterRequest request = GetEncounterRequest.builder()
                .withUserId("one")
                .build();

        //when
        GetEncounterResult result = getEncounterActivity.handleRequest(request);

        //then

        assertEquals("one", result.getEncounter().getUserId());
        assertEquals(result.getEncounter().getMonsterList().get(0).getName(), asset.getName());
    }
}
