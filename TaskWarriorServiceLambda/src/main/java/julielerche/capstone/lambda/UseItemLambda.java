package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.UseItemRequest;
import julielerche.capstone.activity.results.UseItemResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UseItemLambda
        extends LambdaActivityRunner<UseItemRequest, UseItemResult>
        implements RequestHandler<LambdaRequest<UseItemRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<UseItemRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> {
                UseItemRequest unauthenticatedRequest = input.fromBody(UseItemRequest.class);
                return UseItemRequest.builder()
                    .withUserId(unauthenticatedRequest.getUserId())
                    .withAsset(unauthenticatedRequest.getAsset())
                    .build();
            },
            (request, serviceComponent) ->
                    serviceComponent.provideUseItemActivity().handleRequest(request)
        );
    }
}
