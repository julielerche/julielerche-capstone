package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.DeleteTaskRequest;
import julielerche.capstone.activity.results.DeleteTaskResult;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertTrue;

public class DeleteTaskActivityTest {
    @Mock
    private UserDao userDao;

    private DeleteTaskActivity deleteTaskActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        deleteTaskActivity = new DeleteTaskActivity(userDao);
    }

    @Test
    public void handleRequest_withAllInformation_returnsUserWithAssetInInventory() {
        //given
        User user = UserCreater.generateUser("one", "Julie");
        User expectedUser = UserCreater.generateUser("one", "Julie");

        Task task = new Task(TaskType.CHORE, "Dishes", Difficulty.EASY, false);
        user.setChores(List.of(task));

        DeleteTaskRequest request = DeleteTaskRequest.builder()
                .withUserId("one")
                .withTask(task)
                .build();

        when(userDao.deleteTaskFromUser(request)).thenReturn(expectedUser);

        //when
        DeleteTaskResult result = deleteTaskActivity.handleRequest(request);

        //then
        assertTrue(result.getUser().getChores().isEmpty());
        assertEquals(user.getUserId(), result.getUser().getUserId());
    }
}
