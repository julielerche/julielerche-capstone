package julielerche.capstone.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserDao {
    private final DynamoDBMapper mapper;

    /**
     * Creates a new UserDao object from an injected mapper.
     * @param dynamoDBMapper the {@link DynamoDBMapper} used to interact with the users table
     */
    @Inject
    public UserDao(DynamoDBMapper dynamoDBMapper) {
        this.mapper = dynamoDBMapper;
    }
}
