package julielerche.capstone.activity.results;

import julielerche.capstone.models.UserModel;

public class MarkTaskAsCompletedResult {
    private final UserModel userModel;
    private final Integer gold;

    private MarkTaskAsCompletedResult(UserModel userModel, Integer gold) {
        this.userModel = userModel;
        this.gold = gold;
    }

    public UserModel getUserModel() {
        return userModel;
    }
    public Integer getGold() {
        return gold;
    }

    @Override
    public String toString() {
        return "MarkTaskAsCompletedResult{" +
                "userModel=" + userModel +
                "gold=" + gold +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static MarkTaskAsCompletedResult.Builder builder() {
        return new MarkTaskAsCompletedResult.Builder();
    }

    public static class Builder {
        private UserModel userModel;
        private Integer gold;

        public MarkTaskAsCompletedResult.Builder withUser(UserModel userModel) {
            this.userModel = userModel;
            return this;
        }
        public MarkTaskAsCompletedResult.Builder withGold(Integer gold) {
            this.gold = gold;
            return this;
        }

        public MarkTaskAsCompletedResult build() {
            return new MarkTaskAsCompletedResult(userModel, gold);
        }
    }
}
