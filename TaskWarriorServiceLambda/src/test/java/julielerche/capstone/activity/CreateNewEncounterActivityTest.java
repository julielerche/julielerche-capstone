package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.CreateNewEncounterRequest;
import julielerche.capstone.activity.results.CreateNewEncounterResult;
import julielerche.capstone.dynamodb.AssetDao;
import julielerche.capstone.dynamodb.EncounterDao;
import julielerche.capstone.dynamodb.models.AssetFromTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.AssertJUnit.*;

public class CreateNewEncounterActivityTest {
    @Mock
    private EncounterDao encounterDao;
    @Mock
    private AssetDao assetDao;

    private CreateNewEncounterActivity createNewEncounterActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        createNewEncounterActivity = new CreateNewEncounterActivity(encounterDao, assetDao);
    }

    @Test
    public void handleRequest_givenUserId_createsNewEncounter() {
        //given
        AssetFromTable asset = new AssetFromTable();
        asset.setName("Dust Bunny");
        asset.setAssetId(1);
        asset.setAssetType("MONSTER");
        asset.setDescription("easy");
        asset.setHealthOrCost(10);
        when(assetDao.getAllOfAssetType("MONSTER")).thenReturn(List.of(asset, asset, asset));

        CreateNewEncounterRequest request = CreateNewEncounterRequest.builder()
                .withUserId("one")
                .build();

        //when
        CreateNewEncounterResult result = createNewEncounterActivity.handleRequest(request);

        //then
        verify(assetDao).getAllOfAssetType("MONSTER");

        assertEquals("one", result.getEncounter().getUserId());
        assertFalse(result.getEncounter().getMonsterList().isEmpty());
    }
}
