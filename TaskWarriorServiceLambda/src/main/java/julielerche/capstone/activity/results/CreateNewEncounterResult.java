package julielerche.capstone.activity.results;

import julielerche.capstone.models.EncounterModel;

public class CreateNewEncounterResult {
    private final EncounterModel encounter;

    private CreateNewEncounterResult(EncounterModel encounter) {
        this.encounter = encounter;
    }

    public EncounterModel getEncounter() {
        return encounter;
    }

    @Override
    public String toString() {
        return "CreateNewEncounterResult{" +
                "encounter=" + encounter +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static CreateNewEncounterResult.Builder builder() {
        return new CreateNewEncounterResult.Builder();
    }

    public static class Builder {
        private EncounterModel encounter;

        public CreateNewEncounterResult.Builder withEncounter(EncounterModel encounter) {
            this.encounter = encounter;
            return this;
        }

        public CreateNewEncounterResult build() {
            return new CreateNewEncounterResult(encounter);
        }
    }
}
