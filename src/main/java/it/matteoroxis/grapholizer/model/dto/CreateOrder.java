package it.matteoroxis.grapholizer.model.dto;

import java.util.List;

public class CreateOrder {

    private String userId;
    private List<String> productIds;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }
}
