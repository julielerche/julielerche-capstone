package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.GetUserInventoryRequest;
import julielerche.capstone.activity.results.GetUserInventoryResult;

import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.Asset;
import julielerche.capstone.dynamodb.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * Implementation of the GetUserInventoryActivity for the TaskWarriorService's GetUserInventory API.
 * <p>
 * This API allows the customer to get the inventory from the user table.
 */
public class GetUserInventoryActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;

    /**
     * Instantiates a new GetUserInventoryActivity object.
     *
     * @param userDao UserDao to access the user table.
     */
    @Inject
    public GetUserInventoryActivity(UserDao userDao) {
        this.userDao = userDao;
    }
    /**
     * This method handles the incoming request by getting a user
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

    public GetUserInventoryResult handleRequest(final GetUserInventoryRequest getUserRequest) {
        log.info("Received GetUserInventoryRequest {}", getUserRequest);

        //TODO check string for valid characters

        User loadedUser = userDao.loadUser(getUserRequest.getUserId());
        List<Asset> assets = loadedUser.getInventory();
        return GetUserInventoryResult.builder()
                .withAssets(assets)
                .build();
    }
}
