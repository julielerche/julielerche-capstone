package julielerche.capstone.activity.results;

import julielerche.capstone.models.AssetModel;

import java.util.List;

public class AttackMonsterResult {
    private final List<AssetModel> assets;

    private AttackMonsterResult(List<AssetModel> assets) {
        this.assets = assets;
    }

    public List<AssetModel> getAssets() {
        return assets;
    }

    @Override
    public String toString() {
        return "AttackMonsterResult{" +
                "assets=" + assets +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static AttackMonsterResult.Builder builder() {
        return new AttackMonsterResult.Builder();
    }

    public static class Builder {
        private List<AssetModel> assets;

        public AttackMonsterResult.Builder withAssets(List<AssetModel> assets) {
            this.assets = assets;
            return this;
        }

        public AttackMonsterResult build() {
            return new AttackMonsterResult(assets);
        }
    }
}
