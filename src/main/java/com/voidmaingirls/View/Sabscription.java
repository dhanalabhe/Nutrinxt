package com.voidmaingirls.View;

import com.voidmaingirls.model.Authmodel;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Sabscription {
    Scene SubscribeScene;
    Stage SubscribeStage;

    public void setSubscribeScene(Scene subscribeScene) {
        SubscribeScene = subscribeScene;
    }

    public void setSubscribeStage(Stage subscribeStage) {
        SubscribeStage = subscribeStage;
    }

    public VBox createScene(Runnable back) {
        VBox root = new VBox(30);
        root.setStyle(
            "-fx-background-color: #f4f8f0;" +  // soft neutral greenish background
            "-fx-background-radius: 15;"
        );

        root.setPadding(new javafx.geometry.Insets(30));
        root.setAlignment(Pos.TOP_CENTER);

        // Back Button container
        HBox backContainer = new HBox();
        backContainer.setAlignment(Pos.TOP_LEFT);

        Button backButton = new Button("← Back");
        backButton.setStyle("-fx-font-size: 16px; -fx-background-color: #303b02ff; -fx-text-fill: white; -fx-background-radius: 60;");
        backButton.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                back.run();
            }
        });

        backContainer.getChildren().add(backButton);

        HBox cardsContainer = new HBox(20);
        cardsContainer.setAlignment(Pos.CENTER);
        cardsContainer.getChildren().addAll(
            createPlan("BASIC", "$30", Color.web("#00C9FF"), Color.web("#464abeff")),
            createPlan("STANDARD", "$50", Color.web("#FDBB2D"), Color.web("#b74b2dff")),
            createPlan("PREMIUM", "$70", Color.web("#de6dfaff"), Color.web("#5b0961ff"))
        );

        root.getChildren().addAll(backContainer, cardsContainer);
        return root;
    }

    private VBox createPlan(String name, String price, Color color1, Color color2) {
        VBox card = new VBox(15);
        card.setAlignment(Pos.TOP_LEFT);
        card.setPadding(new javafx.geometry.Insets(20));
        card.setPrefWidth(300);
        card.setPrefHeight(600);

        String cardBgColor;
        if (name.equalsIgnoreCase("BASIC")) {
            cardBgColor = "#e0f7fa"; // light cyan
        } else if (name.equalsIgnoreCase("STANDARD")) {
            cardBgColor = "#fff3e0"; // light orange
        } else { // PREMIUM
            cardBgColor = "#f3e5f5"; // light purple
        }

        card.setStyle(
            "-fx-background-radius: 20;" +
            "-fx-background-color: " + cardBgColor + ";" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 4);"
        );

        // Gradient banner
        StackPane banner = new StackPane();
        banner.setPrefHeight(80);
        banner.setMaxWidth(Double.MAX_VALUE);
        banner.setBackground(new Background(new BackgroundFill(
            new javafx.scene.paint.LinearGradient(0, 0, 1, 0, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new javafx.scene.paint.Stop(0, color1), new javafx.scene.paint.Stop(1, color2)),
            new CornerRadii(20, 20, 0, 0, false), javafx.geometry.Insets.EMPTY)));

        javafx.scene.control.Label planLabel = new javafx.scene.control.Label(name);
        planLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        planLabel.setTextFill(Color.WHITE);
        banner.getChildren().add(planLabel);

        // Features
        VBox features = new VBox(10);
        features.setPadding(new javafx.geometry.Insets(20, 10, 10, 10));
        features.setAlignment(Pos.CENTER_LEFT);

        if (name.equalsIgnoreCase("BASIC")) {
            features.getChildren().addAll(
                createFeature("✔", "Few recipes", Color.GREEN),
                createFeature("✖", "Offline mode", Color.RED),
                createFeature("✔", "Track meals", Color.GREEN),
                createFeature("✖", "Coaching", Color.RED),
                createFeature("✔", "Weekly tips", Color.GREEN),
                createFeature("✖", "AI diet", Color.RED),
                createFeature("✖", "Analytics", Color.RED)
            );
        } else if (name.equalsIgnoreCase("STANDARD")) {
            features.getChildren().addAll(
                createFeature("✔", "All recipes", Color.GREEN),
                createFeature("✔", "Offline mode", Color.GREEN),
                createFeature("✔", "Daily tracking", Color.GREEN),
                createFeature("✔", "Tips", Color.GREEN),
                createFeature("✔", "Weekly tips", Color.GREEN),
                createFeature("✖", "AI coaching", Color.RED),
                createFeature("✖", "Analytics", Color.RED)
            );
        } else if (name.equalsIgnoreCase("PREMIUM")) {
            features.getChildren().addAll(
                createFeature("✔", "All recipes", Color.GREEN),
                createFeature("✔", "Offline mode", Color.GREEN),
                createFeature("✔", "Smart tracking", Color.GREEN),
                createFeature("✔", "AI coaching", Color.GREEN),
                createFeature("✔", "Weekly tips", Color.GREEN),
                createFeature("✔", "AI diet", Color.GREEN),
                createFeature("✔", "Analytics", Color.GREEN)
            );
        }

        // Price
        javafx.scene.control.Label priceLabel = new javafx.scene.control.Label(price);
        priceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        priceLabel.setTextFill(color1);

        // BUY button
        Button buyBtn = new Button("BUY NOW");
        buyBtn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        buyBtn.setTextFill(color1);
        buyBtn.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
        buyBtn.setBorder(new Border(new BorderStroke(color1, BorderStrokeStyle.SOLID,
                new CornerRadii(20), BorderWidths.DEFAULT)));
        buyBtn.setPadding(new javafx.geometry.Insets(5, 15, 5, 15));

        buyBtn.setOnAction(e -> {
            if (Authmodel.getEmail() == null || Authmodel.getEmail().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Login Required");
                alert.setHeaderText(null);
                alert.setContentText("Please log in or sign up to generate a plan.");
                alert.showAndWait();
                return;
            }

            Scene paymentScene = PaymentPage.createScene(() -> {
                SubscribeStage.setScene(SubscribeScene);
            }, name);
            SubscribeStage.setScene(paymentScene);
        });

        VBox contentWrapper = new VBox(10, banner, features);
        contentWrapper.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(features, Priority.ALWAYS);

        VBox bottom = new VBox(10, priceLabel, buyBtn);
        bottom.setAlignment(Pos.BOTTOM_CENTER);

        card.getChildren().addAll(contentWrapper, bottom);
        return card;
    }

    private HBox createFeature(String iconText, String labelText, Color iconColor) {
        HBox featureRow = new HBox(10);
        featureRow.setAlignment(Pos.CENTER_LEFT);

        javafx.scene.control.Label icon = new javafx.scene.control.Label(iconText);
        icon.setTextFill(iconColor);

        javafx.scene.control.Label text = new javafx.scene.control.Label(labelText);
        text.setTextFill(Color.GRAY);
        text.setFont(Font.font("Arial", 18));

        featureRow.getChildren().addAll(icon, text);
        return featureRow;
    }
}
