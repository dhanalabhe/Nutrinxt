package com.voidmaingirls.View;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.voidmaingirls.model.Authmodel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class OrderHistory {
    private VBox orderContainer = new VBox(15);
    Scene historyScene;
    Stage historyStage;

    public void setHistoryScene(Scene historyScene) {
        this.historyScene = historyScene;
    }

    public void setHistoryStage(Stage historyStage) {
        this.historyStage = historyStage;
    }

    public VBox createScene(Runnable back) {
        Label title = new Label("Order History for " + Authmodel.getEmail());
        title.setFont(Font.font("Arial", 20));
        title.setStyle("-fx-text-fill: #333333;");

        Button backBtn = new Button("← Back");
        backBtn.setOnAction(e -> back.run());
        backBtn.setStyle("-fx-background-color: #388E3C; -fx-text-fill: white;");
        backBtn.setPadding(new Insets(8, 16, 8, 16));

        ScrollPane scrollPane = new ScrollPane(orderContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(500);

        VBox root = new VBox(20, backBtn, title, scrollPane);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        fetchOrderHistory(Authmodel.getEmail());

        return root;
    }

    private void fetchOrderHistory(String userEmail) {
        Firestore db = FirestoreClient.getFirestore();
        orderContainer.getChildren().clear();

        try {
            // Get all farmers
            ApiFuture<QuerySnapshot> farmerDocs = db.collection("Farmer").get();
            List<QueryDocumentSnapshot> farmers = farmerDocs.get().getDocuments();

            boolean hasOrders = false;

            for (QueryDocumentSnapshot farmerDoc : farmers) {
                String farmerEmail = farmerDoc.getId(); // e.g., kisan@gmail.com

                // Fetch all documents inside confirmedIngredients collection
                ApiFuture<QuerySnapshot> confirmedFuture = db.collection("Farmer")
                        .document(farmerEmail)
                        .collection("confirmedIngredients")
                        .get();

                List<QueryDocumentSnapshot> confirmedOrders = confirmedFuture.get().getDocuments();

                for (QueryDocumentSnapshot doc : confirmedOrders) {
                    String user = doc.getString("user");

                    if (user != null && user.equals(userEmail)) {
                        hasOrders = true;
                        String item = doc.getString("item");

                        VBox box = new VBox(5);
                        box.setPadding(new Insets(15));
                        box.setStyle("-fx-background-color: #E8F5E9; -fx-background-radius: 10; -fx-border-color: #388E3C; -fx-border-width: 2; -fx-border-radius: 10;");
                        box.setEffect(new DropShadow(5, Color.GRAY));
                        box.setMinWidth(850);

                        box.getChildren().addAll(
                                new Label("Item: " + item),
                                new Label("Farmer: " + farmerEmail),
                                new Label("Status: Confirmed"),
                                new Label("Date: Not available")
                        );
                        box.getChildren().forEach(node -> {
                            if (node instanceof Label) {
                                ((Label) node).setFont(Font.font("Roboto", 14));
                            }
                        });

                        orderContainer.getChildren().add(box);
                    }
                }
            }

            if (!hasOrders) {
                Label noData = new Label("No order history found.");
                noData.setFont(Font.font("Arial", 16));
                orderContainer.getChildren().add(noData);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error loading order history.");
            orderContainer.getChildren().add(errorLabel);
        }
    }
}