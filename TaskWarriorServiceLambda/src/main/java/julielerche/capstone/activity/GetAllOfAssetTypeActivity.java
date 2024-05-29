package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.GetAllOfAssetTypeRequest;
import julielerche.capstone.activity.results.GetAllOfAssetTypeResult;

import julielerche.capstone.converters.AssetToModelConverter;
import julielerche.capstone.converters.AssetToOtherTypesConverter;
import julielerche.capstone.dynamodb.AssetDao;
import julielerche.capstone.dynamodb.models.AssetFromTable;
import julielerche.capstone.models.AssetModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Implementation of the GetAllOfAssetTypeActivity for the TaskWarriorService's GetAllOfAssetType API.
 * <p>
 * This API allows the customer to create a new asset in the table.
 */
public class GetAllOfAssetTypeActivity {
    private final Logger log = LogManager.getLogger();
    private final AssetDao assetDao;

    /**
     * Instantiates a new GetAllOfAssetTypeActivity object.
     *
     * @param assetDao AssetDao to access the asset table.
     */
    @Inject
    public GetAllOfAssetTypeActivity(AssetDao assetDao) {
        this.assetDao = assetDao;
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

    public GetAllOfAssetTypeResult handleRequest(final GetAllOfAssetTypeRequest getAssetRequest) {
        log.info("Received GetAllOfAssetTypeRequest {}", getAssetRequest);

        //TODO check string for valid characters

        List<AssetFromTable> assetList = new ArrayList<>();
        assetList.addAll(assetDao.getAllOfAssetType(getAssetRequest.getAssetType()));
        if (getAssetRequest.getAssetType2() != null) {
            assetList.addAll(assetDao.getAllOfAssetType(getAssetRequest.getAssetType2()));
        }
        List<AssetModel> convertedList = new ArrayList<>();
        AssetToOtherTypesConverter typesConverter = new AssetToOtherTypesConverter();

        for (AssetFromTable tableAsset : assetList) {
            convertedList.add(new AssetToModelConverter().assetToModel(typesConverter
                    .convertAssetToAssigned(tableAsset)));
        }

        return GetAllOfAssetTypeResult.builder()
                .withAssets(convertedList)
                .build();
    }
}
