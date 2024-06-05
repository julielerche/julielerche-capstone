package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.UpdateTaskRequest;
import julielerche.capstone.activity.results.UpdateTaskResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateTaskLambda
        extends LambdaActivityRunner<UpdateTaskRequest, UpdateTaskResult>
        implements RequestHandler<LambdaRequest<UpdateTaskRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<UpdateTaskRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> {
                UpdateTaskRequest unauthenticatedRequest = input.fromBody(UpdateTaskRequest.class);
                return UpdateTaskRequest.builder()
                    .withUserId(unauthenticatedRequest.getUserId())
                    .withTask(unauthenticatedRequest.getTask())
                    .withNewName(unauthenticatedRequest.getNewName())
                    .withNewDifficulty(unauthenticatedRequest.getNewDifficulty())
                    .withNewType(unauthenticatedRequest.getNewType())
                    .build();
            },
            (request, serviceComponent) ->
                    serviceComponent.provideUpdateTaskActivity().handleRequest(request)
        );
    }

}
