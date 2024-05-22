package julielerche.capstone.activity.results;

import julielerche.capstone.models.UserModel;

public class GetUserResult {
    private final UserModel user;

    private GetUserResult(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "GetUserResult{" +
                "user=" + user +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static GetUserResult.Builder builder() {
        return new GetUserResult.Builder();
    }

    public static class Builder {
        private UserModel user;

        public GetUserResult.Builder withUser(UserModel user) {
            this.user = user;
            return this;
        }

        public GetUserResult build() {
            return new GetUserResult(user);
        }
    }
}
