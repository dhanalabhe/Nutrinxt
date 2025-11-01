package com.voidmaingirls.View;

import java.util.HashMap;
import java.util.Map;

import com.google.cloud.firestore.CollectionReference;
import com.voidmaingirls.Controller.FirebaseUtil;
import com.voidmaingirls.model.Authmodel;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PaymentPage {

    private final static String[] methods = {"Credit Card", "Debit Card", "Google Pay", "Cash on Delivery"};

    public static Scene createScene(Runnable goBack, String selectedPlan) {
        BorderPane mainRoot = new BorderPane();
        mainRoot.setPrefSize(1300, 700);
        mainRoot.setStyle("-fx-background-color: #f4f4f4;");

        // Back button at top-left
        Button backBtn = new Button("← Back");
        backBtn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #20A35C; -fx-cursor: hand;");
        backBtn.setOnAction(e -> goBack.run());

        HBox backBox = new HBox(backBtn);
        backBox.setPadding(new Insets(20, 0, 0, 20));
        backBox.setAlignment(Pos.TOP_LEFT);
        mainRoot.setTop(backBox);

        // Main content
        VBox root = new VBox(20);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.TOP_CENTER);

        Label heading = new Label("Choose Payment Method");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        heading.setTextFill(Color.web("#20A35C"));

        VBox paymentOptions = new VBox(15);
        paymentOptions.setAlignment(Pos.CENTER);

        ToggleGroup paymentGroup = new ToggleGroup();

        for (String method : methods) {
            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(10));
            row.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 8; -fx-background-radius: 8;");

            Label label = new Label(method);
            label.setTextFill(Color.BLACK);
            label.setFont(Font.font(16));

            RadioButton select = new RadioButton();
            select.setToggleGroup(paymentGroup);

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            row.getChildren().addAll(label, spacer, select);

            if (method.equals("Google Pay")) {
                ImageView qrImageView = new ImageView(new Image("Assets/Images/paymentqr.jpg"));
                qrImageView.setFitWidth(300);
                qrImageView.setPreserveRatio(true);
                qrImageView.setVisible(false);
                qrImageView.setManaged(false);

                VBox googlePayBox = new VBox(10);
                googlePayBox.setAlignment(Pos.CENTER);
                googlePayBox.getChildren().addAll(row, qrImageView);
                paymentOptions.getChildren().add(googlePayBox);

                select.selectedProperty().addListener((obs, oldVal, isSelected) -> {
                    qrImageView.setVisible(isSelected);
                    qrImageView.setManaged(isSelected);
                });
            } else {
                paymentOptions.getChildren().add(row);
            }
        }

        Button confirmBtn = new Button("Confirm Payment");
        confirmBtn.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        confirmBtn.setStyle("-fx-background-color: #20A35C; -fx-text-fill: white;");
        confirmBtn.setDisable(true);

        paymentGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            confirmBtn.setDisable(newVal == null);
        });

        confirmBtn.setOnAction(e -> {
            RadioButton selected = (RadioButton) paymentGroup.getSelectedToggle();
            if (selected != null) {
                String paymentMethod = ((Label) ((HBox) selected.getParent()).getChildren().get(0)).getText();
                storePaymentToFirebase(selectedPlan, paymentMethod);
                showSuccessAnimation();
            }
        });

        VBox.setMargin(paymentOptions, new Insets(30, 0, 10, 0));
        VBox buttonBox = new VBox(20, confirmBtn);
        buttonBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(heading, paymentOptions, buttonBox);
        mainRoot.setCenter(root);

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        //scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        mainRoot.setCenter(scrollPane); // Only center is scrollable
        return new Scene(mainRoot, 1300, 700); // Root stays as BorderPane

    }

    private static void storePaymentToFirebase(String selectedPlan, String method) {
        try {
            String userEmail = Authmodel.getEmail();
            if (userEmail == null || userEmail.isEmpty()) {
                System.out.println("\u274C No user is logged in.");
                return;
            }

            Map<String, Object> paymentData = new HashMap<>();
            paymentData.put("plan", selectedPlan);
            paymentData.put("paymentMethod", method);
            paymentData.put("timestamp", System.currentTimeMillis());

            CollectionReference paymentRef = FirebaseUtil.db
                    .collection("User")
                    .document(userEmail)
                    .collection("Payments");

            paymentRef.add(paymentData).get();
            System.out.println("\u2705 Payment stored successfully in Firestore.");
        } catch (Exception ex) {
            System.out.println("\u274C Failed to store payment:");
            ex.printStackTrace();
        }
    }

    private static void showSuccessAnimation() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Payment Success");

        StackPane root = new StackPane();
        root.setPrefSize(800, 400);
        root.setStyle("-fx-background-color: black;");

        Region colorPane = new Region();
        colorPane.setStyle("-fx-background-color: #20A35C;");
        colorPane.setPrefHeight(0);
        colorPane.setPrefWidth(800);
        root.getChildren().add(colorPane);

        VBox successBox = new VBox(10);
        successBox.setAlignment(Pos.CENTER);
        successBox.setOpacity(0);

        Label checkMark = new Label("\u2714");
        checkMark.setTextFill(Color.WHITE);
        checkMark.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 80));

        Label successMsg = new Label("Payment Done Successfully");
        successMsg.setTextFill(Color.WHITE);
        successMsg.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        successBox.getChildren().addAll(checkMark, successMsg);
        root.getChildren().add(successBox);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        Timeline grow = new Timeline(
                new KeyFrame(Duration.seconds(1.2), new KeyValue(colorPane.prefHeightProperty(), 400))
        );

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.6), successBox);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1.0);
        fadeIn.setDelay(Duration.seconds(1.2));

        SequentialTransition animation = new SequentialTransition(grow, fadeIn);
        animation.play();
    }
}
