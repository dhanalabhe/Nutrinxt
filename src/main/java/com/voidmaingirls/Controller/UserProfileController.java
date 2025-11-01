
package com.voidmaingirls.Controller;

import com.voidmaingirls.model.Authmodel;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserProfileController {

    
    public static void storeProfile(String name, String height, String age, String gender, String address,
                                    String weight) throws Exception {

        Map<String, Object> profileData = new HashMap<>();
        profileData.put("name", name);
        profileData.put("height", height);
        profileData.put("age", age);
        profileData.put("gender", gender);
        profileData.put("address", address);
        profileData.put("weight", weight);
        profileData.put("Role", "User");

        FirebaseUtil.db.collection("User")
                .document(Authmodel.getEmail())
                .set(profileData, SetOptions.merge())  // Use merge to avoid overwriting existing fields
                .get();

        System.out.println("✅ User profile stored successfully.");
    }

   
    public static void storePrimaryGoal(String goal) throws Exception {
        Map<String, Object> goalData = new HashMap<>();
        goalData.put("goal", goal);

        FirebaseUtil.db.collection("User")
                .document(Authmodel.getEmail())
                .set(goalData, SetOptions.merge())  // Merge so it doesn’t overwrite the full document
                .get();

        System.out.println("✅ Primary goal stored successfully: " + goal);
    }

    public static void activityLevel(String activityLevel, String caloriestoEat) throws Exception {
        Map<String, Object> activityLeveMap = new HashMap<>();
        activityLeveMap.put("activityLevel", activityLevel);
        activityLeveMap.put("caloriestoEat", caloriestoEat);

        FirebaseUtil.db.collection("User")
                .document(Authmodel.getEmail())
                .set(activityLeveMap, SetOptions.merge())  // Merge to update specific field
                .get();

        System.out.println("✅ User profile updated with activity level and calories.");
    }

    public static void medicalConditionl(String condition) throws Exception {
        Map<String, Object> medicalConditionMap = new HashMap<>();
        medicalConditionMap.put("medicalCondition", condition);
        

        FirebaseUtil.db.collection("User")
                .document(Authmodel.getEmail())
                .set(medicalConditionMap, SetOptions.merge())  // Merge to update specific field
                .get();

        System.out.println("✅ User profile updated with activity level and calories.");
    }

    public static void dietType(String dietType) throws Exception {
        Map<String, Object> dietTypeMap = new HashMap<>();
        dietTypeMap.put("dietType", dietType);

        FirebaseUtil.db.collection("User")
                .document(Authmodel.getEmail())
                .set(dietTypeMap, SetOptions.merge())  // Merge to update specific field
                .get();

        System.out.println("✅ User profile updated with diet type.");
    }



public static List<Map<String, Object>> getAllUsers() throws Exception {
    List<QueryDocumentSnapshot> documents = FirebaseUtil.db
        .collection("User")
        .get()
        .get()
        .getDocuments();

    List<Map<String, Object>> users = new java.util.ArrayList<>();

    for (QueryDocumentSnapshot doc : documents) {
        users.add(doc.getData());
    }

    return users;
}

public static Map<String, Object> getCompleteUserProfile() throws Exception {
    Map<String, Object> allData = new HashMap<>();

    String email = Authmodel.getEmail();
    if (email == null || email.trim().isEmpty()) {
        throw new IllegalArgumentException("❌ Authmodel.getEmail() returned null or empty. Make sure user is logged in and Authmodel.setEmail(email) is called.");
    }

    DocumentSnapshot snapshot = FirebaseUtil.db
            .collection("User")
            .document(email)
            .get()
            .get();

    if (snapshot.exists()) {
        allData.putAll(snapshot.getData());
    } else {
        System.out.println("⚠️ User profile not found in Firebase for: " + email);
    }

    return allData;
}





}



