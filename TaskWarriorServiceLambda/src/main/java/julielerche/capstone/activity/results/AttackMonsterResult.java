package julielerche.capstone.activity.results;

import julielerche.capstone.models.AssetModel;

import java.util.List;

public class AttackMonsterResult {
    private final List<AssetModel> assets;
    private final int goldEarned;

    private AttackMonsterResult(List<AssetModel> assets, int goldEarned) {
        this.assets = assets;
        this.goldEarned = goldEarned;
    }

    public List<AssetModel> getAssets() {
        return assets;
    }

    public int getGoldEarned() {
        return goldEarned;
    }
    @Override
    public String toString() {
        return "AttackMonsterResult{" +
                "assets=" + assets +
                "goldEarned=" + goldEarned +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static AttackMonsterResult.Builder builder() {
        return new AttackMonsterResult.Builder();
    }

    public static class Builder {
        private List<AssetModel> assets;
        private int goldEarned;

        public AttackMonsterResult.Builder withAssets(List<AssetModel> assets) {
            this.assets = assets;
            return this;
        }

        public AttackMonsterResult.Builder withGoldEarned(int goldEarned) {
            this.goldEarned = goldEarned;
            return this;
        }
        public AttackMonsterResult build() {
            return new AttackMonsterResult(assets, goldEarned);
        }
    }
}
