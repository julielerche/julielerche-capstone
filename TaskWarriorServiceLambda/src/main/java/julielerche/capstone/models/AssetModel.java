package julielerche.capstone.models;

import julielerche.capstone.dynamodb.models.AssetType;

public class AssetModel {
    AssetType assetType;
    Integer assetId;
    String name;
    String description;
    Integer attackPower;
    Integer cost;
    Integer startingHealth;
    Integer currentHealth;

    /**
     * The constructor for the asset model.
     * @param assetType either potion, item, or monster
     * @param assetId the unique number
     * @param name name of the asset
     * @param description description of what it does.
     */
    public AssetModel(AssetType assetType, Integer assetId, String name,
                      String description) {
        this.assetType = assetType;
        this.assetId = assetId;
        this.name = name;
        this.description = description;
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
}
