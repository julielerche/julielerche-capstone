package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetEncounterRequest.Builder.class)
public class GetEncounterRequest {
    private final String userId;

    private GetEncounterRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "GetEncounterRequest{" +
                "userId='" + userId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static GetEncounterRequest.Builder builder() {
        return new GetEncounterRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;

        public GetEncounterRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public GetEncounterRequest build() {
            return new GetEncounterRequest(userId);
        }
    }
}
