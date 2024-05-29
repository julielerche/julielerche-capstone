package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetAllOfAssetTypeRequest.Builder.class)
public class GetAllOfAssetTypeRequest {
    private final String assetType;
    private final String assetType2;

    private GetAllOfAssetTypeRequest(String assetType, String assetType2) {
        this.assetType = assetType;
        this.assetType2 = assetType2;
    }

    public String getAssetType() {
        return assetType;
    }
    public String getAssetType2() {
        return assetType2;
    }

    @Override
    public String toString() {
        return "GetAllOfAssetTypeRequest{" +
                "assetType='" + assetType + '\'' +
                "assetType2='" + assetType2 + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static GetAllOfAssetTypeRequest.Builder builder() {
        return new GetAllOfAssetTypeRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String assetType;
        private String assetType2;

        public GetAllOfAssetTypeRequest.Builder withAssetType(String assetType) {
            this.assetType = assetType;
            return this;
        }
        public GetAllOfAssetTypeRequest.Builder withAssetType2(String assetType2) {
            this.assetType2 = assetType2;
            return this;
        }

        public GetAllOfAssetTypeRequest build() {
            return new GetAllOfAssetTypeRequest(assetType, assetType2);
        }
    }
}
