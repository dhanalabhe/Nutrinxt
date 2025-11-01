package com.voidmaingirls.model;

public class FarmerPersonalInfoModel {
    private String name;
    private String address;
    private String mobileNo;
    private String email;
    private String bankAccountNo;
    public FarmerPersonalInfoModel(String name, String address, String mobileNo, String email, String bankAccountNo) {
        this.name = name;
        this.address = address;
        this.mobileNo = mobileNo;
        this.email = email;
        this.bankAccountNo = bankAccountNo;
    }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getMobileNo() {
        return mobileNo;
    }
    public String getEmail() {
        return email;
    }
    public String getBankAccountNo() {
        return bankAccountNo;
    }
    
}
