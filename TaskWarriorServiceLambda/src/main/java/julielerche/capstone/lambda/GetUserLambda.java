package julielerche.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import julielerche.capstone.activity.requests.GetUserRequest;
import julielerche.capstone.activity.results.GetUserResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetUserLambda
        extends LambdaActivityRunner<GetUserRequest, GetUserResult>
        implements RequestHandler<LambdaRequest<GetUserRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetUserRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> input.fromPath(path ->
                    GetUserRequest.builder()
                        .withUserId(path.get("id"))
                        .build()),
            (request, serviceComponent) ->
                    serviceComponent.provideGetUserActivity().handleRequest(request)
        );
    }

}
