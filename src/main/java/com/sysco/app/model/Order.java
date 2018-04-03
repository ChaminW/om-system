package com.sysco.app.model;

import com.sysco.app.annotation.CheckValidUntilDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "orders")
public class Order {

    @Id
    private String id;
    private String restaurantId;
    private String deliveryAddressId;
    private String deliveryMethod;
    private String status;
    private Long createdDate;
    @CheckValidUntilDate
    private Long validUntil;
    private Long lastUpdatedAt;
    private String description;
    private List<String> itemIdList;

    public Order() {
    }

    public Order(String restaurantId, String deliveryAddressId, String deliveryMethod, String status, Long createdDate, Long validUntil, Long lastUpdatedAt, String description, List<String> itemIdList) {
        this.restaurantId = restaurantId;
        this.deliveryAddressId = deliveryAddressId;
        this.deliveryMethod = deliveryMethod;
        this.status = status;
        this.createdDate = createdDate;
        this.validUntil = validUntil;
        this.lastUpdatedAt = lastUpdatedAt;
        this.description = description;
        this.itemIdList = itemIdList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(String deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public Long getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Long validUntil) {
        this.validUntil = validUntil;
    }

    public Long getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Long lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getItemIdList() {
        return itemIdList;
    }

    public void setItemIdList(List<String> itemIdList) {
        this.itemIdList = itemIdList;
    }
}
