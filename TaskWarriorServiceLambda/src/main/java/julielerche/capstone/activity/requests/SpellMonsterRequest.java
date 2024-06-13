package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import julielerche.capstone.dynamodb.models.Spell;

@JsonDeserialize(builder = SpellMonsterRequest.Builder.class)
public class SpellMonsterRequest {
    private final String userId;
    private final Spell spell;

    private SpellMonsterRequest(String userId, Spell spell) {
        this.userId = userId;
        this.spell = spell;
    }

    public Spell getSpell() {
        return spell;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "SpellMonsterRequest{" +
                "userId='" + userId + '\'' +
                ", spell='" + spell + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static SpellMonsterRequest.Builder builder() {
        return new SpellMonsterRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private Spell spell;
        private String userId;

        public SpellMonsterRequest.Builder withSpell(Spell spell) {
            this.spell = spell;
            return this;
        }

        public SpellMonsterRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public SpellMonsterRequest build() {
            return new SpellMonsterRequest(userId, spell);
        }
    }
}
