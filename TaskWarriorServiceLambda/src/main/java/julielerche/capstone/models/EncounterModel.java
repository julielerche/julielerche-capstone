package julielerche.capstone.models;

import julielerche.capstone.dynamodb.models.Asset;

import java.util.List;

public class EncounterModel {
    String userId;
    List<Asset> monsterList;

    /**
     * Constructs an Encounter Model to obscure dynamodata.
     * @param userId the userid of the user fighting the monsters.
     * @param monsterList the monsters being fought.
     */
    public EncounterModel(String userId, List<Asset> monsterList) {
        this.userId = userId;
        this.monsterList = monsterList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Asset> getMonsterList() {
        return monsterList;
    }

    public void setMonsterList(List<Asset> monsterList) {
        this.monsterList = monsterList;
    }
}
