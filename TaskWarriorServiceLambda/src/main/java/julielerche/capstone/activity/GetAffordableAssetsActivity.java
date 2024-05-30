package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.GetAffordableAssetsRequest;
import julielerche.capstone.activity.results.GetAffordableAssetsResult;

import julielerche.capstone.converters.AssetToModelConverter;
import julielerche.capstone.converters.AssetToOtherTypesConverter;
import julielerche.capstone.dynamodb.AssetDao;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.AssetFromTable;
import julielerche.capstone.models.AssetModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Implementation of the GetAffordableAssetsActivity for the TaskWarriorService's GetAffordableAssets API.
 * <p>
 * This API allows the customer to create a new asset in the table.
 */
public class GetAffordableAssetsActivity {
    private final Logger log = LogManager.getLogger();
    private final AssetDao assetDao;
    private final UserDao userDao;

    /**
     * Instantiates a new GetAffordableAssetsActivity object.
     *
     * @param assetDao AssetDao to access the asset table.
     * @param userDao UserDao to access the user table.
     */
    @Inject
    public GetAffordableAssetsActivity(AssetDao assetDao, UserDao userDao) {
        this.assetDao = assetDao;
        this.userDao = userDao;
    }
    /**
     * This method handles the incoming request by querying the asset table.
     * <p>
     * It then returns the list of model assets.
     * <p>
     * If the provided asset name or customer ID has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param getAssetRequest request object containing the customerId
     * @return getAssetResult result object containing the API defined
     */

    public GetAffordableAssetsResult handleRequest(final GetAffordableAssetsRequest getAssetRequest) {
        log.info("Received GetAffordableAssetsRequest {}", getAssetRequest);

        Integer moneyAmount = userDao.loadUser(getAssetRequest.getUserId()).getGold();

        List<AssetFromTable> assetList = assetDao.getAffordableAssets(moneyAmount);

        List<AssetModel> convertedList = new ArrayList<>();
        AssetToOtherTypesConverter typesConverter = new AssetToOtherTypesConverter();

        for (AssetFromTable tableAsset : assetList) {
            convertedList.add(new AssetToModelConverter().assetToModel(typesConverter
                    .convertAssetToAssigned(tableAsset)));
        }

        return GetAffordableAssetsResult.builder()
                .withAssets(convertedList)
                .build();
    }
}
