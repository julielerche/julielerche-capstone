package julielerche.capstone.dynamodb.models;

public class Monster extends Asset {
    AssetType assetType = AssetType.MONSTER;
    Integer assetId;
    String name;
    String description;
    Integer startingHealth;
    Integer attackPower;
    Integer currentHealth;

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

    public Integer getStartingHealth() {
        return startingHealth;
    }

    public void setStartingHealth(Integer startingHealth) {
        this.startingHealth = startingHealth;
    }

    public Integer getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(Integer attackPower) {
        this.attackPower = attackPower;
    }

    public Integer getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(Integer currentHealth) {
        this.currentHealth = currentHealth;
    }
}
