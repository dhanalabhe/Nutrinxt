package com.voidmaingirls.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import com.voidmaingirls.model.Authmodel;
import com.voidmaingirls.model.DelivaryPersonalInfo;

public class DelivaryAgentControler {

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
            .collection("Delivery")
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

public static boolean storeDeliveryAgentPersonalInfo(DelivaryPersonalInfo info) {
        if (FirebaseUtil.db == null) {
            System.out.println("❌ Firestore not initialized.");
            return false;
        }

        try {
            Map<String, Object> data = new HashMap<>();
            data.put("name", info.getName());
            data.put("address", info.getAddress());
            data.put("phoneNumber", info.getPhoneNumber());
            data.put("email", info.getEmail());
            data.put("bankAccountNo", info.getBankAccountNo());
            data.put("vehicleDetails", info.getVehicleDetails());

            DocumentReference ref = FirebaseUtil.db
                    .collection("DeliveryAgents")
                    .document(info.getEmail()); // using email as unique doc ID

            ApiFuture<WriteResult> result = ref.set(data, SetOptions.merge());
            result.get();

            System.out.println("✅ Delivery agent info stored for: " + info.getEmail());
            return true;
        } catch (Exception e) {
            System.out.println("❌ Failed to store delivery agent info: " + e.getMessage());
            return false;
        }
    }
}
