package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.AddTaskToUserRequest;
import julielerche.capstone.activity.results.AddTaskToUserResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddTaskToUserLambda
        extends LambdaActivityRunner<AddTaskToUserRequest, AddTaskToUserResult>
        implements RequestHandler<LambdaRequest<AddTaskToUserRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<AddTaskToUserRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> {
                    AddTaskToUserRequest unauthenticatedRequest = input.fromBody(AddTaskToUserRequest.class);
                    return AddTaskToUserRequest.builder()
                            .withUserId(unauthenticatedRequest.getUserId())
                            .withTaskType(unauthenticatedRequest.getTaskType())
                            .withTask(unauthenticatedRequest.getTask())
                            .build();
                },
                (request, serviceComponent) ->
                        serviceComponent.provideAddTaskToUserActivity().handleRequest(request)
        );
    }

}
