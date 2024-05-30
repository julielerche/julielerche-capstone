package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.UpdateUserRequest;
import julielerche.capstone.activity.results.UpdateUserResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateUserLambda
        extends LambdaActivityRunner<UpdateUserRequest, UpdateUserResult>
        implements RequestHandler<LambdaRequest<UpdateUserRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<UpdateUserRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> {
                UpdateUserRequest unauthenticatedRequest = input.fromBody(UpdateUserRequest.class);
                return UpdateUserRequest.builder()
                    .withUserId(unauthenticatedRequest.getUserId())
                    .withName(unauthenticatedRequest.getName())
                    .build();
            },
            (request, serviceComponent) ->
                    serviceComponent.provideUpdateUserActivity().handleRequest(request)
        );
    }
}
