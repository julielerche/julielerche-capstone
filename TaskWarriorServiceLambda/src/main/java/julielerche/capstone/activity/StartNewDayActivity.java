package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.StartNewDayRequest;
import julielerche.capstone.activity.results.StartNewDayResult;

import julielerche.capstone.converters.UserToModelConverter;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.Task;
import julielerche.capstone.dynamodb.models.User;
import julielerche.capstone.models.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import javax.inject.Inject;


/**
 * Implementation of the StartNewDayActivity for the TaskWarriorService's StartNewDay API.
 * <p>
 * This API allows the customer to create a new user in the table.
 */
public class StartNewDayActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;

    /**
     * Instantiates a new StartNewDayActivity object.
     *
     * @param userDao UserDao to access the user table.
     */
    @Inject
    public StartNewDayActivity(UserDao userDao) {
        this.userDao = userDao;
    }
    /**
     * This method sets all tasks to uncompleted and user loses health for each uncompleted daily.
     * <p>
     * It then returns the newly created user.
     * <p>
     * If the provided user name or customer ID has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param startNewDayRequest request object containing the customerId
     * @return StartNewDayResult result object containing the API defined
     */

    public StartNewDayResult handleRequest(final StartNewDayRequest startNewDayRequest) {
        log.info("Received StartNewDayRequest {}", startNewDayRequest);

        //TODO check string for valid characters

        User loadedUser = userDao.loadUser(startNewDayRequest.getUserId());

        //health loss for uncompleted dailies
        long completed = loadedUser.getDailies().stream()
                .filter(Task::getCompleted)
                .count();
        int healthLost = (loadedUser.getDailies().size() - (int) completed) * 5;
        loadedUser.setHealth(loadedUser.getHealth() - healthLost);

        //sets all to uncompleted
        loadedUser.setDailies(setTasksToNotCompleted(loadedUser.getDailies()));
        loadedUser.setChores(setTasksToNotCompleted(loadedUser.getChores()));

        userDao.saveUser(loadedUser);

        UserModel model = new UserToModelConverter().userToModel(loadedUser);
        return StartNewDayResult.builder()
                .withUser(model)
                .build();
    }

    private List<Task> setTasksToNotCompleted(List<Task> taskList) {
        for (Task task : taskList) {
            task.setCompleted(false);
        }
        return taskList;
    }
}
