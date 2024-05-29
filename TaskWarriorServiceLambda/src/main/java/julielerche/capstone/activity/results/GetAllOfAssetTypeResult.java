package julielerche.capstone.activity.results;

import julielerche.capstone.models.AssetModel;

import java.util.List;

public class GetAllOfAssetTypeResult {
    private final List<AssetModel> assets;

    private GetAllOfAssetTypeResult(List<AssetModel> assets) {
        this.assets = assets;
    }

    public List<AssetModel> getAssets() {
        return assets;
    }

    @Override
    public String toString() {
        return "GetAllOfAssetTypeResult{" +
                "assets=" + assets +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static GetAllOfAssetTypeResult.Builder builder() {
        return new GetAllOfAssetTypeResult.Builder();
    }

    public static class Builder {
        private List<AssetModel> assets;

        public GetAllOfAssetTypeResult.Builder withAssets(List<AssetModel> assets) {
            this.assets = assets;
            return this;
        }

        public GetAllOfAssetTypeResult build() {
            return new GetAllOfAssetTypeResult(assets);
        }
    }
}
