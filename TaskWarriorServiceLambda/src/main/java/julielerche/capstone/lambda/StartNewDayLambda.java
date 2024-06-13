package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.StartNewDayRequest;
import julielerche.capstone.activity.results.StartNewDayResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StartNewDayLambda
        extends LambdaActivityRunner<StartNewDayRequest, StartNewDayResult>
        implements RequestHandler<AuthenticatedLambdaRequest<StartNewDayRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<StartNewDayRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> input.fromUserClaims(claims -> StartNewDayRequest.builder()
                .withUserId(claims.get("email"))
                .build()),
            (request, serviceComponent) ->
                    serviceComponent.provideStartNewDayActivity().handleRequest(request)
        );
    }

}
