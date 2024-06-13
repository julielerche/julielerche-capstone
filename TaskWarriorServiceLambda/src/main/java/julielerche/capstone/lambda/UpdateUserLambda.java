package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.UpdateUserRequest;
import julielerche.capstone.activity.results.UpdateUserResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateUserLambda
        extends LambdaActivityRunner<UpdateUserRequest, UpdateUserResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateUserRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateUserRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> {
                UpdateUserRequest unauthenticatedRequest = input.fromBody(UpdateUserRequest.class);
                return input.fromUserClaims(claims -> UpdateUserRequest.builder()
                    .withUserId(claims.get("email"))
                    .withName(unauthenticatedRequest.getName())
                    .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideUpdateUserActivity().handleRequest(request)
        );
    }
}
