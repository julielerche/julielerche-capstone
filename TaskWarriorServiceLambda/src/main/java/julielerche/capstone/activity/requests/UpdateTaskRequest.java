package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import julielerche.capstone.dynamodb.models.Task;

@JsonDeserialize(builder = UpdateTaskRequest.Builder.class)
public class UpdateTaskRequest {
    private final String userId;

    private final Task task;
    private final String newName;
    private final String difficulty;

    private UpdateTaskRequest(String userId, Task task, String newName, String difficulty) {
        this.userId = userId;
        this.task = task;
        this.newName = newName;
        this.difficulty = difficulty;
    }

    public String getUserId() {
        return userId;
    }

    public Task getTask() {
        return task;
    }

    public String getNewName() {
        return newName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    @Override
    public String toString() {
        return "UpdateTaskRequest{" +
                "userId='" + userId + '\'' +
                "task='" + task + '\'' +
                "newName='" + newName + '\'' +
                "difficulty='" + difficulty + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static UpdateTaskRequest.Builder builder() {
        return new UpdateTaskRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private Task task;
        private String newName;
        private String difficulty;

        public UpdateTaskRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public UpdateTaskRequest.Builder withTask(Task task) {
            this.task = task;
            return this;
        }
        public UpdateTaskRequest.Builder withNewName(String newName) {
            this.newName = newName;
            return this;
        }
        public UpdateTaskRequest.Builder withDifficulty(String difficulty) {
            this.difficulty = difficulty;
            return this;
        }

        public UpdateTaskRequest build() {
            return new UpdateTaskRequest(userId, task, newName, difficulty);
        }
    }
}
