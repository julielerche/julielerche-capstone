package julielerche.capstone.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import julielerche.capstone.converters.MonsterListConverter;

import java.util.List;

@DynamoDBTable(tableName = "Encounter")
public class Encounter {
    String userId;
    List<Asset> monsterList;
    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    @DynamoDBAttribute(attributeName = "monsterList")
    @DynamoDBTypeConverted(converter = MonsterListConverter.class)
    public List<Asset> getMonsterList() {
        return monsterList;
    }

    public void setMonsterList(List<Asset> monsterList) {
        this.monsterList = monsterList;
    }
}
