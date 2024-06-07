package julielerche.capstone.activity.results;

import julielerche.capstone.dynamodb.models.Task;

public class MarkTaskAsCompletedResult {
    private final Task task;
    private final Integer gold;

    private MarkTaskAsCompletedResult(Task task, Integer gold) {
        this.task = task;
        this.gold = gold;
    }

    public Task getTasks() {
        return task;
    }
    public Integer getGold() {
        return gold;
    }

    @Override
    public String toString() {
        return "MarkTaskAsCompletedResult{" +
                "task=" + task +
                "gold=" + gold +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static MarkTaskAsCompletedResult.Builder builder() {
        return new MarkTaskAsCompletedResult.Builder();
    }

    public static class Builder {
        private Task task;
        private Integer gold;

        public MarkTaskAsCompletedResult.Builder withTask(Task task) {
            this.task = task;
            return this;
        }
        public MarkTaskAsCompletedResult.Builder withGold(Integer gold) {
            this.gold = gold;
            return this;
        }

        public MarkTaskAsCompletedResult build() {
            return new MarkTaskAsCompletedResult(task, gold);
        }
    }
}
