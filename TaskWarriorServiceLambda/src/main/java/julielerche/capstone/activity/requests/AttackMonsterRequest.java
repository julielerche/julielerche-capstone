package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import julielerche.capstone.dynamodb.models.Attack;

@JsonDeserialize(builder = AttackMonsterRequest.Builder.class)
public class AttackMonsterRequest {
    private final String userId;
    private final Attack attack;

    private AttackMonsterRequest(String userId, Attack attack) {
        this.attack = attack;
        this.userId = userId;
    }

    public Attack getAttack() {
        return attack;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "AttackMonsterRequest{" +
                "userId='" + userId + '\'' +
                ", attack='" + attack + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static AttackMonsterRequest.Builder builder() {
        return new AttackMonsterRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private Attack attack;
        private String userId;

        public AttackMonsterRequest.Builder withAttack(Attack attack) {
            this.attack = attack;
            return this;
        }

        public AttackMonsterRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public AttackMonsterRequest build() {
            return new AttackMonsterRequest(userId, attack);
        }
    }
}
