package julielerche.capstone.testHelper;

import julielerche.capstone.dynamodb.models.User;

import java.util.ArrayList;

public class UserCreater {
    private UserCreater() {

    }

    public static User generateUser(String userId, String displayName) {
        User newUser = new User();
        newUser.setUserId(userId);
        newUser.setDisplayName(displayName);
        newUser.setChores(new ArrayList<>());
        newUser.setDailies(new ArrayList<>());
        newUser.setToDos(new ArrayList<>());
        newUser.setInventory(new ArrayList<>());
        newUser.setGold(100);
        newUser.setHealth(100);
        newUser.setMana(100);
        newUser.setStamina(100);
        return  newUser;
    }
}
