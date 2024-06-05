package julielerche.capstone.activity;

import julielerche.capstone.activity.requests.GetEncounterRequest;
import julielerche.capstone.activity.results.GetEncounterResult;

import julielerche.capstone.converters.EncounterToModelConverter;
import julielerche.capstone.dynamodb.EncounterDao;
import julielerche.capstone.dynamodb.models.Encounter;
import julielerche.capstone.models.EncounterModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the GetEncounterActivity for the TaskWarriorService's GetEncounter API.
 * <p>
 * This API allows the customer to get their encounter from the table.
 */
public class GetEncounterActivity {
    private final Logger log = LogManager.getLogger();
    private final EncounterDao encounterDao;

    /**
     * Instantiates a new GetEncounterActivity object.
     *
     * @param encounterDao EncounterDao to access the encounter table.
     */
    @Inject
    public GetEncounterActivity(EncounterDao encounterDao) {
        this.encounterDao = encounterDao;
    }
    /**
     * This method handles the incoming request by getting the encounter from the table.
     * <p>
     * It then returns the encounter from the table.
     * <p>
     *
     * @param getUserRequest request object containing the customerId
     * @return getUserResult result object containing the API defined
     */

    public GetEncounterResult handleRequest(final GetEncounterRequest getUserRequest) {
        log.info("Received GetEncounterRequest {}", getUserRequest);

        Encounter loadEncounter = encounterDao.loadEncounter(getUserRequest.getUserId());
        EncounterModel model = new EncounterToModelConverter().encounterToModel(loadEncounter);
        return GetEncounterResult.builder()
                .withEncounter(model)
                .build();
    }
}
