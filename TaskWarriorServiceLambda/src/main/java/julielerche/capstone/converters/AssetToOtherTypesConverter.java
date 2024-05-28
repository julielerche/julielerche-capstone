package julielerche.capstone.converters;

import julielerche.capstone.dynamodb.models.Asset;
import julielerche.capstone.dynamodb.models.AssetFromTable;
import julielerche.capstone.dynamodb.models.AssetType;
import julielerche.capstone.dynamodb.models.Item;
import julielerche.capstone.dynamodb.models.Monster;
import julielerche.capstone.dynamodb.models.Potion;

public class AssetToOtherTypesConverter {

    /**
     * Converts a Asset to a its correct asset representation.
     * @param tableAsset the asset to convert into the other type
     * @return Asset the correct typed asset to return
     */
    public Asset convertAssetToAssigned(AssetFromTable tableAsset) {
        AssetType type = tableAsset.getAssetType();

        switch (type) {
            case POTION:
                Asset potion = new Potion();
                potion.setAssetId(tableAsset.getAssetId());
                potion.setName(tableAsset.getName());
                potion.setDescription(tableAsset.getDescription());
                ((Potion) potion).setCost(tableAsset.getHealthOrCost());
                return potion;
            case ITEM:
                Asset item = new Item();
                item.setName(tableAsset.getName());
                item.setAssetId(tableAsset.getAssetId());
                item.setDescription(tableAsset.getDescription());
                ((Item) item).setCost(tableAsset.getHealthOrCost());
                return item;
            case MONSTER:
                Asset monster = new Monster();
                monster.setAssetId(tableAsset.getAssetId());
                monster.setName(tableAsset.getName());
                monster.setDescription(tableAsset.getDescription());
                ((Monster) monster).setStartingHealth(tableAsset.getHealthOrCost());
                ((Monster) monster).setCurrentHealth(tableAsset.getHealthOrCost());
                ((Monster) monster).setAttackPower(tableAsset.getAttackPower());
                return monster;
            default:
                throw new RuntimeException();
        }
    }
}
