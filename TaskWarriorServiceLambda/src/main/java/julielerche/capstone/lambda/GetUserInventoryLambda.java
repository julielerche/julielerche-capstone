package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.GetUserInventoryRequest;
import julielerche.capstone.activity.results.GetUserInventoryResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetUserInventoryLambda
        extends LambdaActivityRunner<GetUserInventoryRequest, GetUserInventoryResult>
        implements RequestHandler<LambdaRequest<GetUserInventoryRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetUserInventoryRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> {
                GetUserInventoryRequest unauthenticatedRequest = input.fromBody(GetUserInventoryRequest.class);
                return GetUserInventoryRequest.builder()
                    .withUserId(unauthenticatedRequest.getUserId())
                    .build();
            },
            (request, serviceComponent) ->
                    serviceComponent.provideGetUserInventoryActivity().handleRequest(request)
        );
    }

}
