package com.voidmaingirls.Controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import com.voidmaingirls.model.Authmodel;

public class RecipeHubController {

    public static void storeProfile(List<String> ingredients) {
    if (FirebaseUtil.db == null) {
        System.out.println("❌ Firebase database not initialized.");
        return;
    }
    if (ingredients == null || ingredients.isEmpty()) {
        System.out.println("❌ No ingredients provided.");
        return;
    }

    String userEmail = Authmodel.getEmail();
    if (userEmail == null || userEmail.isEmpty()) {
        System.out.println("❌ No user is logged in.");
        return;
    }

    Map<String, Object> ingredientsMap = new HashMap<>();
    ingredientsMap.put("ingredients", ingredients);

    // Set to a specific document instead of adding a new one
    DocumentReference docRef = FirebaseUtil.db
            .collection("User")
            .document(userEmail)
            .collection("Recipihub")
            .document("RecipeProfile"); // <-- Specific document name

    ApiFuture<WriteResult> future = docRef.set(ingredientsMap);

    try {
        future.get(); // wait until write completes
        System.out.println("✅ Ingredients stored at: RecipeProfile");
    } catch (Exception e) {
        System.out.println("❌ Failed to store ingredients:");
        e.printStackTrace();
    }
}
public static void storeRequiredItems(List<String> requiredItems) {
    if (FirebaseUtil.db == null || requiredItems == null || requiredItems.isEmpty()) {
        System.out.println("❌ Invalid Firebase or empty required items");
        return;
    }

    String userEmail = Authmodel.getEmail();
    if (userEmail == null || userEmail.isEmpty()) {
        System.out.println("❌ User not logged in");
        return;
    }

    Map<String, Object> itemMap = new HashMap<>();
    itemMap.put("requiredItems", requiredItems);
    itemMap.put("timestamp", System.currentTimeMillis());

    DocumentReference docRef = FirebaseUtil.db
        .collection("User")
        .document(userEmail)
        .collection("Recipihub")
        .document("RequiredItems");

    ApiFuture<WriteResult> future = docRef.set(itemMap);

    try {
        future.get();
        System.out.println("✅ Required items stored in Firebase.");
    } catch (Exception e) {
        System.out.println("❌ Error storing required items:");
        e.printStackTrace();
    }
}

}