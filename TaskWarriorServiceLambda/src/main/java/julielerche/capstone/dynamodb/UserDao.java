package julielerche.capstone.dynamodb;

import julielerche.capstone.dynamodb.models.Asset;
import julielerche.capstone.dynamodb.models.AssetFromTable;
import julielerche.capstone.dynamodb.models.Task;
import julielerche.capstone.dynamodb.models.TaskType;
import julielerche.capstone.dynamodb.models.User;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import julielerche.capstone.activity.requests.AddTaskToUserRequest;
import julielerche.capstone.activity.requests.DeleteTaskRequest;
import julielerche.capstone.converters.AssetToOtherTypesConverter;
import julielerche.capstone.exceptions.InsufficentGoldException;
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
     * Saves the task to the User table in dynamoDB.
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

    /**
     * Deletes the given task from the list and saves the user data in the table.
     * @param deleteTaskRequest the request with task and user info
     * @return user the user with the deleted data
     */
    public User deleteTaskFromUser(DeleteTaskRequest deleteTaskRequest) {
        User loadedUser = loadUser(deleteTaskRequest.getUserId());
        Task taskToDelete = deleteTaskRequest.getTask();
        TaskType requestedType = deleteTaskRequest.getTask().getTaskType();
        List<Task> taskList;
        switch (requestedType) {
            case TODO:
                taskList = loadedUser.getToDos();
                taskList.remove(taskToDelete);
                loadedUser.setToDos(taskList);
                break;
            case CHORE:
                taskList = loadedUser.getChores();
                taskList.remove(taskToDelete);
                loadedUser.setChores(taskList);
                break;
            case DAILY:
                taskList = loadedUser.getDailies();
                taskList.remove(taskToDelete);
                loadedUser.setDailies(taskList);
                break;
            default:
                taskList = new ArrayList<>();
                break;
        }
        saveUser(loadedUser);
        return loadedUser;
    }

    /**
     * Adds a specific asset to the user inventory, then saves.
     * @param user the requested user.
     * @param tableAsset the requested asset.
     * @return the updated user.
     */
    public User addAssetToInventory(User user, AssetFromTable tableAsset) {
        Asset convertedAsset = new AssetToOtherTypesConverter().convertAssetToAssigned(tableAsset);
        if (user.getGold() < convertedAsset.getCost()) {
            throw new InsufficentGoldException();
        }
        List<Asset> currentInventory = user.getInventory();
        currentInventory.add(convertedAsset);
        user.setInventory(currentInventory);
        user.setGold(user.getGold() - convertedAsset.getCost());
        saveUser(user);
        return user;
    }

    /**
     * Uses the item on the user and removes it from their inventory.
     * @param user the user to update
     * @param asset the item to use and remove.
     * @return user with updated values.
     */
    public User useItem(User user, Asset asset) {
        String description = asset.getDescription();
        String[] elements = description.split(" ");
        switch (elements[0]) {
            case "health":
                int newHealth = user.getHealth() + Integer.parseInt(elements[2]);
                if (newHealth >= 100) {
                    newHealth = 100;
                }
                user.setHealth(newHealth);
                break;
            case "mana":
                int newmana = user.getMana() + Integer.parseInt(elements[2]);
                if (newmana >= 100) {
                    newmana = 100;
                }
                user.setMana(newmana);
                break;
            case "stamina":
                int newStaimina = user.getStamina() + Integer.parseInt(elements[2]);
                if (newStaimina >= 100) {
                    newStaimina = 100;
                }
                user.setStamina(newStaimina);
                break;
            default:
                break;
        }
        List<Asset> newInventory = user.getInventory();
        newInventory.remove(asset);
        user.setInventory(newInventory);
        saveUser(user);
        return user;
    }
}
