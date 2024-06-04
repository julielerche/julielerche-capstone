package julielerche.capstone.activity.results;

import julielerche.capstone.models.UserModel;

public class UseItemResult {
    private final UserModel user;

    private UseItemResult(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "UseItemResult{" +
                "user=" + user +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static UseItemResult.Builder builder() {
        return new UseItemResult.Builder();
    }

    public static class Builder {
        private UserModel user;

        public UseItemResult.Builder withUser(UserModel user) {
            this.user = user;
            return this;
        }

        public UseItemResult build() {
            return new UseItemResult(user);
        }
    }
}
