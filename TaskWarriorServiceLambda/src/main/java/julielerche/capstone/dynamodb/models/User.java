package julielerche.capstone.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import julielerche.capstone.converters.AssetConverter;
import julielerche.capstone.converters.TaskConverter;

import java.util.ArrayList;
import java.util.List;
@DynamoDBTable(tableName = "Users")
public class User {
    private final String userId;
    private String displayName;
    private List<Task> dailies;
    private List<Task> chores;
    private List<Task> toDos;
    private List<Asset> inventory;
    private Integer health;
    private Integer stamina;
    private Integer mana;
    private Integer gold;

    /**
     * Constructs a user object with default values from the given data.
     * @param userId the userId from the request
     * @param displayName the display name from the request
     */
    public User(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
        this.dailies = new ArrayList<>();
        this.chores = new ArrayList<>();
        this.toDos = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.health = 100;
        this.stamina = 100;
        this.mana = 100;
        this.gold = 100;
    }

    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    @DynamoDBAttribute(attributeName = "displayName")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    @DynamoDBAttribute(attributeName = "dailies")
    @DynamoDBTypeConverted(converter = TaskConverter.class)
    public List<Task> getDailies() {
        return dailies;
    }

    public void setDailies(List<Task> dailies) {
        this.dailies = dailies;
    }
    @DynamoDBAttribute(attributeName = "chores")
    @DynamoDBTypeConverted(converter = TaskConverter.class)
    public List<Task> getChores() {
        return chores;
    }

    public void setChores(List<Task> chores) {
        this.chores = chores;
    }
    @DynamoDBAttribute(attributeName = "toDos")
    @DynamoDBTypeConverted(converter = TaskConverter.class)
    public List<Task> getToDos() {
        return toDos;
    }

    public void setToDos(List<Task> toDos) {
        this.toDos = toDos;
    }
    @DynamoDBAttribute(attributeName = "inventory")
    @DynamoDBTypeConverted(converter = AssetConverter.class)
    public List<Asset> getInventory() {
        return inventory;
    }

    public void setInventory(List<Asset> inventory) {
        this.inventory = inventory;
    }
    @DynamoDBAttribute(attributeName = "health")
    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }
    @DynamoDBAttribute(attributeName = "stamina")
    public Integer getStamina() {
        return stamina;
    }

    public void setStamina(Integer stamina) {
        this.stamina = stamina;
    }
    @DynamoDBAttribute(attributeName = "mana")
    public Integer getMana() {
        return mana;
    }

    public void setMana(Integer mana) {
        this.mana = mana;
    }
    @DynamoDBAttribute(attributeName = "gold")
    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }
}
