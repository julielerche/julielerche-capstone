package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.UpdateUserRequest;
import julielerche.capstone.activity.results.UpdateUserResult;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.User;
import julielerche.capstone.exceptions.UserNotFoundException;
import julielerche.capstone.testHelper.UserCreater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertEquals;

public class UpdateUserActivityTest {
    @Mock
    private UserDao userDao;

    private UpdateUserActivity updateUserActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        updateUserActivity = new UpdateUserActivity(userDao);
    }

    @Test
    public void handleRequest_withIdAndNewName_updatesAndSavesUser() {
        //Given
        String userId = "lerchejulie@email.com";
        String newDisplayName = "Julie";
        User loadedUser = UserCreater.generateUser(userId, "OldName");
        when(userDao.loadUser(userId)).thenReturn(loadedUser);

        UpdateUserRequest request = UpdateUserRequest.builder()
                .withUserId(userId)
                .withName(newDisplayName)
                .build();

        //when
        UpdateUserResult result = updateUserActivity.handleRequest(request);

        //then
        verify(userDao).saveUser(any(User.class));

        assertEquals(userId, result.getUser().getUserId());
        assertEquals(newDisplayName, result.getUser().getDisplayName());
    }

    @Test
    public void handleRequest_noIdInDatabase_throwsError() {
        //Given
        String userId = "lerchejulie@email.com";
        String newDisplayName = "Julie";

        when(userDao.loadUser(userId)).thenThrow(UserNotFoundException.class);

        UpdateUserRequest request = UpdateUserRequest.builder()
                .withUserId(userId)
                .withName(newDisplayName)
                .build();

        //when
        //then
        assertThrows(UserNotFoundException.class, () -> updateUserActivity.handleRequest(request));
    }
}
