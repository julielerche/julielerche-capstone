package julielerche.capstone.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import julielerche.capstone.dynamodb.models.Asset;

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
        this.mapper.save(asset);
    }

}
