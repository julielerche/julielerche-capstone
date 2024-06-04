package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.MarkTaskAsCompletedRequest;
import julielerche.capstone.activity.results.MarkTaskAsCompletedResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MarkTaskAsCompletedLambda
        extends LambdaActivityRunner<MarkTaskAsCompletedRequest, MarkTaskAsCompletedResult>
        implements RequestHandler<LambdaRequest<MarkTaskAsCompletedRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<MarkTaskAsCompletedRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> {
                MarkTaskAsCompletedRequest unauthenticatedRequest = input.fromBody(MarkTaskAsCompletedRequest.class);
                return MarkTaskAsCompletedRequest.builder()
                    .withUserId(unauthenticatedRequest.getUserId())
                    .withTask(unauthenticatedRequest.getTask())
                    .build();
            },
            (request, serviceComponent) ->
                    serviceComponent.provideMarkTaskAsCompletedActivity().handleRequest(request)
        );
    }

}
