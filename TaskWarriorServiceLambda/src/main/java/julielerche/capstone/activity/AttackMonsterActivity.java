package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.AttackMonsterRequest;
import julielerche.capstone.activity.results.AttackMonsterResult;

import julielerche.capstone.converters.AssetToModelConverter;
import julielerche.capstone.converters.UserToModelConverter;
import julielerche.capstone.dynamodb.AssetDao;
import julielerche.capstone.dynamodb.EncounterDao;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.*;
import julielerche.capstone.exceptions.InsufficentGoldException;
import julielerche.capstone.exceptions.InsufficentStatException;
import julielerche.capstone.exceptions.InvalidAssetException;
import julielerche.capstone.models.AssetModel;
import julielerche.capstone.models.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the AttackMonsterActivity for the AssetWarriorService's AddAsset API.
 * <p>
 * This API allows the customer to deal damage to the current monster.
 */
public class AttackMonsterActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;
    private final EncounterDao encounterDao;

    /**
     * Instantiates a new AttackMonsterActivity object.
     *
     * @param userDao UserDao to access the user table.
     * @param encounterDao EncounterDao to access the encounter table.
     */
    @Inject
    public AttackMonsterActivity(UserDao userDao, EncounterDao encounterDao) {
        this.userDao = userDao;
        this.encounterDao = encounterDao;
    }
    /**
     * This method handles the incoming request by getting the user and monsters.
     * <p>
     * It then updates the user table and encounter table.
     * <p>
     *
     * @param attackMonsterRequest object containing the customerId and attack info
     * @return addAssetToUserResult result object containing the usermodel
     */

    public AttackMonsterResult handleRequest(final AttackMonsterRequest attackMonsterRequest) {
        log.info("Received AttackMonsterRequest {}", attackMonsterRequest);
        User loadedUser = userDao.loadUser(attackMonsterRequest.getUserId());
        Attack loadedAttack = attackMonsterRequest.getAttack();
        if (loadedUser.getStamina() < loadedAttack.getStaminaNeeded()) {
            throw new InsufficentStatException("Not enough stamina for action");
        }
        Encounter loadedEncounter = encounterDao.loadEncounter(attackMonsterRequest.getUserId());
        Asset attackedMonster = loadedEncounter.getMonsterList().get(loadedAttack.getTarget()-1);
        attackedMonster.setCurrentHealth(attackedMonster.getCurrentHealth() - loadedAttack.getAttackPower());
        if (attackedMonster.getCurrentHealth() <= 0) {
            loadedEncounter.getMonsterList().remove(attackedMonster);
        }
        loadedUser.setStamina(loadedUser.getStamina() - loadedAttack.getStaminaNeeded());
        userDao.saveUser(loadedUser);
        encounterDao.saveEncounter(loadedEncounter);
        List<AssetModel> monsterModels = new ArrayList<>();
        for (Asset monster : loadedEncounter.getMonsterList()) {
            monsterModels.add(new AssetToModelConverter().assetToModel(monster));
        }
        return AttackMonsterResult.builder()
                .withAssets(monsterModels)
                .build();
    }
}
