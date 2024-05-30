package julielerche.capstone.activity.results;

import julielerche.capstone.dynamodb.models.Asset;

import java.util.List;

public class GetUserInventoryResult {
    private final List<Asset> assets;

    private GetUserInventoryResult(List<Asset> assets) {
        this.assets = assets;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    @Override
    public String toString() {
        return "GetUserInventoryResult{" +
                "assets=" + assets +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static GetUserInventoryResult.Builder builder() {
        return new GetUserInventoryResult.Builder();
    }

    public static class Builder {
        private List<Asset> assets;

        public GetUserInventoryResult.Builder withAssets(List<Asset> assets) {
            this.assets = assets;
            return this;
        }

        public GetUserInventoryResult build() {
            return new GetUserInventoryResult(assets);
        }

    }
}
