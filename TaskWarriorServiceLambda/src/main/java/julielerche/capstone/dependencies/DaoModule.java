package julielerche.capstone.dependencies;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dagger.Module;
import dagger.Provides;
import julielerche.capstone.dynamodb.DynamoDbClientProvider;

import javax.inject.Singleton;

/**
 * Dagger Module providing dependencies for DAO classes.
 */
@Module
public class DaoModule {
    /**
     * Provides a DynamoDBMapper singleton instance.
     *
     * @return DynamoDBMapper object
     */
    @Singleton
    @Provides
    public DynamoDBMapper provideDynamoDBMapper() {
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient(Regions.US_EAST_2));
        return dynamoDBMapper;
    }
}
