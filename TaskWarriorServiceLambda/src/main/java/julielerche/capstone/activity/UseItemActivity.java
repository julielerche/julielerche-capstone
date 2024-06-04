package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.UseItemRequest;
import julielerche.capstone.activity.results.UseItemResult;

import julielerche.capstone.converters.UserToModelConverter;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.Asset;
import julielerche.capstone.dynamodb.models.User;
import julielerche.capstone.exceptions.InvalidAssetException;
import julielerche.capstone.models.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * Implementation of the UseItemActivity for the TaskWarriorService's UseItem API.
 * <p>
 * This API allows the customer use an item from their inventory.
 */
public class UseItemActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;

    /**
     * Instantiates a new UseItemActivity object.
     *
     * @param userDao UserDao to access the user table.
     */
    @Inject
    public UseItemActivity(UserDao userDao) {
        this.userDao = userDao;
    }
    /**
     * This method handles the incoming request by getting a user
     * with the provided userId from the request and validating the item us.
     * <p>
     * It then returns the updated user.
     * <p>
     * If the provided asset is not in the user inventory, throws invalid asset.
     *
     * @param useItemRequest request object containing the customerId and asset
     * @return useItemResult result object containing the API defined
     */

    public UseItemResult handleRequest(final UseItemRequest useItemRequest) {
        log.info("Received UseItemRequest {}", useItemRequest);

        User loadedUser = userDao.loadUser(useItemRequest.getUserId());
        List<Asset> assets = loadedUser.getInventory();
        if (!assets.contains(useItemRequest.getAsset())) {
            throw new InvalidAssetException();
        }
        User updatedUser = userDao.useItem(loadedUser, useItemRequest.getAsset());
        UserModel model = new UserToModelConverter().userToModel(updatedUser);
        return UseItemResult.builder()
                .withUser(model)
                .build();
    }
}
