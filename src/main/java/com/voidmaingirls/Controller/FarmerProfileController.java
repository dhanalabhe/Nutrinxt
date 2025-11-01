package com.voidmaingirls.Controller;

import java.util.HashMap;
import java.util.Map;

import com.google.cloud.firestore.SetOptions;
import com.voidmaingirls.model.Authmodel;

public class FarmerProfileController {
    public static void storeProfile(String name, String address, String mobileNo, String email, String bankAccountNo) throws Exception {

        Map<String, Object> profileData = new HashMap<>();
        profileData.put("name", name);
        profileData.put("address", address);
        profileData.put("mobileNo", mobileNo);
        profileData.put("email", email);
        profileData.put("bankAccountNo", bankAccountNo);
        profileData.put("Role", "Farmer");
        

        FirebaseUtil.db.collection("Farmer")
                .document(Authmodel.getEmail())
                .set(profileData, SetOptions.merge())  // Use merge to avoid overwriting existing fields
                .get();

        System.out.println("✅ User profile stored successfully.");
    }

}
