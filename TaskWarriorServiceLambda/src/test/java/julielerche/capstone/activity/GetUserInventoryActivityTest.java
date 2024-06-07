package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.GetUserInventoryRequest;
import julielerche.capstone.activity.results.GetUserInventoryResult;
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

public class GetUserInventoryActivityTest {
    @Mock
    private UserDao userDao;
    @Mock
    private AssetDao assetDao;

    private GetUserInventoryActivity getUserInventoryActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        getUserInventoryActivity = new GetUserInventoryActivity(userDao);
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

        GetUserInventoryRequest request = GetUserInventoryRequest.builder()
                .withUserId("one")
                .build();

        //when
        GetUserInventoryResult result = getUserInventoryActivity.handleRequest(request);

        //then
        assertTrue(result.getAssets().contains(expectedAsset));
    }
}
