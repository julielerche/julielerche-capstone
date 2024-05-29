package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.GetAllOfAssetTypeRequest;
import julielerche.capstone.activity.results.GetAllOfAssetTypeResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetAllOfAssetTypeLambda
        extends LambdaActivityRunner<GetAllOfAssetTypeRequest, GetAllOfAssetTypeResult>
        implements RequestHandler<LambdaRequest<GetAllOfAssetTypeRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetAllOfAssetTypeRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> {
                GetAllOfAssetTypeRequest unauthenticatedRequest = input.fromBody(GetAllOfAssetTypeRequest.class);
                return GetAllOfAssetTypeRequest.builder()
                    .withAssetType(unauthenticatedRequest.getAssetType())
                    .withAssetType2(unauthenticatedRequest.getAssetType2())
                    .build();
            },
            (request, serviceComponent) ->
                    serviceComponent.provideGetAllOfAssetTypeActivity().handleRequest(request)
        );
    }

}
