package julielerche.capstone.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import julielerche.capstone.dynamodb.models.Asset;
import julielerche.capstone.dynamodb.models.Task;
import julielerche.capstone.exceptions.TaskSerializationException;

import java.util.List;

public class AssetConverter implements DynamoDBTypeConverter<String, List<Asset>> {
    ObjectMapper mapper = new ObjectMapper();
    @Override
    public String convert(List<Asset> object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new TaskSerializationException("Task failed to deserialize", e);
        }
    }

    @Override
    public List<Asset> unconvert(String object) {
        TypeReference<List<Asset>> ref = new TypeReference<>() {
        };
        try {
            return mapper.readValue(object, ref);
        } catch (JsonProcessingException e) {
            throw new TaskSerializationException("Task failed to be created", e);
        }
    }
}
