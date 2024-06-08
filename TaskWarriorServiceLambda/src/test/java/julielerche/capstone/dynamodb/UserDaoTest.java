package julielerche.capstone.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import julielerche.capstone.activity.requests.AddTaskToUserRequest;
import julielerche.capstone.activity.requests.DeleteTaskRequest;
import julielerche.capstone.converters.AssetToOtherTypesConverter;
import julielerche.capstone.dynamodb.AssetDao;
import julielerche.capstone.dynamodb.models.*;
import julielerche.capstone.testHelper.UserCreater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.AssertJUnit.*;

public class UserDaoTest {
    @Mock
    private DynamoDBMapper mapper;
    @Mock
    private PaginatedScanList<AssetFromTable> assetPageScan;

    @InjectMocks
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        openMocks(this);
        userDao = new UserDao(mapper);
    }

    @Test
    public void saveUser_correctUser_savesUserToTable() {
        //given
        User user = UserCreater.generateUser("one", "Julie");

        //when
        userDao.saveUser(user);

        //then
        verify(mapper, times(1)).save(user);
    }

    @Test
    public void loadUser_correctUser_loadsUserFromTable() {
        //given
        User user = UserCreater.generateUser("one", "Julie");
        when(mapper.load(User.class, "one")).thenReturn(user);
        //when
        User result = userDao.loadUser("one");

        //then
        verify(mapper, times(1)).load(User.class, "one");
        assertEquals(user, result);
    }

    @Test
    public void saveTaskToUser_correctUser_addsTask() {
        //given
        Task task = new Task(TaskType.DAILY, "Dishes", Difficulty.EASY, false);
        AddTaskToUserRequest request = new AddTaskToUserRequest("one", "DAILY", task);
        User user = UserCreater.generateUser("one", "Julie");
        when(mapper.load(User.class, "one")).thenReturn(user);
        //when
        User result = userDao.saveTaskToUser(request);

        //then
        verify(mapper, times(1)).load(User.class, "one");
        assertEquals(user, result);
        assertEquals(task, result.getDailies().get(0));
    }

    @Test
    public void deleteTaskFromUser_correctUser_deletesTask() {
        //given
        Task task = new Task(TaskType.DAILY, "Dishes", Difficulty.EASY, false);
        DeleteTaskRequest request = new DeleteTaskRequest.Builder()
                .withUserId("one")
                .withTask(task)
                .build();
        User user = UserCreater.generateUser("one", "Julie");
        List<Task> taskList = user.getDailies();
        taskList.add(task);
        user.setDailies(taskList);
        when(mapper.load(User.class, "one")).thenReturn(user);
        //when
        User result = userDao.deleteTaskFromUser(request);

        //then
        verify(mapper, times(1)).load(User.class, "one");
        assertTrue(result.getDailies().isEmpty());
    }

    @Test
    public void addAssetToInventory_assetAndUserCorrect_savesUserToTable() {
        //given
        User user = UserCreater.generateUser("one", "Julie");
        String assetType = "POTION";
        Integer assetId = 1;
        String name = "Health Potion";
        String description = "health + 10";
        Integer healthOrCost = 10;
        AssetFromTable expectedAsset = new AssetFromTable();
        expectedAsset.setAssetId(assetId);
        expectedAsset.setAssetType(assetType);
        expectedAsset.setName(name);
        expectedAsset.setDescription(description);
        expectedAsset.setHealthOrCost(healthOrCost);
        Asset asset = new AssetToOtherTypesConverter()
                .convertAssetToAssigned(expectedAsset);

        //when
        User result = userDao.addAssetToInventory(user, expectedAsset);

        //then
        verify(mapper, times(1)).save(user);
        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(asset, result.getInventory().get(0));
    }

    @Test
    public void useItem_assetAndUserCorrect_deletesAndUsesItem() {
        //given
        User user = UserCreater.generateUser("one", "Julie");
        String assetType = "POTION";
        Integer assetId = 1;
        String name = "Health Potion";
        String description = "health + 10";
        Integer healthOrCost = 10;
        AssetFromTable expectedAsset = new AssetFromTable();
        expectedAsset.setAssetId(assetId);
        expectedAsset.setAssetType(assetType);
        expectedAsset.setName(name);
        expectedAsset.setDescription(description);
        expectedAsset.setHealthOrCost(healthOrCost);
        Asset asset = new AssetToOtherTypesConverter()
                .convertAssetToAssigned(expectedAsset);
        List<Asset> inventory = user.getInventory();
        inventory.add(asset);
        user.setInventory(inventory);
        user.setHealth(50);

        //when
        User result = userDao.useItem(user, asset);

        //then
        verify(mapper, times(1)).save(user);
        assertEquals(user.getUserId(), result.getUserId());
        assertTrue(user.getInventory().isEmpty());
        assertEquals(60, (int) result.getHealth());
    }
}