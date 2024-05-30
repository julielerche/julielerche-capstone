package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.GetAffordableAssetsRequest;
import julielerche.capstone.activity.results.GetAffordableAssetsResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetAffordableAssetsLambda
        extends LambdaActivityRunner<GetAffordableAssetsRequest, GetAffordableAssetsResult>
        implements RequestHandler<LambdaRequest<GetAffordableAssetsRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetAffordableAssetsRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> {
                GetAffordableAssetsRequest unauthenticatedRequest = input.fromBody(GetAffordableAssetsRequest.class);
                return GetAffordableAssetsRequest.builder()
                    .withUserId(unauthenticatedRequest.getUserId())
                    .build();
            },
            (request, serviceComponent) ->
                    serviceComponent.provideGetAffordableAssetsActivity().handleRequest(request)
        );
    }

}
