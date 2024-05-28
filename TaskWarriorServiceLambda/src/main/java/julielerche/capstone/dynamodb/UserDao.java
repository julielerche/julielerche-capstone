package julielerche.capstone.dynamodb;

import julielerche.capstone.dynamodb.models.Task;
import julielerche.capstone.dynamodb.models.TaskType;
import julielerche.capstone.dynamodb.models.User;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import julielerche.capstone.activity.requests.AddTaskToUserRequest;
import julielerche.capstone.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Saves the user to the User table in dynamoDB.
     * @param user the user to save to the table
     */
    public void saveUser(User user) {
        this.mapper.save(user);
    }

    /**
     * Loads the user from the User table in dynamoDB.
     * @param userId the user to load from the table.
     * @return user the user object loaded from the table.
     */
    public User loadUser(String userId) {
        User loadedUser = this.mapper.load(User.class, userId);

        if (loadedUser == null) {
            throw new UserNotFoundException("No user matches the id");
        }
        return loadedUser;
    }
    /**
     * Saves the user to the User table in dynamoDB.
     * @param addTaskToUserRequest the request with task and user info
     * @return user the user with the new data
     */
    public User saveTaskToUser(AddTaskToUserRequest addTaskToUserRequest) {
        User loadedUser = loadUser(addTaskToUserRequest.getUserId());
        Task taskToAdd = addTaskToUserRequest.getTask();
        TaskType requestedType = addTaskToUserRequest.getTask().getTaskType();
        List<Task> taskList;
        switch (requestedType) {
            case TODO:
                taskList = loadedUser.getToDos();
                taskList.add(taskToAdd);
                loadedUser.setToDos(taskList);
                break;
            case CHORE:
                taskList = loadedUser.getChores();
                taskList.add(taskToAdd);
                loadedUser.setChores(taskList);
                break;
            case DAILY:
                taskList = loadedUser.getDailies();
                taskList.add(taskToAdd);
                loadedUser.setDailies(taskList);
                break;
            default:
                taskList = new ArrayList<>();
                break;
        }
        saveUser(loadedUser);
        return loadedUser;
    }
}
