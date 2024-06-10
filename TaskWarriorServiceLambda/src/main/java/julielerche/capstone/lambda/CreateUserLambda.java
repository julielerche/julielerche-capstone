package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.CreateUserRequest;
import julielerche.capstone.activity.results.CreateUserResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateUserLambda
        extends LambdaActivityRunner<CreateUserRequest, CreateUserResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateUserRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateUserRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> {
                CreateUserRequest unauthenticatedRequest = input.fromBody(CreateUserRequest.class);
                return input.fromUserClaims(claims ->
                    CreateUserRequest.builder()
                    .withUserId(claims.get("email"))
                    .withName(unauthenticatedRequest.getName())
                    .build());
            },
            (request, serviceComponent) ->
                serviceComponent.provideCreateUserActivity().handleRequest(request)
        );
    }
}
