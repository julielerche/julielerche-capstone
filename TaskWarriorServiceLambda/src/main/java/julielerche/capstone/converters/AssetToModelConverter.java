package julielerche.capstone.converters;

import julielerche.capstone.dynamodb.models.Asset;
import julielerche.capstone.models.AssetModel;

public class AssetToModelConverter {
    /**
     * Converts a Asset to a AssetModel representation.
     * @param asset the asset to convert into the model
     * @return model the model of the asset to return
     */
    public AssetModel assetToModel(Asset asset) {
        AssetModel model = new AssetModel(asset.getAssetType(), asset.getAssetId(), asset.getName(),
                asset.getDescription());
        return model;
    }
}
