package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.MonsterTurnRequest;
import julielerche.capstone.activity.results.MonsterTurnResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MonsterTurnLambda
        extends LambdaActivityRunner<MonsterTurnRequest, MonsterTurnResult>
        implements RequestHandler<AuthenticatedLambdaRequest<MonsterTurnRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<MonsterTurnRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> input.fromUserClaims(claims -> MonsterTurnRequest.builder()
                .withUserId(claims.get("email"))
                .build()),
            (request, serviceComponent) ->
                    serviceComponent.provideMonsterTurnActivity().handleRequest(request)
        );
    }

}
