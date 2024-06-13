package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.AttackMonsterRequest;
import julielerche.capstone.activity.results.AttackMonsterResult;
import julielerche.capstone.converters.AssetToOtherTypesConverter;
import julielerche.capstone.dynamodb.EncounterDao;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.*;
import julielerche.capstone.testHelper.UserCreater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.AssertJUnit.assertEquals;

public class AttackMonsterActivityTest {
    @Mock
    private EncounterDao encounterDao;
    @Mock
    private UserDao userDao;
    @Captor
    ArgumentCaptor<User> userCaptor;

    private AttackMonsterActivity attackMonsterActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        attackMonsterActivity = new AttackMonsterActivity(userDao, encounterDao);
    }

    @Test
    public void handleRequest_givenUserIdAndAttack_returnsMonsterDamagedAndUserStaminaChanged() {
        //given
        AssetFromTable asset = new AssetFromTable();
        asset.setName("Dust Bunny");
        asset.setAssetId(1);
        asset.setAssetType("MONSTER");
        asset.setDescription("easy");
        asset.setHealthOrCost(20);
        Encounter encounter = new Encounter();
        encounter.setUserId("one");
        List<Asset> monsterList = new ArrayList<>();
        monsterList.add(new AssetToOtherTypesConverter().convertAssetToAssigned(asset));
        encounter.setMonsterList(monsterList);

        Attack attack = new Attack(10,10,1);

        User user = UserCreater.generateUser("one", "Julie");

        when(userDao.loadUser("one")).thenReturn(user);
        when(encounterDao.loadEncounter("one")).thenReturn(encounter);

        AttackMonsterRequest request = AttackMonsterRequest.builder()
                .withUserId("one")
                .withAttackPower(10)
                .withStaminaNeeded(10)
                .withTarget(1)
                .build();

        //when
        AttackMonsterResult result = attackMonsterActivity.handleRequest(request);
        verify(userDao).saveUser(userCaptor.capture());
        //then

        assertEquals("Dust Bunny", result.getAssets().get(0).getName());
        assertEquals(10, (int) result.getAssets().get(0).getCurrentHealth());
        assertEquals((int) userCaptor.getValue().getStamina(), 90);
    }
}
