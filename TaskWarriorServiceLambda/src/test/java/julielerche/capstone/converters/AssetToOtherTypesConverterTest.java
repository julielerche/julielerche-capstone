package julielerche.capstone.converters;

import julielerche.capstone.dynamodb.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.testng.AssertJUnit.assertEquals;

public class AssetToOtherTypesConverterTest {
    private AssetToOtherTypesConverter converter;

    @BeforeEach
    void setUp() {
        converter = new AssetToOtherTypesConverter();
    }

    @Test
    public void convertAssetToAssigned_givenTableAsset_returnsPotionAsset() {
        String assetType = "POTION";
        Integer assetId = 1;
        String name = "Health Potion";
        String description = "health + 10";
        Integer healthOrCost = 10;

        AssetFromTable tableAsset = new AssetFromTable();
        tableAsset.setAssetType(assetType);
        tableAsset.setAssetId(assetId);
        tableAsset.setName(name);
        tableAsset.setDescription(description);
        tableAsset.setHealthOrCost(healthOrCost);

        Asset result = converter.convertAssetToAssigned(tableAsset);

        assertEquals(result.getClass(), Potion.class);
        assertEquals(result.getCost(), healthOrCost);
        assertEquals(result.getDescription(), description);
        assertEquals(result.getAssetId(), assetId);
        assertEquals(result.getName(), name);
    }

    @Test
    public void convertAssetToAssigned_givenTableAsset_returnsItemAsset() {
        String assetType = "ITEM";
        Integer assetId = 1;
        String name = "Health Potion";
        String description = "health + 10";
        Integer healthOrCost = 10;

        AssetFromTable tableAsset = new AssetFromTable();
        tableAsset.setAssetType(assetType);
        tableAsset.setAssetId(assetId);
        tableAsset.setName(name);
        tableAsset.setDescription(description);
        tableAsset.setHealthOrCost(healthOrCost);

        Asset result = converter.convertAssetToAssigned(tableAsset);

        assertEquals(result.getClass(), Item.class);
        assertEquals(result.getCost(), healthOrCost);
        assertEquals(result.getDescription(), description);
        assertEquals(result.getAssetId(), assetId);
        assertEquals(result.getName(), name);
    }

    @Test
    public void convertAssetToAssigned_givenTableAsset_returnsMonsterAsset() {
        String assetType = "MONSTER";
        Integer assetId = 1;
        String name = "Dust Bunny";
        String description = "easy";
        Integer healthOrCost = 10;

        AssetFromTable tableAsset = new AssetFromTable();
        tableAsset.setAssetType(assetType);
        tableAsset.setAssetId(assetId);
        tableAsset.setName(name);
        tableAsset.setDescription(description);
        tableAsset.setHealthOrCost(healthOrCost);

        Asset result = converter.convertAssetToAssigned(tableAsset);

        assertEquals(result.getClass(), Monster.class);
        assertEquals(result.getStartingHealth(), healthOrCost);
        assertEquals(result.getCurrentHealth(), healthOrCost);
        assertEquals(result.getDescription(), description);
        assertEquals(result.getAssetId(), assetId);
        assertEquals(result.getName(), name);
    }

    @Test
    public void assetToTableAsset_givenAsset_returnsTableAsset(){
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

        //when
        AssetFromTable result = converter.assetToTableAsset(expectedAsset);

        //then
        assertEquals(result.getAssetType(), assetType);
        assertEquals(result.getHealthOrCost(), healthOrCost);
        assertEquals(result.getDescription(), description);
        assertEquals(result.getAssetId(), assetId);
        assertEquals(result.getName(), name);
    }
}
