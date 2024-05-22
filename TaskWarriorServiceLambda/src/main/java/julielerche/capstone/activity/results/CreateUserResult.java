package julielerche.capstone.activity.results;

import julielerche.capstone.models.UserModel;

public class CreateUserResult {
    private final UserModel user;

    private CreateUserResult(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "CreateUserResult{" +
                "user=" + user +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static CreateUserResult.Builder builder() {
        return new CreateUserResult.Builder();
    }

    public static class Builder {
        private UserModel user;

        public CreateUserResult.Builder withUser(UserModel user) {
            this.user = user;
            return this;
        }

        public CreateUserResult build() {
            return new CreateUserResult(user);
        }
    }
}
