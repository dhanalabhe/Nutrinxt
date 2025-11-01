package com.voidmaingirls.View;

import com.voidmaingirls.Controller.GeminiAPI;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NutriBotController {

    Stage botStage;
    Scene botScene;

    public void setBotStage(Stage botStage) {
        this.botStage = botStage;
    }

    public void setBotScene(Scene botScene) {
        this.botScene = botScene;
    }

    public VBox createChatView(Runnable onBackPressed) {
        // === Header ===
        VBox header = new VBox(4);
        header.setAlignment(Pos.TOP_CENTER);
        header.setPadding(new Insets(20, 10, 10, 10));

        Label greet1 = new Label("Hello");
        Label greet2 = new Label("I'm NutriBot");
        greet1.setFont(Font.font("Poppins",javafx.scene.text.FontWeight.BOLD, 18));
        greet1.setTextFill(Color.web("#2e7d32")); // dark green
        greet2.setFont(Font.font("Poppins", javafx.scene.text.FontWeight.EXTRA_BOLD, 16));
        greet2.setTextFill(Color.web("#4CAF50")); // green accent

        header.getChildren().addAll(greet1, greet2);

        // === Chat Box ===
        VBox chatBox = new VBox(12);
        chatBox.setPadding(new Insets(20,10,10,10));
        chatBox.setStyle("-fx-background-color: #ebffebff;");
        chatBox.setPrefWidth(320);

        ScrollPane scrollPane = new ScrollPane(chatBox);
        scrollPane.setPadding(new Insets(10,10,10,10));
        scrollPane.setPrefHeight(530);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent ;");

        // === Input ===
        TextField userInput = new TextField();
        userInput.setPromptText("Type your message...");
        userInput.setFont(Font.font("Poppins", 14));
        userInput.setStyle("-fx-background-radius: 20; -fx-background-color: #f0f0f0;");

        Button sendBtn = new Button("➤");
        sendBtn.setStyle("-fx-background-color: #388e3c; -fx-text-fill: white; -fx-background-radius: 20;");
        sendBtn.setFont(Font.font("Poppins", 18));
        sendBtn.setPrefHeight(40);
        sendBtn.setPrefWidth(60);

        sendBtn.setOnAction(e -> {
            String input = userInput.getText().trim();
            if (!input.isEmpty()) {
                Label userMsg = createBubble("👤 " + input, Pos.CENTER_RIGHT, "#bfefbfff", Color.BLACK);
                chatBox.getChildren().add(userMsg);
                userInput.clear();

                new Thread(() -> {
                    String reply = GeminiAPI.getNutriBotResponse(input);
                    Platform.runLater(() -> {
                        Label botMsg = createBubble("🤖 " + reply, Pos.CENTER_LEFT, "#d7ffd7ff", Color.DARKGREEN);
                        chatBox.getChildren().add(botMsg);
                    });
                }).start();
            }
        });

        HBox inputBox = new HBox(10, userInput, sendBtn);
        inputBox.setPadding(new Insets(10));
        inputBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(userInput, Priority.ALWAYS);

        // === Back Button ===
        Button backBtn = new Button("← Back");
        backBtn.setFont(Font.font("Poppins", 22));
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #2e7d32;");
        backBtn.setOnAction(e -> onBackPressed.run());

        // === Layout ===
        VBox layout = new VBox(backBtn, header, scrollPane, inputBox);
        layout.setStyle("-fx-background-color: #ebffebff;");
        layout.setPrefSize(340, 700);
        layout.setMinSize(340, 700);
        layout.setMaxSize(340, 700);

        return layout;
    }

    private Label createBubble(String text, Pos alignment, String bgColor, Color textColor) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.setFont(Font.font("Poppins", 14));
        label.setTextFill(textColor);
        label.setPadding(new Insets(10));
        label.setStyle("-fx-background-color: " + bgColor + "; -fx-background-radius: 15;");
        HBox bubbleWrapper = new HBox(label);
        bubbleWrapper.setAlignment(alignment);
        return label;
    }
}
