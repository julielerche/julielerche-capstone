package julielerche.capstone.dynamodb;

import julielerche.capstone.dynamodb.models.Asset;
import julielerche.capstone.dynamodb.models.AssetFromTable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import julielerche.capstone.converters.AssetToOtherTypesConverter;

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
    public void saveAsset(Asset asset) {
        AssetToOtherTypesConverter converter = new AssetToOtherTypesConverter();
        AssetFromTable tableAsset = converter.assetToTableAsset(asset);
        this.mapper.save(tableAsset);
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

}
