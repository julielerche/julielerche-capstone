package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.UpdateTaskRequest;
import julielerche.capstone.activity.results.UpdateTaskResult;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.Difficulty;
import julielerche.capstone.dynamodb.models.Task;
import julielerche.capstone.dynamodb.models.TaskType;
import julielerche.capstone.dynamodb.models.User;
import julielerche.capstone.testHelper.UserCreater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.AssertJUnit.assertEquals;

public class UpdateTaskActivityTest {
    @Mock
    private UserDao userDao;

    private UpdateTaskActivity updateTaskActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        updateTaskActivity = new UpdateTaskActivity(userDao);
    }

    @Test
    public void handleRequest_withAllInformation_returnsTaskWithUpdatedValues() {
        //given
        User user = UserCreater.generateUser("one", "Julie");

        Task task = new Task(TaskType.CHORE, "Dishes", Difficulty.EASY, false);
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        user.setChores(taskList);

        UpdateTaskRequest request = UpdateTaskRequest.builder()
                .withUserId("one")
                .withTask(task)
                .withNewType("DAILY")
                .withNewDifficulty("MEDIUM")
                .withNewName("Laundry")
                .build();

        when(userDao.loadUser("one")).thenReturn(user);

        //when
        UpdateTaskResult result = updateTaskActivity.handleRequest(request);

        //then
        Task updatedTask = result.getTasks();
        assertEquals(TaskType.DAILY, updatedTask.getTaskType());
        assertEquals(Difficulty.MEDIUM, updatedTask.getDifficulty());
        assertEquals("Laundry", updatedTask.getTaskName());
    }
}
