package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetUserRequest.Builder.class)
public class GetUserRequest {
    private final String userId;

    private GetUserRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "GetUserRequest{" +
                "userId='" + userId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static GetUserRequest.Builder builder() {
        return new GetUserRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;

        public GetUserRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public GetUserRequest build() {
            return new GetUserRequest(userId);
        }
    }
}
