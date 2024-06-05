package julielerche.capstone.converters;

import julielerche.capstone.dynamodb.models.Encounter;
import julielerche.capstone.models.EncounterModel;

public class EncounterToModelConverter {

    public EncounterModel encounterToModel(Encounter encounter) {
        return new EncounterModel(encounter.getUserId(), encounter.getMonsterList());
    }
}
