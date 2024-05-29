package julielerche.capstone.activity.results;

import julielerche.capstone.models.UserModel;

public class AddAssetToUserResult {
    private final UserModel user;

    private AddAssetToUserResult(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "AddAssetToUserResult{" +
                "user=" + user +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static AddAssetToUserResult.Builder builder() {
        return new AddAssetToUserResult.Builder();
    }

    public static class Builder {
        private UserModel user;

        public AddAssetToUserResult.Builder withUser(UserModel user) {
            this.user = user;
            return this;
        }

        public AddAssetToUserResult build() {
            return new AddAssetToUserResult(user);
        }
    }
}
