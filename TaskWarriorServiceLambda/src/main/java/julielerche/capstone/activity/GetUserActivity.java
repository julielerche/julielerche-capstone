package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.GetUserRequest;
import julielerche.capstone.activity.results.GetUserResult;

import julielerche.capstone.converters.UserToModelConverter;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.User;
import julielerche.capstone.models.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the GetUserActivity for the TaskWarriorService's GetUser API.
 * <p>
 * This API allows the customer to create a new user in the table.
 */
public class GetUserActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;

    /**
     * Instantiates a new GetUserActivity object.
     *
     * @param userDao UserDao to access the user table.
     */
    @Inject
    public GetUserActivity(UserDao userDao) {
        this.userDao = userDao;
    }
    /**
     * This method handles the incoming request by persisting a new user
     * with the provided userId from the request.
     * <p>
     * It then returns the newly created user.
     * <p>
     * If the provided user name or customer ID has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param getUserRequest request object containing the customerId
     * @return getUserResult result object containing the API defined
     */

    public GetUserResult handleRequest(final GetUserRequest getUserRequest) {
        log.info("Received GetUserRequest {}", getUserRequest);

        //TODO check string for valid characters

        User loadedUser = userDao.loadUser(getUserRequest.getUserId());
        UserModel model = new UserToModelConverter().userToModel(loadedUser);
        return GetUserResult.builder()
                .withUser(model)
                .build();
    }
}
