package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import julielerche.capstone.dynamodb.models.Asset;

@JsonDeserialize(builder = CreateAssetRequest.Builder.class)
public class CreateAssetRequest {
    private final String assetType;
    private final Integer assetId;
    private final Asset asset;

    private CreateAssetRequest(String assetType, Integer assetId, Asset asset) {
        this.assetId = assetId;
        this.assetType = assetType;
        this.asset = asset;
    }

    public String getAssetType() {
        return assetType;
    }

    public Integer getAssetId() {
        return assetId;
    }
    public Asset getAsset() {
        return asset;
    }

    @Override
    public String toString() {
        return "CreateAssetRequest{" +
                "assetType='" + assetType + '\'' +
                ", assetId='" + assetId + '\'' +
                ", asset='" + asset + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static CreateAssetRequest.Builder builder() {
        return new CreateAssetRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private Integer assetId;
        private String assetType;
        private Asset asset;

        public CreateAssetRequest.Builder withAssetId(Integer assetId) {
            this.assetId = assetId;
            return this;
        }

        public CreateAssetRequest.Builder withAssetType(String assetType) {
            this.assetType = assetType;
            return this;
        }

        public CreateAssetRequest.Builder withAsset(Asset asset) {
            this.asset = asset;
            return this;
        }

        public CreateAssetRequest build() {
            return new CreateAssetRequest(assetType, assetId, asset);
        }
    }
}
