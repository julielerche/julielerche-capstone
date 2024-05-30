package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.UpdateTaskRequest;
import julielerche.capstone.activity.results.UpdateTaskResult;

import julielerche.capstone.converters.DifficultyConverter;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.Task;
import julielerche.capstone.dynamodb.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Implementation of the UpdateTaskActivity for the TaskWarriorService's UpdateTask API.
 * <p>
 * This API allows the customer to update a task on the user table.
 */
public class UpdateTaskActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;

    /**
     * Instantiates a new UpdateTaskActivity object.
     *
     * @param userDao UserDao to access the user table.
     */
    @Inject
    public UpdateTaskActivity(UserDao userDao) {
        this.userDao = userDao;
    }
    /**
     * This method handles the incoming request by updating a task
     * with the provided userId from the request.
     * <p>
     * It then returns the newly created user.
     * <p>
     * If the provided user name or customer ID has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param updateTaskRequest request object containing the customerId
     * @return updateTaskResult result object containing the API defined
     */

    public UpdateTaskResult handleRequest(final UpdateTaskRequest updateTaskRequest) {
        log.info("Received UpdateTaskRequest {}", updateTaskRequest);

        //TODO check string for valid characters
        List<Task> chosenList = null;
        Task updatedTask = new Task();
        Map<Task, List<Task>> taskMap;
        User loadedUser = userDao.loadUser(updateTaskRequest.getUserId());
        switch (updateTaskRequest.getTask().getTaskType()) {
            case CHORE:
                chosenList = loadedUser.getChores();
                taskMap = this.updateTaskToList(chosenList, updateTaskRequest);
                for (Task task : taskMap.keySet()) {
                    loadedUser.setChores(taskMap.get(task));
                    userDao.saveUser(loadedUser);
                    updatedTask = task;
                }
                break;
            case DAILY:
                chosenList = loadedUser.getDailies();
                taskMap = this.updateTaskToList(chosenList, updateTaskRequest);
                for (Task task : taskMap.keySet()) {
                    loadedUser.setDailies(taskMap.get(task));
                    userDao.saveUser(loadedUser);
                    updatedTask = task;
                }
                break;
            case TODO:
                chosenList = loadedUser.getToDos();
                taskMap = this.updateTaskToList(chosenList, updateTaskRequest);
                for (Task task : taskMap.keySet()) {
                    loadedUser.setToDos(taskMap.get(task));
                    userDao.saveUser(loadedUser);
                    updatedTask = task;
                }
                break;
            default:
                chosenList = new ArrayList<>();
                break;
        }

        return UpdateTaskResult.builder()
                .withTasks(updatedTask)
                .build();
    }

    /**
     * Uses logic to add the updated task to the user's list.
     * @param chosenList the list of daily/chore/todo
     * @param updateTaskRequest the request that has the new info
     * @return a map of the new task to the list of updated tasks
     */
    private Map<Task, List<Task>> updateTaskToList(List<Task> chosenList, UpdateTaskRequest updateTaskRequest) {
        try {
            Task existingTask = new Task();
            for (Task task : chosenList) {
                if (task.getTaskName().equals(updateTaskRequest.getTask().getTaskName())) {
                    existingTask = task;
                    chosenList.remove(existingTask);
                    break;
                }
            }
            if (updateTaskRequest.getNewName() != null) {
                existingTask.setTaskName(updateTaskRequest.getNewName());
            }
            if (updateTaskRequest.getDifficulty() != null) {
                existingTask.setDifficulty(new DifficultyConverter()
                        .stringToDifficulty(updateTaskRequest.getDifficulty()));
            }
            chosenList.add(existingTask);
            Map<Task, List<Task>> taskListMap = new HashMap<>();
            taskListMap.put(existingTask, chosenList);
            return taskListMap;
        } catch (NullPointerException e) {
            throw new RuntimeException();
        }
    }
}