package julielerche.capstone.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import julielerche.capstone.dynamodb.models.Asset;
import julielerche.capstone.dynamodb.models.Monster;
import julielerche.capstone.exceptions.MonsterSerializationException;

import java.util.List;

public class MonsterListConverter implements DynamoDBTypeConverter<String, List<Asset>> {
    ObjectMapper mapper = new ObjectMapper();
    @Override
    public String convertList<Asset> object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new MonsterSerializationException("Monster failed to deserialize", e);
        }
    }

    @Override
    public List<Asset> unconvert(String object) {
        TypeReference<List<Asset>> ref = new TypeReference<>() {
        };
        try {
            return mapper.readValue(object, ref);
        } catch (JsonProcessingException e) {
            throw new MonsterSerializationException("Monster failed to be created", e);
        }
    }
}
