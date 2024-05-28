package julielerche.capstone.dynamodb.models;

public class Task {
    TaskType taskType;
    String taskName;
    Difficulty difficulty;
    Boolean completed;

    public Task(TaskType taskType, String taskName, Difficulty difficulty, Boolean completed) {
        this.taskType = taskType;
        this.taskName = taskName;
        this.difficulty = difficulty;
        this.completed = completed;
    }

    public Task() {
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
