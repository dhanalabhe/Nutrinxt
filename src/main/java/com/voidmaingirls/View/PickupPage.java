package com.voidmaingirls.View;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.voidmaingirls.model.Authmodel;
import com.voidmaingirls.Controller.FirebaseUtil;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class PickupPage {
    private Scene pickupScene, trackScene;
    private Stage pickupStage;

    public void setPickupScene(Scene pickupScene) {
        this.pickupScene = pickupScene;
    }

    public void setPickupStage(Stage stage) {
        this.pickupStage = stage;
    }

    public VBox createScene(Runnable back) {
        StackPane root = new StackPane();

        Region beigeBackground = new Region();
        beigeBackground.setPrefSize(1300, 700);
        beigeBackground.setStyle("-fx-background-color: #f4f2e9;");

        // Green rounded top panel
        Region greenHeader = new Region();
        greenHeader.setPrefSize(1000, 300);
        greenHeader.setStyle(
            "-fx-background-color: #607D2B; " + // Deep farm green
            "-fx-background-radius: 0 0 40 40; " + // Rounded bottom corners
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0.2, 0, 2);"
        );

        // Place green header at top using VBox
        VBox backgroundLayers = new VBox();
        backgroundLayers.getChildren().addAll(greenHeader, new Region()); // stretch rest of scene
        VBox.setVgrow(backgroundLayers.getChildren().get(1), Priority.ALWAYS);

        VBox transparentBox = new VBox(20);
        transparentBox.setAlignment(Pos.TOP_CENTER);
        transparentBox.setMaxSize(880, 600);
        transparentBox.setPadding(new Insets(20));
        transparentBox.setBackground(new Background(new BackgroundFill(
                Color.rgb(255, 255, 255, 0.65), new CornerRadii(15), Insets.EMPTY)));
        transparentBox.setStyle("-fx-border-color: white; -fx-border-radius: 15; -fx-border-width: 1.5;");

        // Top-level layout with back button at top-left
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPrefSize(1300, 700);

        Button backBtn = new Button("Back");
        backBtn.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        backBtn.setStyle("-fx-background-color: #20294fff; -fx-text-fill: white;");
        backBtn.setOnAction(e -> back.run());
        BorderPane.setAlignment(backBtn, Pos.TOP_LEFT);
        BorderPane.setMargin(backBtn, new Insets(20));
        mainLayout.setTop(backBtn);

        Text heading = new Text("NutriNxt - Farmer Pickup Requests");
        heading.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 46));
        heading.setFill(Color.BLACK);

        VBox listBox = new VBox(15);
        listBox.setPadding(new Insets(10));
        listBox.setAlignment(Pos.TOP_CENTER);
        ScrollPane scrollPane = new ScrollPane(listBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(500);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        transparentBox.getChildren().addAll(heading, scrollPane);
        mainLayout.setCenter(transparentBox);

        // Loader Spinner
        VBox loaderBox = new VBox();
        loaderBox.setPrefSize(1300, 700);
        loaderBox.setAlignment(Pos.CENTER);
        loaderBox.setStyle("-fx-background-color: rgba(255,255,255,0.6);");
        loaderBox.getStyleClass().add("loader-box");

        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setPrefSize(80, 80);
        loaderBox.getChildren().add(spinner);

        root.getChildren().addAll(beigeBackground, backgroundLayers, mainLayout, loaderBox);

        loadFarmerPickupRequests(listBox, root);

        return new VBox(root);
    }

    public HBox createPickupCard(String farmerName, String farmerEmail, String pickupAddress, VBox parent) {
        HBox card = new HBox(20);
        card.setPadding(new Insets(15));
        card.setPrefWidth(750);
        card.setBackground(new Background(new BackgroundFill(
                Color.rgb(255, 255, 255, 0.75), new CornerRadii(10), Insets.EMPTY)));
        card.setStyle("-fx-border-color: #ccc; -fx-border-radius: 10;");
        card.setAlignment(Pos.CENTER_LEFT);

        Text infoText = new Text("Farmer: " + farmerName + "\nPickup Address: " + pickupAddress);
        infoText.setFont(Font.font("Poppins", FontWeight.BOLD, 14));

        Button accept = new Button("Accept");
        Button reject = new Button("Reject");
        accept.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        reject.setStyle("-fx-background-color: red; -fx-text-fill: white;");

        accept.setOnAction(e -> {
            card.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(10), Insets.EMPTY)));

            Firestore db = FirebaseUtil.db;
            db.collection("Farmers")
                    .document(farmerEmail)
                    .update("pickupStatus", "accepted");
            initializeDeliveryInfo();
            pickupStage.setScene(trackScene);
        });

        reject.setOnAction(e -> parent.getChildren().remove(card));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox actionBox = new HBox(10, accept, reject);
        actionBox.setAlignment(Pos.CENTER_RIGHT);

        card.getChildren().addAll(infoText, spacer, actionBox);
        return card;
    }

    private void loadFarmerPickupRequests(VBox container, StackPane root) {
        Firestore db = FirebaseUtil.db;
        if (db == null) {
            System.out.println("❌ Firestore is null");
            return;
        }

        ApiFuture<QuerySnapshot> farmerDocsFuture = db.collection("Farmer").get();

        farmerDocsFuture.addListener(() -> {
            try {
                List<QueryDocumentSnapshot> farmers = farmerDocsFuture.get().getDocuments();
                System.out.println("✅ Found " + farmers.size() + " farmers.");

                Platform.runLater(() -> {
                    for (DocumentSnapshot farmerDoc : farmers) {
                        String farmerEmail = farmerDoc.getId();
                        String farmerName = farmerDoc.contains("name") ? farmerDoc.getString("name").toUpperCase() : "UNKNOWN FARMER";
                        String pickupAddress = farmerDoc.contains("address") ? farmerDoc.getString("address") : "No address available";

                        container.getChildren().add(
                                createPickupCard(farmerName, farmerEmail, pickupAddress, container)
                        );
                    }

                    // ✅ Hide loader spinner
                    Node loader = root.lookup(".loader-box");
                    if (loader != null) {
                        loader.setVisible(false);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, Runnable::run);
    }

    private HBox createRequestCard(String userEmail, String docId, String ingredient, VBox parent) {
        HBox card = new HBox(20);
        card.setPadding(new Insets(15));
        card.setPrefWidth(750);
        card.setBackground(new Background(new BackgroundFill(
                Color.rgb(255, 255, 255, 0.75), new CornerRadii(10), Insets.EMPTY)));
        card.setStyle("-fx-border-color: #ccc; -fx-border-radius: 10;");
        card.setAlignment(Pos.CENTER_LEFT);

        Text ingText = new Text("Ingredient: " + ingredient);
        ingText.setFont(Font.font("Poppins", FontWeight.BOLD, 14));

        Button accept = new Button("Accept");
        Button reject = new Button("Reject");
        accept.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        reject.setStyle("-fx-background-color: red; -fx-text-fill: white;");

        accept.setOnAction(e -> {
            card.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(10), Insets.EMPTY)));

            Firestore db = FirebaseUtil.db;
            db.collection("Delivery")
                    .document(userEmail)
                    .collection("Recipehub")
                    .document(docId)
                    .update("status", "accepted");
        });

        reject.setOnAction(e -> parent.getChildren().remove(card));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox HT = new HBox(10, accept, reject);
        HT.setAlignment(Pos.CENTER_RIGHT);

        card.getChildren().addAll(ingText, spacer, HT);
        return card;
    }

    public void initializeDeliveryInfo() {
        FarmerPickupTracking s1 = new FarmerPickupTracking();
        s1.setFarmerTrackStage(pickupStage);
        trackScene = new Scene(s1.createScene(this::handleBack), 1300, 700);
        s1.setFarmerTrackScene(trackScene);
    }

    public void handleBack() {
        pickupStage.setScene(pickupScene);
    }
}
