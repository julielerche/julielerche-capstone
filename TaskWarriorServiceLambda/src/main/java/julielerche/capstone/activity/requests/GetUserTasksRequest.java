package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetUserTasksRequest.Builder.class)
public class GetUserTasksRequest {
    private final String userId;
    private final String taskType;

    private GetUserTasksRequest(String userId, String taskType) {
        this.userId = userId;
        this.taskType = taskType;
    }

    public String getUserId() {
        return userId;
    }

    public String getTaskType() {
        return taskType;
    }

    @Override
    public String toString() {
        return "GetUserTasksRequest{" +
                "userId='" + userId + '\'' +
                "taskType='" + taskType + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static GetUserTasksRequest.Builder builder() {
        return new GetUserTasksRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private String taskType;

        public GetUserTasksRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public GetUserTasksRequest.Builder withTaskType(String taskType) {
            this.taskType = taskType;
            return this;
        }

        public GetUserTasksRequest build() {
            return new GetUserTasksRequest(userId, taskType);
        }
    }
}
