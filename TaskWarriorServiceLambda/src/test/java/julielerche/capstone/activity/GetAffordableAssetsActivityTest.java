package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.GetAffordableAssetsRequest;
import julielerche.capstone.activity.results.GetAffordableAssetsResult;
import julielerche.capstone.dynamodb.AssetDao;
import julielerche.capstone.dynamodb.UserDao;
import julielerche.capstone.dynamodb.models.AssetFromTable;
import julielerche.capstone.dynamodb.models.User;
import julielerche.capstone.testHelper.UserCreater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.AssertJUnit.assertEquals;

public class GetAffordableAssetsActivityTest {
    @Mock
    private AssetDao assetDao;
    @Mock
    private UserDao userDao;

    private GetAffordableAssetsActivity getAffordableAssetsActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        getAffordableAssetsActivity = new GetAffordableAssetsActivity(assetDao, userDao);
    }

    @Test
    public void handleRequest_withLargeAmountOfGold_returnsCorrectAssets() {
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
        when(assetDao.getAffordableAssets(100)).thenReturn(List.of(expectedAsset));
        when(userDao.loadUser("one")).thenReturn(user);

        GetAffordableAssetsRequest request = GetAffordableAssetsRequest.builder()
                .withUserId("one")
                .build();

        //when
        GetAffordableAssetsResult result = getAffordableAssetsActivity.handleRequest(request);

        //then
        assertEquals(name, result.getAssets().get(0).getName());
    }
}
