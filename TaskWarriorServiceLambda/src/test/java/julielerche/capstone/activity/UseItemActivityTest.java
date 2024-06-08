package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.UseItemRequest;
import julielerche.capstone.activity.results.UseItemResult;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class UseItemActivityTest {
    @Mock
    private UserDao userDao;
    @Mock
    private AssetDao assetDao;

    private UseItemActivity useItemActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        useItemActivity = new UseItemActivity(userDao);
    }

    @Test
    public void handleRequest_withAllInformation_returnsUserWithAssetInInventory() {
        //given
        User user = UserCreater.generateUser("one", "Julie");
        user.setHealth(50);


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
        returnedUser.setHealth(60);
        List<Asset> inventory = new ArrayList<>();
        inventory.add(expectedAsset);

        returnedUser.setInventory(inventory);

        when(userDao.loadUser("one")).thenReturn(user);
        when(userDao.useItem(user, expectedAsset)).thenReturn(returnedUser);
        UseItemRequest request = UseItemRequest.builder()
                .withUserId("one")
                .withAsset(expectedAsset)
                .build();

        //when
        UseItemResult result = useItemActivity.handleRequest(request);

        //then
        assertEquals(60, (int) result.getUser().getHealth());
    }

    @Test
    public void handleRequest_assetNotInInventory_throwsException() {
        //given
        User user = UserCreater.generateUser("one", "Julie");
        user.setHealth(50);


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
        returnedUser.setHealth(60);
        List<Asset> inventory = new ArrayList<>();
        inventory.add(expectedAsset);

        returnedUser.setInventory(inventory);

        when(userDao.loadUser("one")).thenReturn(user);
        when(userDao.useItem(user, expectedAsset)).thenThrow(InvalidAssetException.class);
        UseItemRequest request = UseItemRequest.builder()
                .withUserId("one")
                .withAsset(expectedAsset)
                .build();

        //when + then
        assertThrows(InvalidAssetException.class, () -> useItemActivity.handleRequest(request));

    }
}
