package com.sysco.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "restaurants")
public class Restaurant {

    @Id
    private String id;
    private String owner;
    private String addressId;
    private Date createdAt;
    private String contact;
    private String description;
    private Boolean isActive;

    public Restaurant() {
    }

    public Restaurant(String id) {
        this.id = id;
    }

    public Restaurant(String id, String owner, String addressId, Date createdAt, String contact, String description, Boolean isActive) {
        this.id = id;
        this.owner = owner;
        this.addressId = addressId;
        this.createdAt = createdAt;
        this.contact = contact;
        this.description = description;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

}
