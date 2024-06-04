package julielerche.capstone.activity.results;

import julielerche.capstone.models.UserModel;

public class StartNewDayResult {
    private final UserModel user;

    private StartNewDayResult(UserModel user) {
        this.user = user;
    }

    public UserModel StartNewDay() {
        return user;
    }

    @Override
    public String toString() {
        return "StartNewDayResult{" +
                "user=" + user +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static StartNewDayResult.Builder builder() {
        return new StartNewDayResult.Builder();
    }

    public static class Builder {
        private UserModel user;

        public StartNewDayResult.Builder withUser(UserModel user) {
            this.user = user;
            return this;
        }

        public StartNewDayResult build() {
            return new StartNewDayResult(user);
        }
    }
}
