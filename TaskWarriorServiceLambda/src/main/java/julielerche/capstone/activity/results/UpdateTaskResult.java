package julielerche.capstone.activity.results;

import julielerche.capstone.models.UserModel;

public class UpdateTaskResult {
    private final UserModel userModel;

    private UpdateTaskResult(UserModel userModel) {
        this.userModel = userModel;
    }

    public UserModel getUserModel() {
        return this.userModel;
    }
    @Override
    public String toString() {
        return "UpdateTaskResult{" +
                "user=" + userModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static UpdateTaskResult.Builder builder() {
        return new UpdateTaskResult.Builder();
    }

    public static class Builder {
        private UserModel userModel;

        public UpdateTaskResult.Builder withUserModel(UserModel userModel) {
            this.userModel = userModel;
            return this;
        }

        public UpdateTaskResult build() {
            return new UpdateTaskResult(userModel);
        }
    }
}
