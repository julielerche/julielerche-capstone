package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import julielerche.capstone.dynamodb.models.Task;

@JsonDeserialize(builder = AddTaskToUserRequest.Builder.class)
public class AddTaskToUserRequest {
    private final String userId;
    private final String taskType;
    private final Task task;

    /**
     * Constructs the request object.
     * @param userId unique id for the user.
     * @param taskType task category to add to the list of.
     * @param task the task to add.
     */
    public AddTaskToUserRequest(String userId, String taskType, Task task) {
        this.userId = userId;
        this.taskType = taskType;
        this.task = task;
    }

    public String getUserId() {
        return userId;
    }

    public String getTaskType() {
        return taskType;
    }

    public Task getTask() {
        return task;
    }
    @Override
    public String toString() {
        return "AddTaskToUserRequest{" +
                "userId='" + userId + '\'' +
                "taskType='" + taskType + '\'' +
                ", task='" + task + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static AddTaskToUserRequest.Builder builder() {
        return new AddTaskToUserRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private String taskType;
        private Task task;

        public AddTaskToUserRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public AddTaskToUserRequest.Builder withTaskType(String taskType) {
            this.taskType = taskType;
            return this;
        }

        public AddTaskToUserRequest.Builder withTask(Task task) {
            this.task = task;
            return this;
        }

        public AddTaskToUserRequest build() {
        return new AddTaskToUserRequest(userId, taskType, task);
        }
    }
}
