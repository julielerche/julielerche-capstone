package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import julielerche.capstone.dynamodb.models.Task;

@JsonDeserialize(builder = DeleteTaskRequest.Builder.class)
public class DeleteTaskRequest {
    private final String userId;

    private final Task task;

    private DeleteTaskRequest(String userId, Task task) {
        this.userId = userId;
        this.task = task;
    }

    public String getUserId() {
        return userId;
    }

    public Task getTask() {
        return task;
    }

    @Override
    public String toString() {
        return "DeleteTaskRequest{" +
                "userId='" + userId + '\'' +
                "task='" + task + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static DeleteTaskRequest.Builder builder() {
        return new DeleteTaskRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private Task task;

        public DeleteTaskRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public DeleteTaskRequest.Builder withTask(Task task) {
            this.task = task;
            return this;
        }

        public DeleteTaskRequest build() {
            return new DeleteTaskRequest(userId, task);
        }
    }
}
