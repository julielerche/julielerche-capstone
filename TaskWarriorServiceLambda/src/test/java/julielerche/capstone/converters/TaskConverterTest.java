package julielerche.capstone.converters;

import julielerche.capstone.dynamodb.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

public class TaskConverterTest {
    private TaskConverter taskConverter;

    @BeforeEach
    void setUp() {
        taskConverter = new TaskConverter();
    }

    @Test
    public void convert_assetToJSON_correctInformation() {
        Task task = new Task();
        task.setTaskType(TaskType.DAILY);
        task.setTaskName("Dishes");
        task.setDifficulty(Difficulty.EASY);
        task.setCompleted(false);

        String expectedString ="[{\"taskType\":\"DAILY\",\"taskName\":\"Di" +
                "shes\",\"difficulty\":\"EASY\",\"completed\":false}]";

        String result = taskConverter.convert(List.of(task));
        assertEquals(expectedString, result);
    }

    @Test
    public void unconvert_JSONtoAsset_hasCorrectInformation() {
        Task task = new Task();
        task.setTaskType(TaskType.DAILY);
        task.setTaskName("Dishes");
        task.setDifficulty(Difficulty.EASY);
        task.setCompleted(false);

        String inputString ="[{\"taskType\":\"DAILY\",\"taskName\":\"Di" +
                "shes\",\"difficulty\":\"EASY\",\"completed\":false}]";

        List<Task> result = taskConverter.unconvert(inputString);
        assertEquals(List.of(task), result);
    }
}
