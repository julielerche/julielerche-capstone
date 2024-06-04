package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.StartNewDayRequest;
import julielerche.capstone.activity.results.StartNewDayResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StartNewDayLambda
        extends LambdaActivityRunner<StartNewDayRequest, StartNewDayResult>
        implements RequestHandler<LambdaRequest<StartNewDayRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<StartNewDayRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> {
                    StartNewDayRequest unauthenticatedRequest = input.fromBody(StartNewDayRequest.class);
                    return StartNewDayRequest.builder()
                            .withUserId(unauthenticatedRequest.getUserId())
                            .build();
                },
                (request, serviceComponent) ->
                        serviceComponent.provideStartNewDayActivity().handleRequest(request)
        );
    }

}
