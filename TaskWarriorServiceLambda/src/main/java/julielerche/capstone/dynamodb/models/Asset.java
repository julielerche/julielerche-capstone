package julielerche.capstone.dynamodb.models;

public class Asset {
    AssetType assetType;
    Integer assetId;
    String name;
    String description;
    Integer healthOrCost;
    Integer attackPower;
    Integer cost;

//    /**
//     * Constructor for the asset object.
//     * @param assetType either potion, monster, or item
//     * @param assetId the unique number identifier
//     * @param name the name of the object
//     * @param description description of use
//     * @param healthOrCost either the health or cost of the asset
//     */
//    public Asset(AssetType assetType, Integer assetId, String name, String description, Integer healthOrCost) {
//        this.assetType = assetType;
//        this.assetId = assetId;
//        this.name = name;
//        this.description = description;
//        this.healthOrCost = healthOrCost;
//    }

    /**
     * default constructor for the asset object.
     */
    public Asset() {
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
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

    public Integer getHealthOrCost() {
        return healthOrCost;
    }

    public void setHealthOrCost(Integer healthOrCost) {
        this.healthOrCost = healthOrCost;
    }

    public Integer getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(Integer attackPower) {
        this.attackPower = attackPower;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
