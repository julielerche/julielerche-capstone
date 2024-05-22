package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateUserRequest.Builder.class)
public class CreateUserRequest {
    private final String userId;
    private final String name;

    private CreateUserRequest(String userId, String name) {
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
        return "CreateUserRequest{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static CreateUserRequest.Builder builder() {
        return new CreateUserRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String name;
        private String userId;

        public CreateUserRequest.Builder withName(String name) {
            this.name = name;
            return this;
        }

        public CreateUserRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public CreateUserRequest build() {
            return new CreateUserRequest(name, userId);
        }
    }
}
