package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.CreateUserRequest;
import julielerche.capstone.activity.results.CreateUserResult;

import julielerche.capstone.converters.UserToModelConverter;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.Task;
import julielerche.capstone.dynamodb.models.User;
import julielerche.capstone.models.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 * Implementation of the CreateUserActivity for the TaskWarriorService's CreateUser API.
 * <p>
 * This API allows the customer to create a new user in the table.
 */
public class CreateUserActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;

    /**
     * Instantiates a new CreateUserActivity object.
     *
     * @param userDao UserDao to access the user table.
     */
    @Inject
    public CreateUserActivity(UserDao userDao) {
        this.userDao = userDao;
    }
    /**
     * This method handles the incoming request by persisting a new user
     * with the provided userId from the request.
     * <p>
     * It then returns the newly created user.
     * <p>
     * If the provided user name or customer ID has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param createUserRequest request object containing the customerId
     * @return createUserResult result object containing the API defined
     */

    public CreateUserResult handleRequest(final CreateUserRequest createUserRequest) {
        log.info("Received CreateUserRequest {}", createUserRequest);

        //TODO check string for valid characters
        //constructs the new user with default and custom values
        User newUser = new User();
        newUser.setUserId(createUserRequest.getUserId());
        newUser.setDisplayName(createUserRequest.getName());
        newUser.setChores(new ArrayList<>());
        newUser.setDailies(new ArrayList<>());
        newUser.setToDos(new ArrayList<>());
        newUser.setInventory(new ArrayList<>());
        newUser.setGold(100);
        newUser.setHealth(100);
        newUser.setMana(100);
        newUser.setStamina(100);

        //saves the user to the table
        userDao.saveUser(newUser);

        //converts the user to the model to be returned
        UserModel model = new UserToModelConverter().userToModel(newUser);

        //returns the result with the model
        return CreateUserResult.builder()
                .withUser(model)
                .build();
    }
}
