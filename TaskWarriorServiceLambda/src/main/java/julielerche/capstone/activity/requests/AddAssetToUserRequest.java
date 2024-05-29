package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = AddAssetToUserRequest.Builder.class)
public class AddAssetToUserRequest {
    private final String userId;
    private final String assetType;
    private final Integer assetId;

    /**
     * Constructs a AddAssetToUserRequest from parameters.
     * @param userId The user id to add the item to.
     * @param assetType The type of asset to query.
     * @param assetId The id asset to query.
     */
    public AddAssetToUserRequest(String userId, String assetType, Integer assetId) {
        this.userId = userId;
        this.assetType = assetType;
        this.assetId = assetId;
    }

    public String getUserId() {
        return userId;
    }

    public String getAssetType() {
        return assetType;
    }

    public Integer getAssetId() {
        return assetId;
    }

    @Override
    public String toString() {
        return "AddAssetToUserRequest{" +
                "userId='" + userId + '\'' +
                "assetType='" + assetType + '\'' +
                ", assetId='" + assetId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static AddAssetToUserRequest.Builder builder() {
        return new AddAssetToUserRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private String assetType;
        private Integer assetId;

        public AddAssetToUserRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public AddAssetToUserRequest.Builder withAssetType(String assetType) {
            this.assetType = assetType;
            return this;
        }

        public AddAssetToUserRequest.Builder withAssetId(Integer assetId) {
            this.assetId = assetId;
            return this;
        }

        public AddAssetToUserRequest build() {
            return new AddAssetToUserRequest(userId, assetType, assetId);
        }
    }
}
