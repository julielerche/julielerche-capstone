package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.CreateAssetRequest;
import julielerche.capstone.activity.results.CreateAssetResult;
import julielerche.capstone.converters.AssetToOtherTypesConverter;
import julielerche.capstone.dynamodb.AssetDao;
import julielerche.capstone.dynamodb.models.Asset;
import julielerche.capstone.dynamodb.models.AssetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.AssertJUnit.assertEquals;

public class CreateAssetActivityTest {
    @Mock
    private AssetDao assetDao;

    private CreateAssetActivity createAssetActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        createAssetActivity = new CreateAssetActivity(assetDao);
    }

    @Test
    public void handleRequest_withAllInformation_createsNewAsset() {
        //given
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
        when(assetDao.saveAsset(expectedAsset))
                .thenReturn(new AssetToOtherTypesConverter().assetToTableAsset(expectedAsset));

        CreateAssetRequest request = CreateAssetRequest.builder()
                .withAsset(expectedAsset)
                .withAssetId(expectedAsset.getAssetId())
                .withAssetType(assetType)
                .build();

        //when
        CreateAssetResult result = createAssetActivity.handleRequest(request);

        //then
        verify(assetDao).saveAsset(any(Asset.class));

        assertEquals(name, result.getAsset().getName());
        assertEquals(assetId, result.getAsset().getAssetId());
    }
}
