package com.voidmaingirls.model;

public class DelivaryPersonalInfo {
    
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String bankAccountNo;
    private String vehicleDetails;

    public DelivaryPersonalInfo(String name, String address, String phoneNumber, String email, String bankAccountNo, String vehicleDetails) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bankAccountNo = bankAccountNo;
        this.vehicleDetails = vehicleDetails;

    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public String getBankAccountNo() {
        return bankAccountNo;
    }
    public String getVehicleDetails() {
        return vehicleDetails;
    }
    
}
