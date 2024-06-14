package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.GetEncounterRequest;
import julielerche.capstone.activity.results.GetEncounterResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetEncounterLambda
        extends LambdaActivityRunner<GetEncounterRequest, GetEncounterResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetEncounterRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetEncounterRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> input.fromUserClaims(claims ->
                GetEncounterRequest.builder()
                    .withUserId(claims.get("email"))
                    .build()),
            (request, serviceComponent) ->
                    serviceComponent.provideGetEncounterActivity().handleRequest(request)
        );
    }

}
