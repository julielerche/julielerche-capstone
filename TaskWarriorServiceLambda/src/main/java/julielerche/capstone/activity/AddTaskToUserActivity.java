package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.AddTaskToUserRequest;
import julielerche.capstone.activity.results.AddTaskToUserResult;

import julielerche.capstone.converters.UserToModelConverter;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.User;
import julielerche.capstone.models.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the AddTaskToUserActivity for the TaskWarriorService's AddTask API.
 * <p>
 * This API allows the customer to create a new task and save it to a user.
 */
public class AddTaskToUserActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;

    /**
     * Instantiates a new AddTaskToUserActivity object.
     *
     * @param userDao UserDao to access the user table.
     */
    @Inject
    public AddTaskToUserActivity(UserDao userDao) {
        this.userDao = userDao;
    }
    /**
     * This method handles the incoming request by adding the task to the requested user.
     * <p>
     * It then returns the updated user.
     * <p>
     *
     * @param addTaskToUserRequest request object containing the customerId and task
     * @return addTaskToUserResult result object containing the API defined
     */

    public AddTaskToUserResult handleRequest(final AddTaskToUserRequest addTaskToUserRequest) {
        log.info("Received AddTaskToUserRequest {}", addTaskToUserRequest);

        User updatedUser = userDao.saveTaskToUser(addTaskToUserRequest);

        UserModel model = new UserToModelConverter().userToModel(updatedUser);
        return AddTaskToUserResult.builder()
                .withUser(model)
                .build();
    }
}
