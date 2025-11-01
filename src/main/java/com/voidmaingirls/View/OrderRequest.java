package com.voidmaingirls.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.voidmaingirls.model.Authmodel;
import com.voidmaingirls.model.ConfirmedItem;
import com.voidmaingirls.model.Order;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class OrderRequest {

    Scene OrderRequestScene;
    Stage OrderRequestStage;

    public void setOrderRequestScene(Scene orderRequestScene) {
        OrderRequestScene = orderRequestScene;
    }

    public void setOrderRequestStage(Stage orderRequestStage) {
        OrderRequestStage = orderRequestStage;
    }

    private VBox ingredientsContainer = new VBox(15);
    private final List<ToggleButton> tickButtons = new ArrayList<>();
    private VBox loaderBox = new VBox();

    public VBox createScene(Runnable back) throws Exception {
        // Back Button
        Button backBtn = new Button("← Back");
        backBtn.setOnAction(e -> back.run());
        backBtn.setStyle("-fx-background-color: #58B368; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8;");
        backBtn.setPadding(new Insets(8, 15, 8, 15));

        // Ingredient container
        ingredientsContainer.setPadding(new Insets(20));
        ingredientsContainer.setAlignment(Pos.TOP_CENTER);

        ScrollPane scrollPane = new ScrollPane(ingredientsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(550);
        scrollPane.setPrefViewportWidth(900);

        // Submit button
        Button submitBtn = new Button("Submit Selected Items");
        submitBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px;");
        submitBtn.setPadding(new Insets(10, 20, 10, 20));
        submitBtn.setOnAction(e -> handleSubmitSelectedItems());

        VBox mainLayout = new VBox(20, backBtn, scrollPane, submitBtn);
        mainLayout.setPadding(new Insets(20));

        // Loader spinner
        ProgressIndicator loader = new ProgressIndicator();
        loader.setPrefSize(80, 80);
        loaderBox.setAlignment(Pos.CENTER);
        loaderBox.setPrefHeight(700);
        loaderBox.getChildren().add(loader);
        loaderBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7);");

        StackPane root = new StackPane();
        root.getChildren().addAll(mainLayout, loaderBox);

        fetchAllRequiredItems(); // start loading items
        return new VBox(root);
    }

    private void handleSubmitSelectedItems() {
        Firestore db = FirestoreClient.getFirestore();
        int count = 0;

        for (ToggleButton btn : tickButtons) {
            if (btn.isSelected()) {
                HBox row = (HBox) btn.getParent();
                VBox content = (VBox) row.getChildren().get(0);

                String user = ((Label) content.getChildren().get(0)).getText().replace("User: ", "").trim();
                String item = ((Label) content.getChildren().get(1)).getText().replace("Ingredient: ", "").trim();
                String farmerEmail = Authmodel.getEmail();

                // Save to Farmer/confirmedIngredients/{user_item}
                String docId = "farmerOrderHistory";
                db.collection("Farmer").document(farmerEmail)
                        .collection("confirmedIngredients")
                        .document(docId)
                        .set(new ConfirmedItem(user, item));

                // Save to User/OrderHistory/{item_timestamp}
                String orderDocId = "UserOrderHistory";
                db.collection("User").document(user)
                        .collection("OrderHistory")
                        .document(orderDocId)
                        .set(new Order(item, farmerEmail, "Confirmed"));

                // Remove item from RequiredItems
                DocumentReference requiredDocRef = db.collection("User").document(user)
                        .collection("Recipihub").document("RequiredItems");

                try {
                    DocumentSnapshot snapshot = requiredDocRef.get().get();
                    if (snapshot.exists()) {
                        List<String> requiredItems = (List<String>) snapshot.get("requiredItems");
                        if (requiredItems != null && requiredItems.contains(item)) {
                            requiredItems.remove(item);
                            requiredDocRef.update("requiredItems", requiredItems);
                        }
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    ex.printStackTrace();
                }

                count++;
            }
        }

        if (count == 0) {
            showAlert("No Selection", "Please select at least one item.");
        } else {
            showAlert("Success", "Submitted " + count + " item(s).");
            ingredientsContainer.getChildren().clear();
            tickButtons.clear();
            fetchAllRequiredItems();  // Refresh view
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void fetchAllRequiredItems() {
        loaderBox.setVisible(true); // Show loader
        Firestore db = FirestoreClient.getFirestore();

        new Thread(() -> {
            try {
                List<QueryDocumentSnapshot> userDocs = db.collection("User").get().get().getDocuments();

                for (QueryDocumentSnapshot userDoc : userDocs) {
                    String userEmail = userDoc.getId();
                    String requiredItemsPath = "User/" + userEmail + "/Recipihub/RequiredItems";
                    DocumentSnapshot requiredDoc = db.document(requiredItemsPath).get().get();

                    if (requiredDoc.exists()) {
                        List<String> items = (List<String>) requiredDoc.get("requiredItems");
                        if (items != null) {
                            for (String item : items) {
                                final String email = userEmail;
                                final String ingredient = item;

                                javafx.application.Platform.runLater(() -> addIngredientCard(email, ingredient));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                javafx.application.Platform.runLater(() -> loaderBox.setVisible(false)); // Hide loader
            }
        }).start();
    }

    private void addIngredientCard(String userEmail, String itemName) {
        Label userLabel = new Label("User: " + userEmail);
        Label itemLabel = new Label("Ingredient: " + itemName);

        userLabel.setFont(Font.font("Roboto", 14));
        itemLabel.setFont(Font.font("Roboto", 16));

        VBox content = new VBox(5, userLabel, itemLabel);
        content.setPadding(new Insets(15));
        content.setStyle("-fx-background-color: beige; -fx-background-radius: 10;");
        content.setEffect(new DropShadow(5, Color.GRAY));
        content.setMinWidth(850);

        ToggleButton tickButton = new ToggleButton("✓");
        tickButton.setFont(Font.font("Arial", 18));
        tickButton.setShape(new Circle(25));
        tickButton.setMinSize(50, 50);
        tickButton.setStyle("-fx-background-color: white; -fx-border-color: black;");
        tickButton.selectedProperty().addListener((obs, oldVal, newVal) -> {
            tickButton.setStyle(newVal ?
                    "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-color: black;" :
                    "-fx-background-color: white; -fx-border-color: black;");
        });

         tickButton.selectedProperty().addListener((obs, oldVal, newVal) -> {
        if (newVal) {
            tickButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-color: black;");
            content.setStyle("-fx-background-color: #E8F5E9; -fx-background-radius: 10; -fx-border-color: #388E3C; -fx-border-width: 2;");
        } else {
            tickButton.setStyle("-fx-background-color: white; -fx-border-color: black;");
            content.setStyle("-fx-background-color: beige; -fx-background-radius: 10;");
        }
    });

        tickButtons.add(tickButton);
        HBox row = new HBox(20, content, tickButton);
        row.setPadding(new Insets(10));
        row.setAlignment(Pos.CENTER_LEFT);
        ingredientsContainer.getChildren().add(row);
    }
}