package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.CreateNewEncounterRequest;
import julielerche.capstone.activity.results.CreateNewEncounterResult;

import julielerche.capstone.converters.AssetToOtherTypesConverter;
import julielerche.capstone.converters.EncounterToModelConverter;
import julielerche.capstone.dynamodb.AssetDao;
import julielerche.capstone.dynamodb.EncounterDao;
import julielerche.capstone.dynamodb.models.Asset;
import julielerche.capstone.dynamodb.models.AssetFromTable;
import julielerche.capstone.dynamodb.models.Encounter;
import julielerche.capstone.models.EncounterModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.inject.Inject;


/**
 * Implementation of the CreateNewEncounterActivity for the TaskWarriorService's CreateNewEncounter API.
 * <p>
 * This API allows the customer to create a new asset in the table.
 */
public class CreateNewEncounterActivity {
    private final Logger log = LogManager.getLogger();
    private final EncounterDao encounterDao;
    private final AssetDao assetDao;

    /**
     * Instantiates a new CreateNewEncounterActivity object.
     *
     * @param encounterDao EncounterDao to access the asset table.
     * @param assetDao AssetDao to load monsters from.
     */
    @Inject
    public CreateNewEncounterActivity(EncounterDao encounterDao, AssetDao assetDao) {
        this.encounterDao = encounterDao;
        this.assetDao = assetDao;
    }
    /**
     * This method handles the incoming request by creating a new encounter
     * with the provided userId from the request.
     * <p>
     * It then returns the newly created encounter model.
     * <p>
     *
     * @param createEncounterRequest request object containing the asset pojo
     * @return createEncounterResult result object containing the API defined
     */

    public CreateNewEncounterResult handleRequest(final CreateNewEncounterRequest createEncounterRequest) {
        log.info("Received CreateNewEncounterRequest {}", createEncounterRequest);
        Encounter encounter = new Encounter();
        encounter.setUserId(createEncounterRequest.getUserId());
        List<AssetFromTable> monsterList = assetDao.getAllOfAssetType("MONSTER");
        System.out.print(monsterList);
        Random random = new Random();
        int numberOfMonsters = random.nextInt(3);
        if (numberOfMonsters == 0) {
            numberOfMonsters = 1;
        }
        List<AssetFromTable> chosenMonsters = new ArrayList<>();
        for (int k = 0; k <= numberOfMonsters; k++) {
            chosenMonsters.add(monsterList.get(random.nextInt(monsterList.size())));
        }

        List<Asset> convertedList = chosenMonsters.stream()
                .map(assetFromTable -> new AssetToOtherTypesConverter().convertAssetToAssigned(assetFromTable))
                .collect(Collectors.toList());
        encounter.setMonsterList(convertedList);

        encounterDao.saveEncounter(encounter);

        //converts the asset to the model to be returned
        EncounterModel model = new EncounterToModelConverter().encounterToModel(encounter);

        //returns the result with the model
        return CreateNewEncounterResult.builder()
                .withEncounter(model)
                .build();
    }
}
