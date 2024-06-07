package julielerche.capstone.activity.results;

import julielerche.capstone.models.EncounterModel;

public class GetEncounterResult {
    private final EncounterModel encounter;

    private GetEncounterResult(EncounterModel encounter) {
        this.encounter = encounter;
    }

    public EncounterModel getEncounter() {
        return encounter;
    }

    @Override
    public String toString() {
        return "GetEncounterResult{" +
                "encounter=" + encounter +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static GetEncounterResult.Builder builder() {
        return new GetEncounterResult.Builder();
    }

    public static class Builder {
        private EncounterModel encounter;

        public GetEncounterResult.Builder withEncounter(EncounterModel encounter) {
            this.encounter = encounter;
            return this;
        }

        public GetEncounterResult build() {
            return new GetEncounterResult(encounter);
        }
    }
}
