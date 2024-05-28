package julielerche.capstone.dynamodb.models;

public class Item extends Asset {
    AssetType assetType = AssetType.ITEM;
    Integer assetId;
    String name;
    String description;
    Integer cost;

    public AssetType getAssetType() {
        return assetType;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
