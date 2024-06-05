package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.GetEncounterRequest;
import julielerche.capstone.activity.results.GetEncounterResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetEncounterLambda
        extends LambdaActivityRunner<GetEncounterRequest, GetEncounterResult>
        implements RequestHandler<LambdaRequest<GetEncounterRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetEncounterRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> {
                GetEncounterRequest unauthenticatedRequest = input.fromBody(GetEncounterRequest.class);
                return GetEncounterRequest.builder()
                    .withUserId(unauthenticatedRequest.getUserId())
                    .build();
            },
            (request, serviceComponent) ->
                    serviceComponent.provideGetEncounterActivity().handleRequest(request)
        );
    }

}
