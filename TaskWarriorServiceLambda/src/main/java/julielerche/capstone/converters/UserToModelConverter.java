package julielerche.capstone.converters;

import julielerche.capstone.dynamodb.models.User;
import julielerche.capstone.models.UserModel;

public class UserToModelConverter {

    /**
     * Converts a User to a UserModel representation.
     * @param user the user to convert into the model
     * @return model the model of the user to return
     */
    public UserModel userToModel(User user) {
        UserModel model = new UserModel(user.getUserId(), user.getDisplayName(),
                user.getDailies(), user.getChores(), user.getToDos(), user.getInventory(),
                user.getHealth(), user.getStamina(), user.getMana(), user.getGold());
        return model;
    }
}
