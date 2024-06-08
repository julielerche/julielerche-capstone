package julielerche.capstone.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import julielerche.capstone.dynamodb.models.Asset;
import julielerche.capstone.dynamodb.models.AssetFromTable;
import julielerche.capstone.dynamodb.models.AssetType;
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
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class AssetDaoTest {
    @Mock
    private DynamoDBMapper mapper;
    @Mock
    private PaginatedScanList<AssetFromTable> assetPageScan;

    @InjectMocks
    private AssetDao assetDao;

    @BeforeEach
    void setUp() {
        openMocks(this);
        assetDao = new AssetDao(mapper);
    }

    @Test
    public void saveAsset_givenNormalAsset_returnsAssetFromTable() {
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

        //when
        AssetFromTable result = assetDao.saveAsset(expectedAsset);

        //then
        verify(mapper, times(1)).save(any());
        assertEquals(assetType, result.getAssetType());
        assertEquals(assetId, result.getAssetId());
        assertEquals(assetId, result.getAssetId());
        assertEquals(description, result.getDescription());
        assertEquals(healthOrCost, result.getHealthOrCost());
    }

    @Test
    public void getSpecificAsset_givenNormalAsset_returnsAssetFromTable() {
        //given
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
        when(mapper.load(any())).thenReturn(expectedAsset);

        //when
        AssetFromTable result = assetDao.getSpecificAsset(assetType, assetId);

        //then
        verify(mapper, times(1)).load(any());
        assertEquals(assetType, result.getAssetType());
        assertEquals(assetId, result.getAssetId());
        assertEquals(assetId, result.getAssetId());
        assertEquals(description, result.getDescription());
        assertEquals(healthOrCost, result.getHealthOrCost());
    }

    @Test
    public void getAllOfAssetType_givenAssetType_returnsListOfAssets() {
        //given
        String assetType = "POTION";

        when(mapper.scan(eq(AssetFromTable.class), any())).thenReturn(assetPageScan);

        //when
        List<AssetFromTable> result = assetDao.getAllOfAssetType(assetType);

        //then
        verify(mapper, times(1)).scan(eq(AssetFromTable.class), any());
        assertNotNull(result);
    }

    @Test
    public void getAffordableAssets_givenGoldAmount_returnsListOfAssets() {
        //given
        int gold = 20;

        when(mapper.scan(eq(AssetFromTable.class), any())).thenReturn(assetPageScan);

        //when
        List<AssetFromTable> result = assetDao.getAffordableAssets(gold);

        //then
        verify(mapper, times(1)).scan(eq(AssetFromTable.class), any());
        assertNotNull(result);
    }
}
