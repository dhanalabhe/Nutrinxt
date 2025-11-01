package com.voidmaingirls.model;



public class PrimarygoalModel {

    private String goal;

    public PrimarygoalModel(String goal){
        this.goal=goal;
    }
    public String getGoal() {
        System.out.println("Primary goal model part");
        return goal;
    }
}
