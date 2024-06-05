package julielerche.capstone.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import julielerche.capstone.dynamodb.models.Encounter;
import julielerche.capstone.dynamodb.models.User;
import julielerche.capstone.exceptions.UserNotFoundException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EncounterDao {
    private final DynamoDBMapper mapper;

    /**
     * Creates a new EncounterDao object from an injected mapper.
     *
     * @param dynamoDBMapper the {@link DynamoDBMapper} used to interact with the users table
     */
    @Inject
    public EncounterDao(DynamoDBMapper dynamoDBMapper) {
        this.mapper = dynamoDBMapper;
    }

    /**
     * Loads the encounter from the encounter table in dynamoDB.
     * @param userId the id to load the encounter from
     * @return Encounter the encounter object loaded from the table.
     */
    public Encounter loadEncounter(String userId) {
        Encounter loadedEncounter = this.mapper.load(Encounter.class, userId);

        if (loadedEncounter == null) {
            throw new UserNotFoundException("No encounter matches the id");
        }
        return loadedEncounter;
    }

    /**
     * Loads the encounter from the encounter table in dynamoDB.
     * @param encounter the encounter to save to the table
     */
    public void saveEncounter(Encounter encounter) {
        this.mapper.load(Encounter.class, encounter);
    }
}