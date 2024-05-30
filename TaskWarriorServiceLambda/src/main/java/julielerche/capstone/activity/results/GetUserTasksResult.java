package julielerche.capstone.activity.results;

import julielerche.capstone.dynamodb.models.Task;
import julielerche.capstone.models.UserModel;

import java.util.List;

public class GetUserTasksResult {
    private final List<Task> tasks;

    private GetUserTasksResult(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return "GetUserTasksResult{" +
                "tasks=" + tasks +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static GetUserTasksResult.Builder builder() {
        return new GetUserTasksResult.Builder();
    }

    public static class Builder {
        private List<Task> tasks;

        public GetUserTasksResult.Builder withTasks(List<Task> tasks) {
            this.tasks = tasks;
            return this;
        }

        public GetUserTasksResult build() {
            return new GetUserTasksResult(tasks);
        }
    }
}
