package julielerche.capstone.converters;

import julielerche.capstone.dynamodb.models.Asset;
import julielerche.capstone.dynamodb.models.AssetFromTable;
import julielerche.capstone.dynamodb.models.AssetType;
import julielerche.capstone.dynamodb.models.Encounter;
import julielerche.capstone.models.AssetModel;
import julielerche.capstone.models.EncounterModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class EncounterToModelConverterTest {
    private EncounterToModelConverter converter;

    @BeforeEach
    void setUp() {
        converter = new EncounterToModelConverter();
    }

    @Test
    public void encounterToModel_givenAsset_returnsCorrectInfo() {
        //given
        String assetType = "MONSTER";
        Integer assetId = 1;
        String name = "Dust Bunny";
        String description = "easy";
        Integer healthOrCost = 10;

        Asset expectedAsset = new Asset();
        expectedAsset.setAssetId(assetId);
        expectedAsset.setAssetType(AssetType.valueOf(assetType));
        expectedAsset.setName(name);
        expectedAsset.setDescription(description);
        expectedAsset.setHealthOrCost(healthOrCost);

        Encounter startingEncounter = new Encounter();
        startingEncounter.setUserId("one");
        startingEncounter.setMonsterList(List.of(expectedAsset));

        //when
        EncounterModel result = converter.encounterToModel(startingEncounter);

        //then
        assertEquals("one", result.getUserId());
        assertTrue(result.getMonsterList().contains(expectedAsset));
    }
}
