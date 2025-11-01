package com.voidmaingirls.Controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;

public class FarmerpickupController {

    public static ApiFuture<WriteResult> markOrderAsDelivered(String userEmail, String docId) {
        
        Firestore db = FirebaseUtil.db;
        return db.collection("Delivery")
                 .document(userEmail)
                 .collection("Recipehub")
                 .document(docId)
                 .delete();
    }
}
