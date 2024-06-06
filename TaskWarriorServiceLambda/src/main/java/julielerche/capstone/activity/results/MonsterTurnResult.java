package julielerche.capstone.activity.results;

import julielerche.capstone.models.UserModel;

public class MonsterTurnResult {
    private final UserModel user;

    private MonsterTurnResult(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "MonsterTurnResult{" +
                "user=" + user +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static MonsterTurnResult.Builder builder() {
        return new MonsterTurnResult.Builder();
    }

    public static class Builder {
        private UserModel user;

        public MonsterTurnResult.Builder withUser(UserModel user) {
            this.user = user;
            return this;
        }

        public MonsterTurnResult build() {
            return new MonsterTurnResult(user);
        }
    }
}
