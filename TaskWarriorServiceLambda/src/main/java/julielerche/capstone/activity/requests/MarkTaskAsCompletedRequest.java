package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import julielerche.capstone.dynamodb.models.Task;

@JsonDeserialize(builder = MarkTaskAsCompletedRequest.Builder.class)
public class MarkTaskAsCompletedRequest {
    private final String userId;
    private final Task task;

    private MarkTaskAsCompletedRequest(String userId, Task task) {
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
        return "MarkTaskAsCompletedRequest{" +
                "userId='" + userId + '\'' +
                "task='" + task + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static MarkTaskAsCompletedRequest.Builder builder() {
        return new MarkTaskAsCompletedRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private Task task;
        public MarkTaskAsCompletedRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public MarkTaskAsCompletedRequest.Builder withTask(Task task) {
            this.task = task;
            return this;
        }

        public MarkTaskAsCompletedRequest build() {
            return new MarkTaskAsCompletedRequest(userId, task);
        }
    }
}
