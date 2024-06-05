package julielerche.capstone.converters;

import julielerche.capstone.dynamodb.models.Encounter;
import julielerche.capstone.models.EncounterModel;

public class EncounterToModelConverter {
    /**
     * Converts an encounter to a model.
     * @param encounter the encounter to convert
     * @return the encountermodel.
     */
    public EncounterModel encounterToModel(Encounter encounter) {
        return new EncounterModel(encounter.getUserId(), encounter.getMonsterList());
    }
}
