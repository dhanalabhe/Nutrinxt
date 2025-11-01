package com.voidmaingirls.model;

public class Authmodel {
    private static String email;

    public Authmodel(String email) {
        Authmodel.email = email;
    }

    public static String getEmail() {
        System.out.println("auth model part");
        return email;
    }

    public static void setEmail(String email) {
        Authmodel.email = email;
        System.out.println("✅ Email set in Authmodel: " + email);
    }
}
