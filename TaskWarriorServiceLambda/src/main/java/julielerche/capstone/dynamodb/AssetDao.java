package julielerche.capstone.dynamodb;

import julielerche.capstone.dynamodb.models.Asset;
import julielerche.capstone.dynamodb.models.AssetFromTable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import julielerche.capstone.converters.AssetToOtherTypesConverter;
import julielerche.capstone.exceptions.UserNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AssetDao {
    private final DynamoDBMapper mapper;

    /**
     * Creates a new AssetDao object from an injected mapper.
     * @param dynamoDBMapper the {@link DynamoDBMapper} used to interact with the users table
     */
    @Inject
    public AssetDao(DynamoDBMapper dynamoDBMapper) {
        this.mapper = dynamoDBMapper;
    }

    /**
     * Saves the user to the Asset table in dynamoDB.
     * @param asset the asset to save to the table
     */
    public AssetFromTable saveAsset(Asset asset) {
        AssetToOtherTypesConverter converter = new AssetToOtherTypesConverter();
        AssetFromTable tableAsset = converter.assetToTableAsset(asset);
        this.mapper.save(tableAsset);
        return tableAsset;
    }

    /**
     * Gets a specific asset from the table using the type and id.
     * @param assetType either POTION, ITEM, or MONSTER
     * @param assetId the Id of the asset.
     * @return the asset from the table.
     */
    public AssetFromTable getSpecificAsset(String assetType, Integer assetId) {
        AssetFromTable query = new AssetFromTable();
        query.setAssetType(assetType);
        query.setAssetId(assetId);
        return this.mapper.load(query);
    }

    /**
     * Gets a list of assets from the table using a query.
     * @param assetType either POTION, ITEM, or MONSTER
     * @return the list of assets from the table.
     */
    public List<AssetFromTable> getAllOfAssetType(String assetType) {
        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();
        if (assetType == null) {
            throw new UserNotFoundException("Could not find asset with type " + assetType);
        }

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":assetType", new AttributeValue().withS(assetType));

        dynamoDBScanExpression.setExpressionAttributeValues(valueMap);
        dynamoDBScanExpression.setFilterExpression("assetType = :assetType");

        return this.mapper.scan(AssetFromTable.class, dynamoDBScanExpression);
    }

    /**
     * Queries the table for all assets that cost less than the gold the user has.
     * @param goldAmount the amount of gold the user has.
     * @return List assets the user can afford.
     */
    public List<AssetFromTable> getAffordableAssets(Integer goldAmount) {
        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
                .withIndexName("HealthOrCostIndex")
                .withConsistentRead(false);

        if (goldAmount == null) {
            throw new UserNotFoundException("Could not find asset with type " + goldAmount);
        }

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":goldAmount", new AttributeValue().withN(String.valueOf(goldAmount)));
        valueMap.put(":monster", new AttributeValue().withS("MONSTER"));

        dynamoDBScanExpression.setExpressionAttributeValues(valueMap);
        dynamoDBScanExpression.setFilterExpression("healthOrCost <= :goldAmount and assetType <> :monster");

        return this.mapper.scan(AssetFromTable.class, dynamoDBScanExpression);
    }

}
