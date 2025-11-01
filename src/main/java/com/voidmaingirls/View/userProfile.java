package com.voidmaingirls.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Map;

public class userProfile {

    Scene userScene, dashboardScene;
    Stage userStage;

    public void setUserScene(Scene userScene) {
        this.userScene = userScene;
    }

    public void setUserStage(Stage userStage) {
        this.userStage = userStage;
    }

    public VBox createScene(Runnable back, Map<String, String> userData) {
        VBox outerContainer = new VBox();
        outerContainer.setAlignment(Pos.CENTER);
        outerContainer.setPadding(new Insets(30));
        outerContainer.setStyle("-fx-background-color: linear-gradient(to bottom, #f4f6f9, #dbe2ef);");

        VBox content = new VBox(30);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(20));

        // Profile Card
        VBox profileCard = new VBox(20);
        profileCard.setAlignment(Pos.CENTER);
        profileCard.setPadding(new Insets(30));
        profileCard.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 15;" +
            "-fx-border-radius: 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 12, 0.4, 0, 4);"
        );

        // Title
        Text title = new Text("User Profile");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        title.setFill(Color.web("#2b2d42"));
        profileCard.getChildren().add(title);

        // Info Grid in Card
        GridPane infoGrid = new GridPane();
        infoGrid.setVgap(18);
        infoGrid.setHgap(50);
        infoGrid.setAlignment(Pos.CENTER_LEFT);
        infoGrid.setPadding(new Insets(10, 20, 10, 20));

        String[] labels = {
            "name", "email", "address", "age", "height",
            "weight", "goal", "activityLevel", "caloriestoEat", "dietType"
        };

        for (int i = 0; i < labels.length; i++) {
            String key = labels[i];
            String value = userData.getOrDefault(key, "N/A");

            Text label = new Text(capitalize(key) + ":");
            label.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 20));
            label.setFill(Color.web("#4a4a4a"));

            Text val = new Text(value);
            val.setFont(Font.font("Segoe UI", 18));
            val.setFill(Color.web("#222222"));

            infoGrid.add(label, 0, i);
            infoGrid.add(val, 1, i);
        }

        profileCard.getChildren().add(infoGrid);

        // Buttons Box
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(15, 0, 0, 0));

        Button backButton = new Button("Back");
        backButton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        backButton.setStyle(
            "-fx-background-color: #6c757d;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 20;" +
            "-fx-padding: 10 25 10 25;" +
            "-fx-cursor: hand;"
        );
        backButton.setOnAction(e -> {
            intializeDashboard();
            userStage.setScene(dashboardScene);
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        logoutButton.setStyle(
            "-fx-background-color: #b23b3b;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 20;" +
            "-fx-padding: 10 25 10 25;" +
            "-fx-cursor: hand;"
        );
        logoutButton.setOnAction(e -> back.run());

        buttonBox.getChildren().addAll(backButton, logoutButton);
        profileCard.getChildren().add(buttonBox);

        content.getChildren().add(profileCard);

        // ScrollPane
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        outerContainer.getChildren().add(scrollPane);

        return outerContainer;
    }

    private String capitalize(String text) {
        if (text == null || text.isEmpty()) return text;
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    public void intializeDashboard() {
        DashBoard s1 = new DashBoard();
        s1.setDashboardStage(userStage);
        dashboardScene = new Scene(s1.createScene(this::handleBack), 1300, 700);
        s1.setDashboardScene(dashboardScene);
    }

    private void handleBack() {
        userStage.setScene(userScene);
    }
}
