package julielerche.capstone.activity.results;

import julielerche.capstone.models.UserModel;

public class UpdateUserResult {
    private final UserModel user;

    private UpdateUserResult(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "UpdateUserResult{" +
                "user=" + user +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static UpdateUserResult.Builder builder() {
        return new UpdateUserResult.Builder();
    }

    public static class Builder {
        private UserModel user;

        public UpdateUserResult.Builder withUser(UserModel user) {
            this.user = user;
            return this;
        }

        public UpdateUserResult build() {
            return new UpdateUserResult(user);
        }
    }
}
