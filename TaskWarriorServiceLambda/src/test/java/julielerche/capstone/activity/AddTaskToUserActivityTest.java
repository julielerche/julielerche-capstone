package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.AddTaskToUserRequest;
import julielerche.capstone.activity.results.AddTaskToUserResult;
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

public class AddTaskToUserActivityTest {
    @Mock
    private UserDao userDao;

    private AddTaskToUserActivity addTaskToUserActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        addTaskToUserActivity = new AddTaskToUserActivity(userDao);
    }

    @Test
    public void handleRequest_withAllInformation_returnsUserWithAssetInInventory() {
        //given
        User user = UserCreater.generateUser("one", "Julie");

        Task task = new Task(TaskType.CHORE, "Dishes", Difficulty.EASY, false);
        user.setChores(List.of(task));
        AddTaskToUserRequest request = AddTaskToUserRequest.builder()
                .withUserId("one")
                .withTask(task)
                .withTaskType("CHORE")
                .build();

        when(userDao.saveTaskToUser(request)).thenReturn(user);

        //when
        AddTaskToUserResult result = addTaskToUserActivity.handleRequest(request);

        //then
        assertTrue(result.getUser().getChores().contains(task));
    }
}
