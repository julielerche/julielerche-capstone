package julielerche.capstone.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import julielerche.capstone.dynamodb.models.Task;
import julielerche.capstone.exceptions.TaskSerializationException;

import java.util.List;

public class TaskConverter implements DynamoDBTypeConverter<String, List<Task>> {
    ObjectMapper mapper = new ObjectMapper();
    @Override
    public String convert(List<Task> object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new TaskSerializationException("Task failed to deserialize", e);
        }
    }

    @Override
    public List<Task> unconvert(String object) {
        TypeReference<List<Task>> ref = new TypeReference<>() {
        };
        try {
            return mapper.readValue(object, ref);
        } catch (JsonProcessingException e) {
            throw new TaskSerializationException("Task failed to be created", e);
        }
    }
}
