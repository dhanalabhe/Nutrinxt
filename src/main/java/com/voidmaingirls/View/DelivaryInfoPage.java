package com.voidmaingirls.View;

import com.voidmaingirls.Controller.DelivaryAgentControler;
import com.voidmaingirls.model.DelivaryPersonalInfo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class DelivaryInfoPage {

    Scene deliveryInfoScene, delivaryHomeScene;
    Stage deliveryInfoStage;

    public void setDeliveryInfoScene(Scene deliveryInfoScene) {
        this.deliveryInfoScene = deliveryInfoScene;
    }

    public void setDeliveryInfoStage(Stage deliveryInfoStage) {
        this.deliveryInfoStage = deliveryInfoStage;
    }

    public VBox createScene(Runnable back) {
        Image bgImage = new Image(getClass().getResourceAsStream("/Assets/Images/delivaryBackground.jpg"));
        BackgroundImage bg = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false)
        );

        BorderPane root = new BorderPane();
        root.setBackground(new Background(bg));

        // Main white box container with shadow
        HBox mainBox = new HBox(30);
        mainBox.setPadding(new Insets(30));
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setBackground(new Background(
                new BackgroundFill(Color.rgb(255, 255, 255, 0.65), new CornerRadii(20), Insets.EMPTY)
        ));
        mainBox.setEffect(new DropShadow(20, Color.GRAY));

        // LEFT: Big image
        VBox imageBox = new VBox();
        imageBox.setAlignment(Pos.BASELINE_LEFT);
        imageBox.setPadding(new Insets(10));

        Image bigImg = new Image(getClass().getResourceAsStream("/Assets/Images/menu1.png"));
        ImageView bigImageView = new ImageView(bigImg);
        bigImageView.setFitHeight(600);
        bigImageView.setPreserveRatio(true);
        bigImageView.setSmooth(true);
        bigImageView.setEffect(new DropShadow(10, Color.DARKGRAY));
        imageBox.getChildren().add(bigImageView);

        // RIGHT: Form box
        VBox formBox = new VBox(10);
        formBox.setAlignment(Pos.TOP_LEFT);
        formBox.setPadding(new Insets(20));

        Label heading = new Label("Personal Information");
        heading.setFont(Font.font("Tahoma", 40));
        heading.setTextFill(Color.DARKGREEN);
        heading.setStyle("-fx-font-weight: bold;");
        heading.setEffect(new DropShadow(2, Color.DARKGREY));

        TextField nameField = new TextField();
        nameField.setPromptText("Enter Name");
        nameField.setFont(Font.font("Tahoma", 20));
        nameField.setStyle("-fx-control-inner-background: #f5f19cff;-fx-font-weight: bold;");

        TextField addressField = new TextField();
        addressField.setPromptText("Enter Address");
        addressField.setFont(Font.font("Tahoma", 20));
        addressField.setStyle("-fx-control-inner-background: #f5f19cff;-fx-font-weight: bold;");

        TextField noField = new TextField();
        noField.setPromptText("Enter Mobile No");
        noField.setFont(Font.font("Tahoma", 20));
        noField.setStyle("-fx-control-inner-background: #f5f19cff;-fx-font-weight: bold;");

        TextField mailField = new TextField();
        mailField.setPromptText("Enter Email");
        mailField.setFont(Font.font("Tahoma", 20));
        mailField.setStyle("-fx-control-inner-background: #f5f19cff;-fx-font-weight: bold;");

        TextField accField = new TextField();
        accField.setPromptText("Enter Bank Account No");
        accField.setFont(Font.font("Tahoma", 20));
        accField.setStyle("-fx-control-inner-background: #f5f19cff;-fx-font-weight: bold;");

        TextField vehField = new TextField();
        vehField.setPromptText("Enter Transport Vehical Type");
        vehField.setFont(Font.font("Tahoma", 20));
        vehField.setStyle("-fx-control-inner-background: #f5f19cff;-fx-font-weight: bold;");

        Label statusLabel = new Label();
        statusLabel.setTextFill(Color.DARKBLUE);

        Button submitBtn = new Button("Submit");
        submitBtn.setStyle("-fx-background-color: #37474F; -fx-text-fill: white");
        submitBtn.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 20));
        submitBtn.setMinWidth(100);
        submitBtn.setOnAction(e -> {
    // Collect form values
    DelivaryPersonalInfo info = new DelivaryPersonalInfo(
        nameField.getText(),
        addressField.getText(),
        noField.getText(),
        mailField.getText(),
        accField.getText(),
        vehField.getText()
    );

    // Save to Firestore in background
    new Thread(() -> {
        boolean saved = DelivaryAgentControler.storeDeliveryAgentPersonalInfo(info);

        // Update UI on JavaFX thread
        javafx.application.Platform.runLater(() -> {
            if (saved) {
                statusLabel.setText("✅ Info saved successfully!");
                initialiDelivary();
                deliveryInfoStage.setScene(delivaryHomeScene);
            } else {
                statusLabel.setText("❌ Failed to save info. Check console for error.");
            }
        });
    }).start();
});




        Button editBtn = new Button("Edit");
        editBtn.setStyle("-fx-background-color: #556b76ff; -fx-text-fill: white");
        editBtn.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 20));
        editBtn.setMinWidth(100);

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: #888888; -fx-text-fill: white;");
        backBtn.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 16));
        backBtn.setOnAction(e -> back.run());


        editBtn.setOnAction(e -> {
            nameField.clear();
            addressField.clear();
            noField.clear();
            mailField.clear();
            accField.clear();
            vehField.clear();
            statusLabel.setText("");
        });

        Label nameLbl = new Label("Name");
        nameLbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
        Label addressLbl = new Label("Address");
        addressLbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
        Label noLbl = new Label("Mobile No");
        noLbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
        Label mailLbl = new Label("Email");
        mailLbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
        Label accLbl = new Label("Bank Account No");
        accLbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
        Label vehLbl = new Label("Vehicle");
        vehLbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));

        formBox.getChildren().addAll(
                heading,
                nameLbl, nameField,
                addressLbl, addressField,
                noLbl, noField,
                mailLbl, mailField,
                accLbl, accField,
                vehLbl, vehField,
                new HBox(10, submitBtn, editBtn),
                statusLabel,
                backBtn
        );

        mainBox.getChildren().addAll(imageBox, formBox);
        root.setCenter(mainBox);

        return new VBox(root);
    }

    public void initialiDelivary() {
        delivaryhome s1 = new delivaryhome();
        s1.setDelivaryStage(deliveryInfoStage);
        VBox nextPage = s1.createScene(this::handleBack);  // <--- Use VBox here
        delivaryHomeScene = new Scene(nextPage, 1300, 700);
        s1.setDelivaryScene(delivaryHomeScene);
    }

    public void handleBack() {
        deliveryInfoStage.setScene(deliveryInfoScene);
    }
}
