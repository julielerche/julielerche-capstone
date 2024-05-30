package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetUserInventoryRequest.Builder.class)
public class GetUserInventoryRequest {
    private final String userId;

    private GetUserInventoryRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "GetUserInventoryRequest{" +
                "userId='" + userId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static GetUserInventoryRequest.Builder builder() {
        return new GetUserInventoryRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;

        public GetUserInventoryRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public GetUserInventoryRequest build() {
            return new GetUserInventoryRequest(userId);
        }
    }
}
