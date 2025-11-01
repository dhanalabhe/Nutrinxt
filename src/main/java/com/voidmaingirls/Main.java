package com.voidmaingirls;
import com.voidmaingirls.Controller.FirebaseUtil;
import com.voidmaingirls.View.Home;

import javafx.application.Application;

public class Main {
    public static void main(String[] args) throws Exception {
        FirebaseUtil.initializeFirebase();
        System.out.println("Application Started");
        Application.launch(Home.class,args);
    }
}