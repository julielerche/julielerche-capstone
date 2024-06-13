package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.SpellMonsterRequest;
import julielerche.capstone.activity.results.SpellMonsterResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpellMonsterLambda
        extends LambdaActivityRunner<SpellMonsterRequest, SpellMonsterResult>
        implements RequestHandler<AuthenticatedLambdaRequest<SpellMonsterRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<SpellMonsterRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> {
                SpellMonsterRequest unauthenticatedRequest = input.fromBody(SpellMonsterRequest.class);
                return input.fromUserClaims(claims -> SpellMonsterRequest.builder()
                        .withUserId(claims.get("email"))
                        .withSpell(unauthenticatedRequest.getSpell())
                    .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideSpellMonsterActivity().handleRequest(request)
        );
    }

}
