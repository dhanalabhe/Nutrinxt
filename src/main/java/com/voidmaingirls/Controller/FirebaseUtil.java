package com.voidmaingirls.Controller;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;

public class FirebaseUtil {

    public static Firestore db;

    // 🔹 Call this once during app startup
    public static void initializeFirebase() throws Exception {
        if (FirebaseApp.getApps().isEmpty()) { // Avoid re-initialization
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/n" + //
                                "utrinxt-5d3c6-firebase-adminsdk-fbsvc-a6fe80b0e2.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
            System.out.println("intialize firebase");
        }
        db = FirestoreClient.getFirestore();
    }

    
}
