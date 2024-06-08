package julielerche.capstone.converters;

import julielerche.capstone.dynamodb.models.Asset;
import julielerche.capstone.dynamodb.models.AssetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

public class MonsterListConverterTest {
    private MonsterListConverter monsterListConverter;

    @BeforeEach
    void setUp() {
        monsterListConverter = new MonsterListConverter();
    }

    @Test
    public void convert_assetToJSON_correctInformation() {
        String assetType = "POTION";
        Integer assetId = 1;
        String name = "Health Potion";
        String description = "health + 10";
        Integer healthOrCost = 10;
        Asset expectedAsset = new Asset();
        expectedAsset.setAssetId(assetId);
        expectedAsset.setAssetType(AssetType.valueOf(assetType));
        expectedAsset.setName(name);
        expectedAsset.setDescription(description);
        expectedAsset.setHealthOrCost(healthOrCost);
        List<Asset> assetList = List.of(expectedAsset);
        String expectedString = "[{\"assetType\":\"POTION\",\"assetId\":1,\"name\":\"Health Potion\",\"desc" +
                "ription\":\"health + 10\",\"healthOrCost\":10,\"attack" +
                "Power\":null,\"cost\":null,\"startingHealth\":null,\"currentHealth\":null}]";

        String result = monsterListConverter.convert(assetList);
        assertEquals(expectedString, result);
    }

    @Test
    public void unconvert_JSONtoAsset_hasCorrectInformation() {

        String assetType = "POTION";
        Integer assetId = 1;
        String name = "Health Potion";
        String description = "health + 10";
        Integer healthOrCost = 10;
        Asset expectedAsset = new Asset();
        expectedAsset.setAssetId(assetId);
        expectedAsset.setAssetType(AssetType.valueOf(assetType));
        expectedAsset.setName(name);
        expectedAsset.setDescription(description);
        expectedAsset.setHealthOrCost(healthOrCost);
        List<Asset> assetList = List.of(expectedAsset);
        String inputString = "[{\"assetType\":\"POTION\",\"assetId\":1,\"name\":\"Health Potion\",\"desc" +
                "ription\":\"health + 10\",\"healthOrCost\":10,\"attack" +
                "Power\":null,\"cost\":null,\"startingHealth\":null,\"currentHealth\":null}]";

        List<Asset> result = monsterListConverter.unconvert(inputString);
        assertEquals(assetList, result);
    }
}
