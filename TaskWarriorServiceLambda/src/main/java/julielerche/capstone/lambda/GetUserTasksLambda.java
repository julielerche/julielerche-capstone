package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.GetUserTasksRequest;
import julielerche.capstone.activity.results.GetUserTasksResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetUserTasksLambda
        extends LambdaActivityRunner<GetUserTasksRequest, GetUserTasksResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetUserTasksRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetUserTasksRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> {
                GetUserTasksRequest unauthenticatedRequest = input.fromBody(GetUserTasksRequest.class);
                return input.fromUserClaims(claims ->
                    GetUserTasksRequest.builder()
                    .withUserId(claims.get("email"))
                    .withTaskType(unauthenticatedRequest.getTaskType())
                    .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideGetUserTasksActivity().handleRequest(request)
        );
    }

}
