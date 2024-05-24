package julielerche.capstone.converters;

import julielerche.capstone.dynamodb.models.*;

public class AssetToOtherTypesConverter {
    public Asset convertAssetToAssigned(AssetFromTable tableAsset) {
        AssetType type = tableAsset.getAssetType();

        switch (type)
        {
            case POTION:
                //code
                Asset potion = new Potion();
                potion.setAssetId(tableAsset.getAssetId());
                potion.setName(tableAsset.getName());
                potion.setDescription(tableAsset.getDescription());
                ((Potion) potion).setCost(tableAsset.getHealthOrCost());
                return potion;
            case ITEM:
                //code
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
