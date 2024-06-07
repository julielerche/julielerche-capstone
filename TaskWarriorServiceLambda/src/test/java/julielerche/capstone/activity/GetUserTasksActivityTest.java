package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.GetUserTasksRequest;
import julielerche.capstone.activity.results.GetUserTasksResult;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.Difficulty;
import julielerche.capstone.dynamodb.models.Task;
import julielerche.capstone.dynamodb.models.TaskType;
import julielerche.capstone.dynamodb.models.User;
import julielerche.capstone.testHelper.UserCreater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.AssertJUnit.assertTrue;

public class GetUserTasksActivityTest {
    @Mock
    private UserDao userDao;

    private GetUserTasksActivity getUserTasksActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        getUserTasksActivity = new GetUserTasksActivity(userDao);
    }

    @Test
    public void handleRequest_getUserDailies_returnsDailyTasks() {
        //given
        User user = UserCreater.generateUser("one", "Julie");

        Task task = new Task(TaskType.DAILY, "Dishes", Difficulty.EASY, false);
        user.setDailies(List.of(task));
        GetUserTasksRequest request = GetUserTasksRequest.builder()
                .withUserId("one")
                .withTaskType("DAILY")
                .build();

        when(userDao.loadUser("one")).thenReturn(user);

        //when
        GetUserTasksResult result = getUserTasksActivity.handleRequest(request);

        //then
        assertTrue(result.getTasks().contains(task));
    }

    @Test
    public void handleRequest_getUserChores_returnsChoreTasks() {
        //given
        User user = UserCreater.generateUser("one", "Julie");

        Task task = new Task(TaskType.CHORE, "Dishes", Difficulty.EASY, false);
        user.setChores(List.of(task));
        GetUserTasksRequest request = GetUserTasksRequest.builder()
                .withUserId("one")
                .withTaskType("CHORE")
                .build();

        when(userDao.loadUser("one")).thenReturn(user);

        //when
        GetUserTasksResult result = getUserTasksActivity.handleRequest(request);

        //then
        assertTrue(result.getTasks().contains(task));
    }

    @Test
    public void handleRequest_getUserToDos_returnsToDosTasks() {
        //given
        User user = UserCreater.generateUser("one", "Julie");

        Task task = new Task(TaskType.TODO, "Dishes", Difficulty.EASY, false);
        user.setToDos(List.of(task));
        GetUserTasksRequest request = GetUserTasksRequest.builder()
                .withUserId("one")
                .withTaskType("TODO")
                .build();

        when(userDao.loadUser("one")).thenReturn(user);

        //when
        GetUserTasksResult result = getUserTasksActivity.handleRequest(request);

        //then
        assertTrue(result.getTasks().contains(task));
    }
}
