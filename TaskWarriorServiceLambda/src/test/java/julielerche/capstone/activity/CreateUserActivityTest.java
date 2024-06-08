package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.CreateUserRequest;
import julielerche.capstone.activity.results.CreateUserResult;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.AssertJUnit.assertEquals;

public class CreateUserActivityTest {
    @Mock
    private UserDao userDao;

    private CreateUserActivity createUserActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        createUserActivity = new CreateUserActivity(userDao);
    }

    @Test
    public void handleRequest_withIdAndName_createsAndSavesUser() {
        //Given
        String userId = "lerchejulie@email.com";
        String displayName = "Julie";
        Integer expectedHealth = 100;


        CreateUserRequest request = CreateUserRequest.builder()
                .withUserId(userId)
                .withName(displayName)
                .build();

        //when
        CreateUserResult result = createUserActivity.handleRequest(request);

        //then
        verify(userDao).saveUser(any(User.class));

        assertEquals(userId, result.getUser().getUserId());
        assertEquals(displayName, result.getUser().getDisplayName());
        assertEquals(expectedHealth, result.getUser().getHealth());
    }
}
