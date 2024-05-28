package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.DeleteTaskRequest;
import julielerche.capstone.activity.results.DeleteTaskResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteTaskLambda
        extends LambdaActivityRunner<DeleteTaskRequest, DeleteTaskResult>
        implements RequestHandler<LambdaRequest<DeleteTaskRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<DeleteTaskRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> {
                DeleteTaskRequest unauthenticatedRequest = input.fromBody(DeleteTaskRequest.class);
                return DeleteTaskRequest.builder()
                    .withUserId(unauthenticatedRequest.getUserId())
                    .withTask(unauthenticatedRequest.getTask())
                    .build();
            },
            (request, serviceComponent) ->
                    serviceComponent.provideDeleteTaskActivity().handleRequest(request)
        );
    }

}
