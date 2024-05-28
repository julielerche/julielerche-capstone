package julielerche.capstone.activity.results;

import julielerche.capstone.models.UserModel;

public class DeleteTaskResult {
    private final UserModel user;

    private DeleteTaskResult(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "DeleteTaskResult{" +
                "user=" + user +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static DeleteTaskResult.Builder builder() {
        return new DeleteTaskResult.Builder();
    }

    public static class Builder {
        private UserModel user;

        public DeleteTaskResult.Builder withUser(UserModel user) {
            this.user = user;
            return this;
        }

        public DeleteTaskResult build() {
            return new DeleteTaskResult(user);
        }
    }
}
