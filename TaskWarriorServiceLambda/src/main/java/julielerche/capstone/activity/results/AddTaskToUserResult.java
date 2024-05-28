package julielerche.capstone.activity.results;

import julielerche.capstone.models.UserModel;

public class AddTaskToUserResult {
    private final UserModel user;

    private AddTaskToUserResult(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "AddTaskToUserResult{" +
                "user=" + user +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static AddTaskToUserResult.Builder builder() {
        return new AddTaskToUserResult.Builder();
    }

    public static class Builder {
        private UserModel user;

        public AddTaskToUserResult.Builder withUser(UserModel user) {
            this.user = user;
            return this;
        }

        public AddTaskToUserResult build() {
            return new AddTaskToUserResult(user);
        }
    }
}
