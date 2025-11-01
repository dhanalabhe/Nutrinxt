package com.voidmaingirls.View;

import com.voidmaingirls.Controller.FarmerProfileController;
import com.voidmaingirls.model.Authmodel;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class farmerPersonalinfo {

    Scene farmerPersonalInfoScene,FarmerHomeScene;
    Stage farmerPersonalInfoStage;
    public void setFarmerPersonalInfoScene(Scene scene) {
        this.farmerPersonalInfoScene = scene;
    }
    public void setFarmerPersonalInfoStage(Stage stage) {
        this.farmerPersonalInfoStage = stage;
    }

    public VBox createScene(Runnable back) {

        Image bgImage = new Image(getClass().getResourceAsStream("/Assets/Images/menu2.jpg"));
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

        // LEFT: Single big image
        VBox imageBox = new VBox();
        imageBox.setAlignment(Pos.BASELINE_LEFT);
        imageBox.setPadding(new Insets(10));

        Image bigImg = new Image(getClass().getResourceAsStream("/Assets/Images/menu1.png")); // <-- Replace with your desired image
        ImageView bigImageView = new ImageView(bigImg);
        bigImageView.setFitWidth(433);  // Adjust width as needed
       // bigImageView.setFitHeight(1300); // Adjust height as needed
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
        nameField.setFont(Font.font("Poppins", 20));
        nameField.setStyle("-fx-control-inner-background: #e5f7baff;-fx-font-weight: Normal;");

        TextField addressField = new TextField();
        addressField.setPromptText("Enter Address");
        addressField.setFont(Font.font("Poppins", 20));
        addressField.setStyle("-fx-control-inner-background: #e5f7baff;-fx-font-weight:Normal;");

        TextField noField = new TextField();
        noField.setPromptText("Enter Mobile No");
        noField.setFont(Font.font("Poppins", 20));
        noField.setStyle("-fx-control-inner-background: #e5f7baff;-fx-font-weight: Normal;");

        TextField mailField = new TextField();
        mailField.setPromptText("Enter Email");
        mailField.setFont(Font.font("Poppins", 20));
        mailField.setStyle("-fx-control-inner-background: #e5f7baff;-fx-font-weight: Normal;");


         TextField accField = new TextField();
        accField.setPromptText("Enter Bank Account No");
        accField.setFont(Font.font("Poppins", 20));
        accField.setStyle("-fx-control-inner-background: #e5f7baff;-fx-font-weight: Normal;");


        Button submitBtn = new Button("Submit");
        submitBtn.setStyle("-fx-background-color: #37474F; -fx-text-fill: white");
        submitBtn.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 20));
        submitBtn.setMinWidth(100);

        Button editBtn = new Button("Edit");
        editBtn.setStyle("-fx-background-color: #37474F; -fx-text-fill: white");
        editBtn.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 20));
        editBtn.setMinWidth(100);

        Label statusLabel = new Label();
        statusLabel.setTextFill(Color.DARKBLUE);

        submitBtn.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                String name = nameField.getText().trim();
                String address = addressField.getText().trim();
                String mobileNo = noField.getText().trim();
                String email = mailField.getText().trim();
                String bankAccountNo = accField.getText().trim();

                // Basic validation (optional)
                if (name.isEmpty() || address.isEmpty() || mobileNo.isEmpty() || email.isEmpty() || bankAccountNo.isEmpty()) {
                    statusLabel.setText("⚠️ Please fill in all fields.");
                    return;
                }
                String currentUserEmail = Authmodel.getEmail();
                if (currentUserEmail == null || currentUserEmail.isEmpty()) {
                    statusLabel.setText("❌ You must be logged in to submit.");
                    return;
                }


                try {
                    FarmerProfileController.storeProfile(name, address, mobileNo, email, bankAccountNo);
                    statusLabel.setText("✅ Profile saved successfully.");
                    initializeFarmerHome();
                    farmerPersonalInfoStage.setScene(FarmerHomeScene);
                } catch (Exception e) {
                    statusLabel.setText("❌ Failed to save profile: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        editBtn.setOnAction(e -> {
            nameField.clear();
            addressField.clear();
            noField.clear();
            mailField.clear();
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

        formBox.getChildren().addAll(
                heading,
                nameLbl, nameField,
                addressLbl, addressField,
                noLbl, noField,
                mailLbl, mailField,
                accLbl,accField,
                new HBox(10, submitBtn, editBtn),
                statusLabel
        );

        mainBox.getChildren().addAll(imageBox, formBox);
        root.setCenter(mainBox);
       
        VBox vbox = new VBox(root);
        return vbox;
    }
    public void initializeFarmerHome() {
        FarmerHome s1 = new FarmerHome();
        s1.setFarmerHomeStage(farmerPersonalInfoStage);
        FarmerHomeScene = new Scene(s1.createScene(this::handleBack), 1300, 700);
        s1.setFarmerHomeScene(FarmerHomeScene);
    }
    public void handleBack() {
        farmerPersonalInfoStage.setScene(farmerPersonalInfoScene);  // return to home scene
    }
    
}
