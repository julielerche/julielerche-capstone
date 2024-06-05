package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = StartNewDayRequest.Builder.class)
public class StartNewDayRequest {
    private final String userId;

    private StartNewDayRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "StartNewDayRequest{" +
                "userId='" + userId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static StartNewDayRequest.Builder builder() {
        return new StartNewDayRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;

        public StartNewDayRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public StartNewDayRequest build() {
            return new StartNewDayRequest(userId);
        }
    }
}
