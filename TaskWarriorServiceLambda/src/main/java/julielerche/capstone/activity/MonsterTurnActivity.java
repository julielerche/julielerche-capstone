package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.MonsterTurnRequest;
import julielerche.capstone.activity.results.MonsterTurnResult;

import julielerche.capstone.converters.AssetToModelConverter;
import julielerche.capstone.converters.UserToModelConverter;
import julielerche.capstone.dynamodb.EncounterDao;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.Asset;
import julielerche.capstone.dynamodb.models.Encounter;
import julielerche.capstone.dynamodb.models.Spell;
import julielerche.capstone.dynamodb.models.User;
import julielerche.capstone.exceptions.InsufficentStatException;
import julielerche.capstone.exceptions.UserOutOfHealthException;
import julielerche.capstone.models.AssetModel;
import julielerche.capstone.models.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.inject.Inject;


/**
 * Implementation of the MonsterTurnActivity for the AssetWarriorService's AddAsset API.
 * <p>
 * This API allows the monsters to deal damage to the user.
 */
public class MonsterTurnActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;
    private final EncounterDao encounterDao;

    /**
     * Instantiates a new MonsterTurnActivity object.
     *
     * @param userDao UserDao to access the user table.
     * @param encounterDao EncounterDao to access the encounter table.
     */
    @Inject
    public MonsterTurnActivity(UserDao userDao, EncounterDao encounterDao) {
        this.userDao = userDao;
        this.encounterDao = encounterDao;
    }
    /**
     * This method handles the incoming request by getting the user and monsters.
     * <p>
     * It then updates the user table and encounter table with damage.
     * <p>
     *
     * @param attackMonsterRequest object containing the customerId
     * @return addAssetToUserResult result object containing the damaged user.
     */

    public MonsterTurnResult handleRequest(final MonsterTurnRequest attackMonsterRequest) {
        log.info("Received MonsterTurnRequest {}", attackMonsterRequest);
        User loadedUser = userDao.loadUser(attackMonsterRequest.getUserId());

        Encounter loadedEncounter = encounterDao.loadEncounter(attackMonsterRequest.getUserId());
        int damageDealt = 0;
        for (Asset monster : loadedEncounter.getMonsterList()) {
            damageDealt += monster.getAttackPower();
        }

        loadedUser.setHealth(loadedUser.getHealth() - damageDealt);

        userDao.saveUser(loadedUser);

        if (loadedUser.getHealth() < 0) {
            throw new UserOutOfHealthException("Health below zero!");
        }
        UserModel model = new UserToModelConverter().userToModel(loadedUser);

        return MonsterTurnResult.builder()
                .withUser(model)
                .build();
    }
}
