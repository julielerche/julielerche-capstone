package julielerche.capstone.activity.results;

import julielerche.capstone.models.AssetModel;

import java.util.List;

public class SpellMonsterResult {
    private final List<AssetModel> assets;
    private final int goldEarned;

    private SpellMonsterResult(List<AssetModel> assets, int goldEarned) {
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
        return "SpellMonsterResult{" +
                "assets=" + assets +
                "goldEarned=" + goldEarned +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static SpellMonsterResult.Builder builder() {
        return new SpellMonsterResult.Builder();
    }

    public static class Builder {
        private List<AssetModel> assets;
        private int goldEarned;

        public SpellMonsterResult.Builder withAssets(List<AssetModel> assets) {
            this.assets = assets;
            return this;
        }

        public SpellMonsterResult.Builder withGoldEarned(int goldEarned) {
            this.goldEarned = goldEarned;
            return this;
        }
        public SpellMonsterResult build() {
            return new SpellMonsterResult(assets, goldEarned);
        }
    }
}
