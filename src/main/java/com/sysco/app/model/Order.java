package com.sysco.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "order")
public class Order{
    @Id
    private int orderId;
    private String deliveryMethod;
    private String status;
    private String description;

    public Order(int orderId, String deliveryMethod, String status, String description) {
        this.orderId = orderId;
        this.deliveryMethod = deliveryMethod;
        this.status = status;
        this.description = description;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Id:- " + getOrderId() + ", Delivery method:- " + getDeliveryMethod() + ", Status:- " + getStatus());
        return str.toString();
    }

}
