package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.CreateNewEncounterRequest;
import julielerche.capstone.activity.results.CreateNewEncounterResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateNewEncounterLambda
        extends LambdaActivityRunner<CreateNewEncounterRequest, CreateNewEncounterResult>
        implements RequestHandler<LambdaRequest<CreateNewEncounterRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<CreateNewEncounterRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> {
                CreateNewEncounterRequest unauthenticatedRequest = input.fromBody(CreateNewEncounterRequest.class);
                return CreateNewEncounterRequest.builder()
                    .withUserId(unauthenticatedRequest.getUserId())
                    .build();
            },
            (request, serviceComponent) ->
                    serviceComponent.provideCreateNewEncounterActivity().handleRequest(request)
        );
    }

}
