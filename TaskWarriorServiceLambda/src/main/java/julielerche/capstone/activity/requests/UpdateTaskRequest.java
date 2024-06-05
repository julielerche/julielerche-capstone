package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import julielerche.capstone.dynamodb.models.Task;

@JsonDeserialize(builder = UpdateTaskRequest.Builder.class)
public class UpdateTaskRequest {
    private final String userId;
    private final Task task;
    private final String newName;
    private final String newDifficulty;
    private final String newType;

    private UpdateTaskRequest(String userId, Task task, String newName, String newDifficulty, String newType) {
        this.userId = userId;
        this.task = task;
        this.newName = newName;
        this.newDifficulty = newDifficulty;
        this.newType = newType;
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


    public String getNewDifficulty() {
        return newDifficulty;
    }

    public String getNewType() {
        return newType;
    }

    @Override
    public String toString() {
        return "UpdateTaskRequest{" +
                "userId='" + userId + '\'' +
                "task='" + task + '\'' +
                "newName='" + newName + '\'' +
                "newDifficulty='" + newDifficulty + '\'' +
                "newType='" + newType + '\'' +
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
        private String newDifficulty;
        private String newType;

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
        public UpdateTaskRequest.Builder withNewDifficulty(String newDifficulty) {
            this.newDifficulty = newDifficulty;
            return this;
        }

        public UpdateTaskRequest.Builder withNewType(String newType) {
            this.newType = newType;
            return this;
        }

        public UpdateTaskRequest build() {
            return new UpdateTaskRequest(userId, task, newName, newDifficulty, newType);
        }
    }
}
