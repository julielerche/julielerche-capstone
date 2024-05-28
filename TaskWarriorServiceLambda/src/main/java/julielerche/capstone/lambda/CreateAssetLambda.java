package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.CreateAssetRequest;
import julielerche.capstone.activity.results.CreateAssetResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateAssetLambda
        extends LambdaActivityRunner<CreateAssetRequest, CreateAssetResult>
        implements RequestHandler<LambdaRequest<CreateAssetRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<CreateAssetRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> {
                CreateAssetRequest unauthenticatedRequest = input.fromBody(CreateAssetRequest.class);
                return CreateAssetRequest.builder()
                            .withAssetType(unauthenticatedRequest.getAssetType())
                            .withAssetId(unauthenticatedRequest.getAssetId())
                            .withAsset(unauthenticatedRequest.getAsset())
                            .build();
            },
            (request, serviceComponent) ->
                    serviceComponent.provideCreateAssetActivity().handleRequest(request)
        );
    }

}
