package com.voidmaingirls.model;

public class ConfirmedItem {
    private String user;
    private String item;

    public ConfirmedItem() {} // Needed for Firestore

    public ConfirmedItem(String user, String item) {
        this.user = user;
        this.item = item;
    }

    public String getUser() {
        return user;
    }

    public String getItem() {
        return item;
    }
}
