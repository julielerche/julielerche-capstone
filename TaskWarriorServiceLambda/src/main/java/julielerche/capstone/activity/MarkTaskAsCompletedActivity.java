package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.MarkTaskAsCompletedRequest;
import julielerche.capstone.activity.results.MarkTaskAsCompletedResult;

import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.Difficulty;
import julielerche.capstone.dynamodb.models.Task;
import julielerche.capstone.dynamodb.models.TaskType;
import julielerche.capstone.dynamodb.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Implementation of the MarkTaskAsCompletedActivity for the TaskWarriorService's MarkTaskAsCompleted API.
 * <p>
 * This API allows the customer to mark a task as complete.
 */
public class MarkTaskAsCompletedActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;

    /**
     * Instantiates a new MarkTaskAsCompletedActivity object.
     *
     * @param userDao UserDao to access the user table.
     */
    @Inject
    public MarkTaskAsCompletedActivity(UserDao userDao) {
        this.userDao = userDao;
    }
    /**
     * This method handles the incoming request by setting a task to complete
     * with the provided userId from the request.
     * <p>
     * It then returns the task that was completed and the gold amount.
     * <p>

     * @param markTaskAsCompletedRequest request object containing the customerId and task
     * @return markTaskAsCompletedResult result object containing the task and gold
     */

    public MarkTaskAsCompletedResult handleRequest(final MarkTaskAsCompletedRequest markTaskAsCompletedRequest) {
        log.info("Received MarkTaskAsCompletedRequest {}", markTaskAsCompletedRequest);

        //TODO check string for valid characters
        List<Task> chosenList = null;
        Task updatedTask = new Task();
        Map<Task, List<Task>> taskMap;
        Integer goldEarned = 0;

        //loads user from table
        User loadedUser = userDao.loadUser(markTaskAsCompletedRequest.getUserId());
        switch (markTaskAsCompletedRequest.getTask().getTaskType()) {
            case CHORE:
                chosenList = loadedUser.getChores();
                taskMap = this.markTaskAsCompletedToList(chosenList, markTaskAsCompletedRequest);
                for (Task task : taskMap.keySet()) {
                    loadedUser.setChores(taskMap.get(task));
                    updatedTask = task;
                }
                break;
            case DAILY:
                chosenList = loadedUser.getDailies();
                taskMap = this.markTaskAsCompletedToList(chosenList, markTaskAsCompletedRequest);
                for (Task task : taskMap.keySet()) {
                    loadedUser.setDailies(taskMap.get(task));
                    updatedTask = task;
                }
                break;
            case TODO:
                chosenList = loadedUser.getToDos();
                taskMap = this.markTaskAsCompletedToList(chosenList, markTaskAsCompletedRequest);
                for (Task task : taskMap.keySet()) {
                    loadedUser.setToDos(taskMap.get(task));
                    updatedTask = task;
                }
                break;
            default:
                break;
        }
        goldEarned = getGold(updatedTask.getDifficulty(), updatedTask.getTaskType());
        loadedUser.setGold(loadedUser.getGold() + goldEarned);
        userDao.saveUser(loadedUser);

        return MarkTaskAsCompletedResult.builder()
                .withTask(updatedTask)
                .withGold(goldEarned)
                .build();
    }

    /**
     * Uses logic to add the updated task to the user's list or deletes to/do categroy.
     * @param chosenList the list of daily/chore/to-do
     * @param markTaskAsCompletedRequest the request that has the new info
     * @return a map of the new task to the list of updated tasks
     */
    private Map<Task, List<Task>> markTaskAsCompletedToList(List<Task> chosenList,
                                        MarkTaskAsCompletedRequest markTaskAsCompletedRequest) {
        try {
            Task existingTask = new Task();
            for (Task task : chosenList) {
                if (task.getTaskName().equals(markTaskAsCompletedRequest.getTask().getTaskName())) {
                    existingTask = task;
                    chosenList.remove(existingTask);
                    existingTask.setCompleted(true);
                    break;
                }
            }
            if (!existingTask.getTaskType().equals(TaskType.TODO)) {
                chosenList.add(existingTask);
            }
            Map<Task, List<Task>> taskListMap = new HashMap<>();
            taskListMap.put(existingTask, chosenList);
            return taskListMap;
        } catch (NullPointerException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Gets an amount of gold based on difficulty of task.
     * @param difficulty the difficulty of the task
     * @param type the type of task it is
     * @return integer of the gold amount
     */

    private Integer getGold(Difficulty difficulty, TaskType type) {
        Integer gold = 0;
        switch (difficulty) {
            case EASY:
                gold = 10;
                break;
            case MEDIUM:
                gold = 20;
                break;
            case HARD:
                gold = 30;
                break;
            default:
                break;
        }
        switch (type) {
            case CHORE:
                gold = (int) (gold * 1.5);
                return gold;
            case TODO:
                gold = gold * 2;
                return gold;
            default:
                return gold;
        }
    }
}
