package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.AddAssetToUserRequest;
import julielerche.capstone.activity.results.AddAssetToUserResult;

import julielerche.capstone.converters.UserToModelConverter;
import julielerche.capstone.dynamodb.AssetDao;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.AssetFromTable;
import julielerche.capstone.dynamodb.models.User;
import julielerche.capstone.exceptions.InsufficentGoldException;
import julielerche.capstone.exceptions.InvalidAssetException;
import julielerche.capstone.models.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the AddAssetToUserActivity for the AssetWarriorService's AddAsset API.
 * <p>
 * This API allows the customer to create a new asset and save it to a user.
 */
public class AddAssetToUserActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;
    private final AssetDao assetDao;

    /**
     * Instantiates a new AddAssetToUserActivity object.
     *
     * @param userDao UserDao to access the user table.
     * @param assetDao AssetDao to access the asset table.
     */
    @Inject
    public AddAssetToUserActivity(UserDao userDao, AssetDao assetDao) {
        this.userDao = userDao;
        this.assetDao = assetDao;
    }
    /**
     * This method handles the incoming request by adding the asset to the requested user.
     * <p>
     * It then returns the updated user.
     * <p>
     *
     * @param addAssetToUserRequest request object containing the customerId and asset info
     * @return addAssetToUserResult result object containing the usermodel
     */

    public AddAssetToUserResult handleRequest(final AddAssetToUserRequest addAssetToUserRequest) {
        log.info("Received AddAssetToUserRequest {}", addAssetToUserRequest);
        AssetFromTable requestedAsset = assetDao.getSpecificAsset(addAssetToUserRequest.getAssetType(),
                addAssetToUserRequest.getAssetId());
        //checks to see if asset is a monster, errors
        if (requestedAsset.getAssetType().equals("MONSTER")) {
            throw new InvalidAssetException("Cannot be monster");
        }
        User requestedUser = userDao.loadUser(addAssetToUserRequest.getUserId());
        try {
            User updatedUser = userDao.addAssetToInventory(requestedUser, requestedAsset);
            UserModel model = new UserToModelConverter().userToModel(updatedUser);
            return AddAssetToUserResult.builder()
                    .withUser(model)
                    .build();
        } catch (InsufficentGoldException e) {
            throw new RuntimeException("Not enough gold to buy item");
        }

    }
}
