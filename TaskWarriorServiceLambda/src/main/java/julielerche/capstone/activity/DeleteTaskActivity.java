package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.DeleteTaskRequest;
import julielerche.capstone.activity.results.DeleteTaskResult;

import julielerche.capstone.converters.UserToModelConverter;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.User;
import julielerche.capstone.models.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the DeleteTaskActivity for the TaskWarriorService's AddTask API.
 * <p>
 * This API allows the customer to delete a task from requested user.
 */
public class DeleteTaskActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;

    /**
     * Instantiates a new DeleteTaskActivity object.
     *
     * @param userDao UserDao to access the user table.
     */
    @Inject
    public DeleteTaskActivity(UserDao userDao) {
        this.userDao = userDao;
    }
    /**
     * This method handles the incoming request by adding the task to the requested user.
     * <p>
     * It then returns the updated user.
     * <p>
     *
     * @param deleteTaskRequest request object containing the customerId and task
     * @return deleteTaskResult result object containing the API defined
     */

    public DeleteTaskResult handleRequest(final DeleteTaskRequest deleteTaskRequest) {
        log.info("Received DeleteTaskRequest {}", deleteTaskRequest);

        User updatedUser = userDao.deleteTaskFromUser(deleteTaskRequest);

        UserModel model = new UserToModelConverter().userToModel(updatedUser);
        return DeleteTaskResult.builder()
                .withUser(model)
                .build();
    }
}
