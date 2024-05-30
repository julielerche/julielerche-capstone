package julielerche.capstone.activity.results;

import julielerche.capstone.models.AssetModel;

import java.util.List;

public class GetAffordableAssetsResult {
    private final List<AssetModel> assets;

    private GetAffordableAssetsResult(List<AssetModel> assets) {
        this.assets = assets;
    }

    public List<AssetModel> getAssets() {
        return assets;
    }

    @Override
    public String toString() {
        return "GetAffordableAssetsResult{" +
                "assets=" + assets +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static GetAffordableAssetsResult.Builder builder() {
        return new GetAffordableAssetsResult.Builder();
    }

    public static class Builder {
        private List<AssetModel> assets;

        public GetAffordableAssetsResult.Builder withAssets(List<AssetModel> assets) {
            this.assets = assets;
            return this;
        }

        public GetAffordableAssetsResult build() {
            return new GetAffordableAssetsResult(assets);
        }
    }
}
