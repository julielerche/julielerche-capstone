package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.CreateUserRequest;
import julielerche.capstone.activity.results.CreateUserResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateUserLambda
        extends LambdaActivityRunner<CreateUserRequest, CreateUserResult>
        implements RequestHandler<LambdaRequest<CreateUserRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<CreateUserRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> {
                CreateUserRequest unauthenticatedRequest = input.fromBody(CreateUserRequest.class);
                return CreateUserRequest.builder()
                    .withUserId(unauthenticatedRequest.getUserId())
                    .withName(unauthenticatedRequest.getName())
                    .build();
            },
            (request, serviceComponent) ->
                serviceComponent.provideCreateUserActivity().handleRequest(request)
        );
    }
}
