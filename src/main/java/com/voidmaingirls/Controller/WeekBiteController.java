package com.voidmaingirls.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.voidmaingirls.model.Authmodel;

public class WeekBiteController {

    public static void storeProfile(List<String> dietTypes, String startDate, String endDate,
                                List<String> mealTypes, String geminiPlan) throws Exception {
    if (FirebaseUtil.db == null) {
        System.out.println("❌ Firebase database not initialized.");
        return;
    }

    String userEmail = Authmodel.getEmail();
    if (userEmail == null || userEmail.isEmpty()) {
        System.out.println("❌ No user is logged in.");
        return;
    }

    // STEP 1: Basic plan inputs
    Map<String, Object> planData = new HashMap<>();
    planData.put("dietType", dietTypes);
    planData.put("startDate", startDate);
    planData.put("endDate", endDate);
    planData.put("mealType", mealTypes);
    planData.put("generatedPlan", geminiPlan); // 👈 add Gemini response

    // STEP 2: Fetch full user profile from Firestore
    try {
        Map<String, Object> userProfile = UserProfileController.getCompleteUserProfile(); // 👈 method we built earlier
        planData.putAll(userProfile); // merge profile fields
    } catch (Exception e) {
        System.out.println("⚠️ Could not fetch user profile: " + e.getMessage());
    }

    // STEP 3: Store in nested collection
    CollectionReference plansRef = FirebaseUtil.db
            .collection("User")
            .document(userEmail)
            .collection("WeekBites");

    ApiFuture<DocumentReference> future = plansRef.add(planData);

    try {
        DocumentReference docRef = future.get();
        System.out.println("✅ Plan stored with ID: " + docRef.getId());
    } catch (Exception e) {
        System.out.println("❌ Failed to store plan:");
        e.printStackTrace();
    }
}
    
}
