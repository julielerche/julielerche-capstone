package julielerche.capstone.models;

import julielerche.capstone.dynamodb.models.Asset;
import julielerche.capstone.dynamodb.models.Task;

import java.util.List;

public class UserModel {
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
     *
     * @param userId the user id from the original
     * @param displayName the display name from the original
     * @param dailies the list of daily tasks from the original
     * @param chores the list of chore tasks from the original
     * @param toDos the list of to-do tasks from the original
     * @param inventory the list of assets from the original
     * @param health the current health of the original
     * @param stamina the current stamina of the original
     * @param mana the current mana of the original
     * @param gold the current gold of the original
     */

    public UserModel(String userId, String displayName, List<Task> dailies, List<Task> chores,
                     List<Task> toDos, List<Asset> inventory, Integer health, Integer stamina,
                     Integer mana, Integer gold) {
        this.userId = userId;
        this.displayName = displayName;
        this.dailies = dailies;
        this.chores = chores;
        this.toDos = toDos;
        this.inventory = inventory;
        this.health = health;
        this.stamina = stamina;
        this.mana = mana;
        this.gold = gold;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<Task> getDailies() {
        return dailies;
    }

    public List<Task> getChores() {
        return chores;
    }

    public List<Task> getToDos() {
        return toDos;
    }

    public List<Asset> getInventory() {
        return inventory;
    }

    public Integer getHealth() {
        return health;
    }

    public Integer getStamina() {
        return stamina;
    }

    public Integer getMana() {
        return mana;
    }

    public Integer getGold() {
        return gold;
    }

}
