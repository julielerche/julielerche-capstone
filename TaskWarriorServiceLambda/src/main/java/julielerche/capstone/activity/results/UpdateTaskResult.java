package julielerche.capstone.activity.results;

import julielerche.capstone.dynamodb.models.Task;

import java.util.List;

public class UpdateTaskResult {
    private final Task task;

    private UpdateTaskResult(Task task) {
        this.task = task;
    }

    public Task getTasks() {
        return task;
    }

    @Override
    public String toString() {
        return "UpdateTaskResult{" +
                "task=" + task +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static UpdateTaskResult.Builder builder() {
        return new UpdateTaskResult.Builder();
    }

    public static class Builder {
        private Task task;

        public UpdateTaskResult.Builder withTasks(Task task) {
            this.task = task;
            return this;
        }

        public UpdateTaskResult build() {
            return new UpdateTaskResult(task);
        }
    }
}
