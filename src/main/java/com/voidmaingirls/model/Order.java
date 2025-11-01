package com.voidmaingirls.model;

import java.util.Date;

public class Order {

    
    private String item;
    private String farmerEmail;
    private String status;
    

    public Order(String item, String farmerEmail, String status) {
        this.item = item;
        this.farmerEmail = farmerEmail;
        this.status = status;
        
    }

    // existing fields and

    // Getters (required for Firestore to serialize/deserialize)
    public String getItem() {
        return item;
    }

    public String getFarmerEmail() {
        return farmerEmail;
    }

    public String getStatus() {
        return status;
    }


    // Optional: Setters (needed if you ever read data back into this class)
    public void setItem(String item) {
        this.item = item;
    }

    public void setFarmerEmail(String farmerEmail) {
        this.farmerEmail = farmerEmail;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   
}
