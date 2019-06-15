
package com.lexxdigital.easyfooduserapps.model.add_favourites_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddFavouristeResquest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("entity_id")
    @Expose
    private String entityId;
    @SerializedName("entity_type")
    @Expose
    private String entityType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

}
