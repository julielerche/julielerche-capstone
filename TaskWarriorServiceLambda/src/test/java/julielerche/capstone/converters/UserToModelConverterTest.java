package julielerche.capstone.converters;

import julielerche.capstone.dynamodb.models.User;
import julielerche.capstone.models.UserModel;
import julielerche.capstone.testHelper.UserCreater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.testng.AssertJUnit.assertEquals;

public class UserToModelConverterTest {

    private UserToModelConverter converter;

    @BeforeEach
    void setUp() {
        converter = new UserToModelConverter();
    }

    @Test
    public void userToModel_givenUser_returnsUserModel() {
        //given
        User user = UserCreater.generateUser("one", "Julie");

        //when
        UserModel result = converter.userToModel(user);

        //then
        assertEquals("one", result.getUserId());
        assertEquals("Julie", result.getDisplayName());
        assertEquals(100, (int) result.getHealth());
    }
}
