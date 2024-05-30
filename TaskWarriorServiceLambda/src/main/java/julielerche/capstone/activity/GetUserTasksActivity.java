package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.GetUserTasksRequest;
import julielerche.capstone.activity.results.GetUserTasksResult;

import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.Task;
import julielerche.capstone.dynamodb.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Implementation of the GetUserTasksActivity for the TaskWarriorService's GetUserTasks API.
 * <p>
 * This API allows the customer to get specific tasks from the table.
 */
public class GetUserTasksActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;

    /**
     * Instantiates a new GetUserTasksActivity object.
     *
     * @param userDao UserDao to access the user table.
     */
    @Inject
    public GetUserTasksActivity(UserDao userDao) {
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
     * @param getUserTasksRequest request object containing the customerId
     * @return getUserTasksResult result object containing the API defined
     */

    public GetUserTasksResult handleRequest(final GetUserTasksRequest getUserTasksRequest) {
        log.info("Received GetUserTasksRequest {}", getUserTasksRequest);

        //TODO check string for valid characters
        List<Task> chosenList = null;
        User loadedUser = userDao.loadUser(getUserTasksRequest.getUserId());
        switch (getUserTasksRequest.getTaskType()) {
            case "CHORE":
                chosenList = loadedUser.getChores();
                break;
            case "DAILY":
                chosenList = loadedUser.getDailies();
                break;
            case "TODO":
                chosenList = loadedUser.getToDos();
                break;
            default:
                chosenList = new ArrayList<>();
                break;
        }
        return GetUserTasksResult.builder()
                .withTasks(chosenList)
                .build();
    }
}
