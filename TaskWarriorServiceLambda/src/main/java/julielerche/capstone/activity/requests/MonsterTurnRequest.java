package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = MonsterTurnRequest.Builder.class)
public class MonsterTurnRequest {
    private final String userId;

    private MonsterTurnRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "MonsterTurnRequest{" +
                "userId='" + userId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static MonsterTurnRequest.Builder builder() {
        return new MonsterTurnRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;

        public MonsterTurnRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public MonsterTurnRequest build() {
            return new MonsterTurnRequest(userId);
        }
    }
}
