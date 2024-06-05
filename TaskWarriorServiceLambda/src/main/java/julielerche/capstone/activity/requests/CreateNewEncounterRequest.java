package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateNewEncounterRequest.Builder.class)
public class CreateNewEncounterRequest {
    private final String userId;

    private CreateNewEncounterRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "CreateNewEncounterRequest{" +
                "userId='" + userId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static CreateNewEncounterRequest.Builder builder() {
        return new CreateNewEncounterRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;

        public CreateNewEncounterRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public CreateNewEncounterRequest build() {
            return new CreateNewEncounterRequest(userId);
        }
    }
}
