package com.voidmaingirls.model;

public class DeliveryOrder {
    
    private String orderId;
    private String pickupAddress;
    private String dropAddress;
    private String status; // e.g., "Pending", "In Transit", "Delivered"

    // Constructor
    public DeliveryOrder(String orderId, String pickupAddress, String dropAddress, String status) {
        this.orderId = orderId;
        this.pickupAddress = pickupAddress;
        this.dropAddress = dropAddress;
        this.status = status;
    }

    // Default constructor
    public DeliveryOrder() {
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getDropAddress() {
        return dropAddress;
    }

    public void setDropAddress(String dropAddress) {
        this.dropAddress = dropAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


