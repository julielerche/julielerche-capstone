package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.GetUserRequest;
import julielerche.capstone.activity.results.GetUserResult;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.User;
import julielerche.capstone.exceptions.UserNotFoundException;
import julielerche.capstone.testHelper.UserCreater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertEquals;

public class GetUserActivityTest {
    @Mock
    private UserDao userDao;

    private GetUserActivity getUserActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        getUserActivity = new GetUserActivity(userDao);
    }

    @Test
    public void handleRequest_withIdAndName_getsCorrectUser() {
        //Given
        String userId = "lerchejulie@email.com";
        String displayName = "Julie";
        Integer expectedHealth = 100;
        User loadedUser = UserCreater.generateUser(userId, displayName);
        when(userDao.loadUser(userId)).thenReturn(loadedUser);


        GetUserRequest request = GetUserRequest.builder()
                .withUserId(userId)
                .build();

        //when
        GetUserResult result = getUserActivity.handleRequest(request);

        //then

        assertEquals(userId, result.getUser().getUserId());
        assertEquals(displayName, result.getUser().getDisplayName());
        assertEquals(expectedHealth, result.getUser().getHealth());
    }

    @Test
    public void handleRequest_withWrongId_throwsUserNotFoundException() {
        //Given
        String userId = "lerchejulie@email.com";
        String displayName = "Julie";
        Integer expectedHealth = 100;
        User loadedUser = UserCreater.generateUser(userId, displayName);
        when(userDao.loadUser(userId)).thenThrow(UserNotFoundException.class);


        GetUserRequest request = GetUserRequest.builder()
                .withUserId(userId)
                .build();

        //when
        assertThrows(UserNotFoundException.class, () -> getUserActivity.handleRequest(request));
    }
}
