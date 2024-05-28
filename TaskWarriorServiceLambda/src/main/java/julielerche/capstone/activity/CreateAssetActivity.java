package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.CreateAssetRequest;
import julielerche.capstone.activity.results.CreateAssetResult;

import julielerche.capstone.converters.AssetToModelConverter;
import julielerche.capstone.dynamodb.AssetDao;
import julielerche.capstone.models.AssetModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the CreateAssetActivity for the TaskWarriorService's CreateAsset API.
 * <p>
 * This API allows the customer to create a new asset in the table.
 */
public class CreateAssetActivity {
    private final Logger log = LogManager.getLogger();
    private final AssetDao assetDao;

    /**
     * Instantiates a new CreateAssetActivity object.
     *
     * @param assetDao AssetDao to access the asset table.
     */
    @Inject
    public CreateAssetActivity(AssetDao assetDao) {
        this.assetDao = assetDao;
    }
    /**
     * This method handles the incoming request by persisting a new asset
     * with the provided assetId from the request.
     * <p>
     * It then returns the newly created asset.
     * <p>
     * If the provided asset name or customer ID has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param createAssetRequest request object containing the asset pojo
     * @return createAssetResult result object containing the API defined
     */

    public CreateAssetResult handleRequest(final CreateAssetRequest createAssetRequest) {
        log.info("Received CreateAssetRequest {}", createAssetRequest);

        //TODO check string for valid characters

        //saves the asset to the table
        assetDao.saveAsset(createAssetRequest.getAsset());

        //converts the asset to the model to be returned
        AssetModel model = new AssetToModelConverter().assetToModel(createAssetRequest.getAsset());

        //returns the result with the model
        return CreateAssetResult.builder()
                .withAsset(model)
                .build();
    }
}
