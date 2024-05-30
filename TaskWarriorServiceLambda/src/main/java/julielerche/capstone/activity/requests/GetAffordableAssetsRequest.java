package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetAffordableAssetsRequest.Builder.class)
public class GetAffordableAssetsRequest {
    private final String userId;

    private GetAffordableAssetsRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "GetAffordableAssetsRequest{" +
                "userId='" + userId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static GetAffordableAssetsRequest.Builder builder() {
        return new GetAffordableAssetsRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;

        public GetAffordableAssetsRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public GetAffordableAssetsRequest build() {
            return new GetAffordableAssetsRequest(userId);
        }
    }
}
