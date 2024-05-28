package julielerche.capstone.activity.results;

import julielerche.capstone.models.AssetModel;

public class CreateAssetResult {
    private final AssetModel asset;

    private CreateAssetResult(AssetModel asset) {
        this.asset = asset;
    }

    public AssetModel getAsset() {
        return asset;
    }

    @Override
    public String toString() {
        return "CreateAssetResult{" +
                "asset=" + asset +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static CreateAssetResult.Builder builder() {
        return new CreateAssetResult.Builder();
    }

    public static class Builder {
        private AssetModel asset;

        public CreateAssetResult.Builder withAsset(AssetModel asset) {
            this.asset = asset;
            return this;
        }

        public CreateAssetResult build() {
            return new CreateAssetResult(asset);
        }
    }
}
