package julielerche.capstone.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Assets")
public class AssetFromTable {
    String assetType;
    Integer assetId;
    String name;
    String description;
    Integer healthOrCost;
    Integer attackPower;

    @DynamoDBHashKey(attributeName = "assetType")
    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }
    @DynamoDBRangeKey(attributeName = "assetId")
    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }
    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "HealthOrCostIndex", attributeName = "healthOrCost")
    public Integer getHealthOrCost() {
        return healthOrCost;
    }

    public void setHealthOrCost(Integer healthOrCost) {
        this.healthOrCost = healthOrCost;
    }
    @DynamoDBAttribute(attributeName = "attackPower")
    public Integer getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(Integer attackPower) {
        this.attackPower = attackPower;
    }
}
