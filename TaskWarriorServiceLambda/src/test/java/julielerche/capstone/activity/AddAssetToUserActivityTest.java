package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.AddAssetToUserRequest;
import julielerche.capstone.activity.results.AddAssetToUserResult;
import julielerche.capstone.converters.AssetToOtherTypesConverter;
import julielerche.capstone.dynamodb.AssetDao;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.Asset;
import julielerche.capstone.dynamodb.models.AssetFromTable;
import julielerche.capstone.dynamodb.models.AssetType;
import julielerche.capstone.dynamodb.models.User;
import julielerche.capstone.exceptions.InvalidAssetException;
import julielerche.capstone.testHelper.UserCreater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertTrue;

public class AddAssetToUserActivityTest {
    @Mock
    private UserDao userDao;
    @Mock
    private AssetDao assetDao;

    private AddAssetToUserActivity addAssetToUserActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        addAssetToUserActivity = new AddAssetToUserActivity(userDao, assetDao);
    }

    @Test
    public void handleRequest_withAllInformation_returnsUserWithAssetInInventory() {
        //given
        User user = UserCreater.generateUser("one", "Julie");

        String assetType = "POTION";
        Integer assetId = 1;
        String name = "Health Potion";
        String description = "health + 10";
        Integer healthOrCost = 10;
        Asset expectedAsset = new Asset();
        expectedAsset.setAssetId(assetId);
        expectedAsset.setAssetType(AssetType.valueOf(assetType));
        expectedAsset.setName(name);
        expectedAsset.setDescription(description);
        expectedAsset.setHealthOrCost(healthOrCost);

        User returnedUser = user;
        returnedUser.setInventory(List.of(expectedAsset));

        when(assetDao.getSpecificAsset(assetType, assetId))
                .thenReturn(new AssetToOtherTypesConverter().assetToTableAsset(expectedAsset));
        when(userDao.loadUser("one")).thenReturn(user);
        when(userDao.addAssetToInventory(eq(user), any(AssetFromTable.class))).thenReturn(returnedUser);

        AddAssetToUserRequest request = AddAssetToUserRequest.builder()
                .withUserId("one")
                .withAssetId(expectedAsset.getAssetId())
                .withAssetType(assetType)
                .build();

        //when
        AddAssetToUserResult result = addAssetToUserActivity.handleRequest(request);

        //then
        assertTrue(result.getUser().getInventory().contains(expectedAsset));
    }

    @Test
    public void handleRequest_assetIsMonster_throwsException() {
        //given
        User user = UserCreater.generateUser("one", "Julie");

        String assetType = "MONSTER";
        Integer assetId = 1;
        String name = "Bunny";
        String description = "attack = 10";
        Integer healthOrCost = 10;
        Asset expectedAsset = new Asset();
        expectedAsset.setAssetId(assetId);
        expectedAsset.setAssetType(AssetType.valueOf(assetType));
        expectedAsset.setName(name);
        expectedAsset.setDescription(description);
        expectedAsset.setHealthOrCost(healthOrCost);

        when(assetDao.getSpecificAsset(assetType, assetId))
                .thenReturn(new AssetToOtherTypesConverter().assetToTableAsset(expectedAsset));
        when(userDao.loadUser("one")).thenReturn(user);

        AddAssetToUserRequest request = AddAssetToUserRequest.builder()
                .withUserId("one")
                .withAssetId(expectedAsset.getAssetId())
                .withAssetType(assetType)
                .build();

        //when + then
        assertThrows(InvalidAssetException.class, () -> addAssetToUserActivity.handleRequest(request));

    }
}
