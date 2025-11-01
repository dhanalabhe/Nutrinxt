package com.voidmaingirls.model;

public class Activitymodel {
    private String activityLevel;
    private String caloriestoEat;

    public Activitymodel(String activityLevel, String caloriestoEat) {
        this.activityLevel = activityLevel;
       
        this.caloriestoEat = caloriestoEat;
    }

    public String getActivityType() {
        return activityLevel;
    }

    

    public String getCaloriesBurned() {
        return caloriestoEat;
    }
    
}
