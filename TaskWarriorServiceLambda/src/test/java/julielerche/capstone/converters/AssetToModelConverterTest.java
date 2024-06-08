package julielerche.capstone.converters;

import julielerche.capstone.dynamodb.models.Asset;
import julielerche.capstone.dynamodb.models.AssetType;
import julielerche.capstone.models.AssetModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.testng.AssertJUnit.assertEquals;

public class AssetToModelConverterTest {
    private AssetToModelConverter converter;

    @BeforeEach
    void setUp() {
        converter = new AssetToModelConverter();
    }

    @Test
    public void assetToModel_givenAsset_returnsCorrectInfo() {
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

        AssetModel model = new AssetModel(AssetType.POTION, assetId, name, description);
        model.setCost(10);

        AssetModel result = converter.assetToModel(expectedAsset);
        assertEquals(result.getAssetId(), model.getAssetId());
        assertEquals(result.getAssetId(), model.getAssetId());
    }
}
