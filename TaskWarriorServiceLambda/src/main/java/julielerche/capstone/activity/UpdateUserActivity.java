package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.UpdateUserRequest;
import julielerche.capstone.activity.results.UpdateUserResult;

import julielerche.capstone.converters.UserToModelConverter;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.User;
import julielerche.capstone.models.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import javax.inject.Inject;


/**
 * Implementation of the UpdateUserActivity for the TaskWarriorService's UpdateUser API.
 * <p>
 * This API allows the customer to update an existing user in the table.
 */
public class UpdateUserActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;

    /**
     * Instantiates a new UpdateUserActivity object.
     *
     * @param userDao UserDao to access the user table.
     */
    @Inject
    public UpdateUserActivity(UserDao userDao) {
        this.userDao = userDao;
    }
    /**
     * This method handles the incoming request by persisting a new user
     * with the provided userId from the request.
     * <p>
     * It then returns the newly updated user.
     * <p>
     * If the provided user name or customer ID has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param updateUserRequest request object containing the customerId
     * @return updateUserResult result object containing the API defined
     */

    public UpdateUserResult handleRequest(final UpdateUserRequest updateUserRequest) {
        log.info("Received UpdateUserRequest {}", updateUserRequest);

        //TODO check string for valid characters
        //gets the user from the table
        User loadedUser = userDao.loadUser(updateUserRequest.getUserId());

        //sets the new name for the user
        loadedUser.setDisplayName(updateUserRequest.getName());

        //saves the user to the table
        userDao.saveUser(loadedUser);

        //converts the user to the model to be returned
        UserModel model = new UserToModelConverter().userToModel(loadedUser);

        //returns the result with the model
        return UpdateUserResult.builder()
                .withUser(model)
                .build();
    }
}
