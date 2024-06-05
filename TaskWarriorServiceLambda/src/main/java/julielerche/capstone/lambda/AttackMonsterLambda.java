package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.AttackMonsterRequest;
import julielerche.capstone.activity.results.AttackMonsterResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AttackMonsterLambda
        extends LambdaActivityRunner<AttackMonsterRequest, AttackMonsterResult>
        implements RequestHandler<LambdaRequest<AttackMonsterRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<AttackMonsterRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> {
                AttackMonsterRequest unauthenticatedRequest = input.fromBody(AttackMonsterRequest.class);
                return AttackMonsterRequest.builder()
                    .withUserId(unauthenticatedRequest.getUserId())
                    .withAttack(unauthenticatedRequest.getAttack())
                    .build();
            },
            (request, serviceComponent) ->
                    serviceComponent.provideAttackMonsterActivity().handleRequest(request)
        );
    }

}
