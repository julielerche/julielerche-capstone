package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.MarkTaskAsCompletedRequest;
import julielerche.capstone.activity.results.MarkTaskAsCompletedResult;
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
import static org.testng.AssertJUnit.assertTrue;

public class MarkTaskAsCompletedActivityTest {
    @Mock
    private UserDao userDao;

    private MarkTaskAsCompletedActivity markTaskAsCompletedActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        markTaskAsCompletedActivity = new MarkTaskAsCompletedActivity(userDao);
    }

    @Test
    public void handleRequest_withAllInformation_returnsTaskMarkedCompleted() {
        //given
        User user = UserCreater.generateUser("one", "Julie");

        Task task = new Task(TaskType.CHORE, "Dishes", Difficulty.EASY, false);
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        user.setChores(taskList);

        MarkTaskAsCompletedRequest request = MarkTaskAsCompletedRequest.builder()
                .withUserId("one")
                .withTask(task)
                .build();

        when(userDao.loadUser("one")).thenReturn(user);

        //when
        MarkTaskAsCompletedResult result = markTaskAsCompletedActivity.handleRequest(request);

        //then
        assertTrue(result.getGold() > 5);
        assertTrue(result.getUserModel().getChores().contains(task));
    }
}
