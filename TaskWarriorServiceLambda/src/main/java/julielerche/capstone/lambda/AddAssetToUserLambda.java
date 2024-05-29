package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.AddAssetToUserRequest;
import julielerche.capstone.activity.results.AddAssetToUserResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddAssetToUserLambda
        extends LambdaActivityRunner<AddAssetToUserRequest, AddAssetToUserResult>
        implements RequestHandler<LambdaRequest<AddAssetToUserRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<AddAssetToUserRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> {
                AddAssetToUserRequest unauthenticatedRequest = input.fromBody(AddAssetToUserRequest.class);
                return AddAssetToUserRequest.builder()
                    .withUserId(unauthenticatedRequest.getUserId())
                    .withAssetType(unauthenticatedRequest.getAssetType())
                    .withAssetId(unauthenticatedRequest.getAssetId())
                    .build();
            },
            (request, serviceComponent) ->
                    serviceComponent.provideAddAssetToUserActivity().handleRequest(request)
        );
    }

}
