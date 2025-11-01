package com.voidmaingirls.View;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.voidmaingirls.Controller.FirebaseUtil;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class DropPage {

    Scene dropScene, trackScene;

    public void setDropScene(Scene dropScene) {
        this.dropScene = dropScene;
    }

    Stage dropStage;

    public void setDropStage(Stage dropStage) {
        this.dropStage = dropStage;
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
        transparentBox.setMaxHeight(600);
        transparentBox.setMaxWidth(880);
        transparentBox.setPadding(new Insets(20));
        transparentBox.setBackground(new Background(new BackgroundFill(
                Color.rgb(255, 255, 255, 0.65), new CornerRadii(15), null)));
        transparentBox.setStyle("-fx-border-color: white; -fx-border-radius: 15; -fx-border-width: 1.5;");

        Text heading = new Text("Delivery Drop Requests");
        heading.setFont(Font.font("Verdana", FontWeight.BOLD, 42));
        heading.setFill(Color.BLACK);

        VBox deliveryVBox = new VBox(15);
        deliveryVBox.setPadding(new Insets(10));
        deliveryVBox.setAlignment(Pos.TOP_CENTER);

        ScrollPane scrollPane = new ScrollPane(deliveryVBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(600);
        scrollPane.setPrefWidth(600);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        // ✅ Loader setup
        VBox loaderBox = new VBox();
        loaderBox.setAlignment(Pos.CENTER);
        loaderBox.setPrefSize(1300, 700);
        loaderBox.setStyle("-fx-background-color: rgba(255,255,255,0.6);");

        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setPrefSize(80, 80);
        loaderBox.getChildren().add(spinner);
        loaderBox.getStyleClass().add("loader-box");

        // Load delivery data
        loadDeliveryDropRequests(deliveryVBox);

        // Back button
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPrefSize(1300, 700);
        Button backButton = new Button("Back");
        backButton.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        backButton.setStyle("-fx-background-color: #343183ff; -fx-text-fill: white;");
        BorderPane.setAlignment(backButton, Pos.TOP_LEFT);
        BorderPane.setMargin(backButton, new Insets(20));
        mainLayout.setTop(backButton);
        backButton.setOnAction(e -> back.run());

        transparentBox.getChildren().addAll(backButton, heading, scrollPane);
        root.getChildren().addAll(beigeBackground, backgroundLayers, transparentBox, loaderBox);

        VBox wrapper = new VBox(root);
        wrapper.setAlignment(Pos.CENTER);
        return wrapper;
    }

    private void loadDeliveryDropRequests(VBox container) {
        Firestore db = FirebaseUtil.db;
        if (db == null) {
            System.out.println("❌ Firestore is null");
            return;
        }

        ApiFuture<QuerySnapshot> future = db.collection("User").get();

        future.addListener(() -> {
            try {
                List<QueryDocumentSnapshot> documents = future.get().getDocuments();

                Platform.runLater(() -> {
                    for (DocumentSnapshot doc : documents) {
                        String userEmail = doc.getId();
                        String customer = doc.contains("name") ? doc.getString("name") : "Unknown";
                        String address = doc.contains("address") ? doc.getString("address") : "No address provided";
                        String items = doc.contains("items") ? doc.getString("items") : "No items listed";

                        container.getChildren().add(
                                createDeliveryCard(customer, address, items, userEmail)
                        );
                    }

                    Node loader = container.getScene().lookup(".loader-box");
                        if (loader != null) {
                            loader.setVisible(false);
                        }

                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, Runnable::run);
    }

    private HBox createDeliveryCard(String customer, String address, String items, String userEmail) {
        HBox card = new HBox();
        card.setSpacing(40);
        card.setPadding(new Insets(15));
        card.setPrefWidth(800);
        card.setBackground(new Background(new BackgroundFill(
                Color.rgb(255, 255, 255, 0.75), new CornerRadii(10), null)));
        card.setStyle("-fx-border-color: #ccc; -fx-border-radius: 10;");
        card.setAlignment(Pos.CENTER_LEFT);

        Text customerText = new Text("Customer: " + customer);
        customerText.setFont(Font.font("Poppins", FontWeight.BOLD, 14));

        Text addressText = new Text("Address: " + address);
        addressText.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 14));

        Text itemText = new Text("Items: " + items);
        itemText.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 14));

        Button deliveredBtn = new Button("Accept");
        Button notDeliveredBtn = new Button("Reject");
        deliveredBtn.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        notDeliveredBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");

        deliveredBtn.setOnAction(e -> {
            card.setBackground(new Background(new BackgroundFill(
                    Color.rgb(144, 238, 144, 0.9), new CornerRadii(10), null)));

            Firestore db = FirebaseUtil.db;
            db.collection("User")
                    .document(userEmail)
                    .update("dropoffStatus", "delivered");

            initializeDeliveryInfo();
            dropStage.setScene(trackScene);
        });

        notDeliveredBtn.setOnAction(e -> {
            card.setBackground(new Background(new BackgroundFill(
                    Color.rgb(255, 102, 102, 0.9), new CornerRadii(10), null)));

            Firestore db = FirebaseUtil.db;
            db.collection("User")
                    .document(userEmail)
                    .update("dropoffStatus", "not delivered");
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox buttonBox = new HBox(10, deliveredBtn, notDeliveredBtn);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        card.getChildren().addAll(customerText, addressText, itemText, spacer, buttonBox);
        return card;
    }

    public void initializeDeliveryInfo() {
        FarmerPickupTracking s1 = new FarmerPickupTracking();
        s1.setFarmerTrackStage(dropStage);
        trackScene = new Scene(s1.createScene(this::handleBack), 1300, 700);
        s1.setFarmerTrackScene(trackScene);
    }

    public void handleBack() {
        dropStage.setScene(dropScene);
    }
}
