package julielerche.capstone.dynamodb.models;

public class Asset {
    AssetType assetType;
    Integer assetId;
    String name;
    String description;
    Integer healthOrCost;
    Integer attackPower;
    Integer cost;
    Integer startingHealth;
    Integer currentHealth;

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

    public Integer getStartingHealth() {
        return startingHealth;
    }

    public void setStartingHealth(Integer startingHealth) {
        this.startingHealth = startingHealth;
    }

    public Integer getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(Integer currentHealth) {
        this.currentHealth = currentHealth;
    }

    @Override
    public boolean equals(Object obj) {
        Asset other = (Asset) obj;
        return this.getName().equals(other.name) && this.getDescription().equals(other.description);
    }
}
