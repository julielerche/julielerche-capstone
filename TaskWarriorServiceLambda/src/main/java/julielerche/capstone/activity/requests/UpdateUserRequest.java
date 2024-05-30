package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UpdateUserRequest.Builder.class)
public class UpdateUserRequest {
    private final String userId;
    private final String name;

    private UpdateUserRequest(String userId, String name) {
        this.name = name;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "UpdateUserRequest{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static UpdateUserRequest.Builder builder() {
        return new UpdateUserRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String name;
        private String userId;

        public UpdateUserRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public UpdateUserRequest.Builder withName(String name) {
            this.name = name;
            return this;
        }

        public UpdateUserRequest build() {
            return new UpdateUserRequest(userId, name);
        }
    }
}
