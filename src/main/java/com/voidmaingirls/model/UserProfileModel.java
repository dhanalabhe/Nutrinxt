package com.voidmaingirls.model;

public class UserProfileModel {
    private  String name;
    private String height;   
    private String age;
    private String gender;
    private String address;
    private String weight;
    

    public UserProfileModel(String name, String height, String age, String gender, String address, String weight) {
        this.name = name;
        this.height = height;
        this.age = age;
        this.gender=gender;
        this.address = address; 
        this.weight = weight;
    }
    
    public String getName() {
        return name;
    }

    public String getHeight() {
        return height;
    }
    public String getAge() {
        return age;
    }
    public String getGender() {
        return gender;
    }
    public String getAddress() {
        return address;
    }
    public String getWeight() {
        return weight;
    }
    

}
