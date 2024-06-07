package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.StartNewDayRequest;
import julielerche.capstone.activity.results.StartNewDayResult;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.Difficulty;
import julielerche.capstone.dynamodb.models.Task;
import julielerche.capstone.dynamodb.models.TaskType;
import julielerche.capstone.dynamodb.models.User;
import julielerche.capstone.testHelper.UserCreater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.AssertJUnit.*;

public class StartNewDayActivityTest {
    @Mock
    private UserDao userDao;
    @Captor
    ArgumentCaptor<User> userCaptor;

    private StartNewDayActivity startNewDayActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        startNewDayActivity = new StartNewDayActivity(userDao);
    }

    @Test
    public void handleRequest_withAllTasksCompleted_marksAllTasksAsFalse() {
        //given
        User user = UserCreater.generateUser("one", "Julie");

        Task task = new Task(TaskType.CHORE, "Dishes", Difficulty.EASY, true);
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        user.setChores(taskList);

        StartNewDayRequest request = StartNewDayRequest.builder()
                .withUserId("one")
                .build();

        when(userDao.loadUser("one")).thenReturn(user);

        //when
        StartNewDayResult result = startNewDayActivity.handleRequest(request);

        //then
        List<Task> resultList = result.getUser().getChores();
        assertFalse(resultList.get(0).getCompleted());
        assertFalse(resultList.get(1).getCompleted());
        assertFalse(resultList.get(2).getCompleted());
    }

    @Test
    public void handleRequest_withAllTasksNotCompleted_userLosesHealth() {
        //given
        User user = UserCreater.generateUser("one", "Julie");

        Task task = new Task(TaskType.DAILY, "Dishes", Difficulty.EASY, false);
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        user.setDailies(taskList);

        StartNewDayRequest request = StartNewDayRequest.builder()
                .withUserId("one")
                .build();

        when(userDao.loadUser("one")).thenReturn(user);

        //when
        StartNewDayResult result = startNewDayActivity.handleRequest(request);
        verify(userDao).saveUser(userCaptor.capture());
        //then
        int expectedHealth = 85;
        User savedUser = userCaptor.getValue();
        List<Task> resultList = result.getUser().getDailies();
        assertFalse(resultList.get(0).getCompleted());
        assertFalse(resultList.get(1).getCompleted());
        assertFalse(resultList.get(2).getCompleted());
        assertEquals(expectedHealth, (int) savedUser.getHealth());
    }
}
