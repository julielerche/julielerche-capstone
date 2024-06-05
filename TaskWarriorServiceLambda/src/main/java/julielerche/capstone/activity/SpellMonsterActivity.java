package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.SpellMonsterRequest;
import julielerche.capstone.activity.results.SpellMonsterResult;

import julielerche.capstone.converters.AssetToModelConverter;
import julielerche.capstone.dynamodb.EncounterDao;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.Asset;
import julielerche.capstone.dynamodb.models.Encounter;
import julielerche.capstone.dynamodb.models.Spell;
import julielerche.capstone.dynamodb.models.User;
import julielerche.capstone.exceptions.InsufficentStatException;
import julielerche.capstone.models.AssetModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.inject.Inject;


/**
 * Implementation of the SpellMonsterActivity for the AssetWarriorService's AddAsset API.
 * <p>
 * This API allows the customer to deal damage to the current monster.
 */
public class SpellMonsterActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;
    private final EncounterDao encounterDao;

    /**
     * Instantiates a new SpellMonsterActivity object.
     *
     * @param userDao UserDao to access the user table.
     * @param encounterDao EncounterDao to access the encounter table.
     */
    @Inject
    public SpellMonsterActivity(UserDao userDao, EncounterDao encounterDao) {
        this.userDao = userDao;
        this.encounterDao = encounterDao;
    }
    /**
     * This method handles the incoming request by getting the user and monsters.
     * <p>
     * It then updates the user table and encounter table.
     * <p>
     *
     * @param attackMonsterRequest object containing the customerId and spell info
     * @return addAssetToUserResult result object containing the asset models of monsters
     */

    public SpellMonsterResult handleRequest(final SpellMonsterRequest attackMonsterRequest) {
        log.info("Received SpellMonsterRequest {}", attackMonsterRequest);
        User loadedUser = userDao.loadUser(attackMonsterRequest.getUserId());
        Spell loadedSpell = attackMonsterRequest.getSpell();
        if (loadedUser.getMana() < loadedSpell.getManaNeeded()) {
            throw new InsufficentStatException("Not enough mana for action");
        }
        Encounter loadedEncounter = encounterDao.loadEncounter(attackMonsterRequest.getUserId());

        ListIterator<Asset> iterator = loadedEncounter.getMonsterList().listIterator();
        while (iterator.hasNext()){
            Asset attackedMonster = iterator.next();
            attackedMonster.setCurrentHealth(attackedMonster.getCurrentHealth() - loadedSpell.getAttackPower());
            if (attackedMonster.getCurrentHealth() <= 0) {
                iterator.remove();
            }
        }

        loadedUser.setMana(loadedUser.getMana() - loadedSpell.getManaNeeded());
        int goldEarned = 0;
        if (loadedEncounter.getMonsterList().isEmpty()) {
            loadedUser.setGold(loadedUser.getGold() + 100);
            goldEarned = 100;
        }
        userDao.saveUser(loadedUser);
        encounterDao.saveEncounter(loadedEncounter);

        List<AssetModel> monsterModels = new ArrayList<>();
        for (Asset monster : loadedEncounter.getMonsterList()) {
            monsterModels.add(new AssetToModelConverter().assetToModel(monster));
        }
        return SpellMonsterResult.builder()
                .withAssets(monsterModels)
                .withGoldEarned(goldEarned)
                .build();
    }
}
