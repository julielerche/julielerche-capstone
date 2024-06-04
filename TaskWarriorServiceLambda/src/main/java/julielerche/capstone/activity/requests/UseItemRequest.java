package julielerche.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import julielerche.capstone.dynamodb.models.Asset;

@JsonDeserialize(builder = UseItemRequest.Builder.class)
public class UseItemRequest {
    private final String userId;
    private final Asset asset;

    /**
     * Constructs a UseItemRequest from parameters.
     * @param userId The user id to add the item to.
     * @param asset The asset to use.
     */
    public UseItemRequest(String userId, Asset asset) {
        this.userId = userId;
        this.asset = asset;
    }

    public String getUserId() {
        return userId;
    }

    public Asset getAsset() {
        return asset;
    }

    @Override
    public String toString() {
        return "UseItemRequest{" +
                "userId='" + userId + '\'' +
                "asset='" + asset + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static UseItemRequest.Builder builder() {
        return new UseItemRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private Asset asset;

        public UseItemRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public UseItemRequest.Builder withAsset(Asset asset) {
            this.asset = asset;
            return this;
        }
        public UseItemRequest build() {
            return new UseItemRequest(userId, asset);
        }
    }
}
