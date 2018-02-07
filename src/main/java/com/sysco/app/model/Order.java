package com.sysco.app.model;

import com.sysco.app.validation.ItemsConstraint;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Document(collection = "orders")
public class Order {

    @Id
    private String id;
    @NotBlank(message = "Restaurant Id must not be blank!") @NotNull
    private String restaurantId;
    private String deliveryAddressId;
    private String deliveryMethod;
    private String status;
    @DateTimeFormat
    private Date createdDate;
    @DateTimeFormat
    private Date validUntil;
    @DateTimeFormat
    private Date lastUpdatedAt;
    private String description;
    @ItemsConstraint
    private List<String> itemIdList;

    public Order() {
    }

    public Order(String id) {
        this.id = id;
    }

    public Order(String restaurantId, String deliveryAddressId, String deliveryMethod, String status, Date createdDate, Date validUntil, Date lastUpdatedAt, String description, List<String> itemIdList) {
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Date lastUpdatedAt) {
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
