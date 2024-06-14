package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = AttackMonsterRequest.Builder.class)
public class AttackMonsterRequest {
    private final String userId;
    private final int attackPower;
    private final int staminaNeeded;
    private final int target;

    private AttackMonsterRequest(String userId, int attackPower, int staminaNeeded, int target) {
        this.userId = userId;
        this.attackPower = attackPower;
        this.staminaNeeded = staminaNeeded;
        this.target = target;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getStaminaNeeded() {
        return staminaNeeded;
    }

    public int getTarget() {
        return target;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "AttackMonsterRequest{" +
                "userId='" + userId + '\'' +
                ", attackPower='" + attackPower + '\'' +
                ", staminaNeeded='" + staminaNeeded + '\'' +
                ", target='" + target + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static AttackMonsterRequest.Builder builder() {
        return new AttackMonsterRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private int attackPower;
        private int staminaNeeded;
        private int target;
        public AttackMonsterRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public AttackMonsterRequest.Builder withAttackPower(int attackPower) {
            this.attackPower = attackPower;
            return this;
        }
        public AttackMonsterRequest.Builder withStaminaNeeded(int staminaNeeded) {
            this.staminaNeeded = staminaNeeded;
            return this;
        }
        public AttackMonsterRequest.Builder withTarget(int target) {
            this.target = target;
            return this;
        }

        public AttackMonsterRequest build() {
            return new AttackMonsterRequest(userId, attackPower, staminaNeeded, target);
        }
    }
}
