package com.voidmaingirls.View;





import com.google.cloud.storage.Acl.User;
import com.voidmaingirls.Controller.UserProfileController;
import com.voidmaingirls.model.Authmodel;
import com.voidmaingirls.model.MedicalConditionModel;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MedicalDetailsPage {

    Scene medicalScene,dietScene;
    Stage medicalStage;

    public void setMedicalScene(Scene medicalScene) {
        this.medicalScene = medicalScene;
    }

    public void setMedicalStage(Stage medicalStage) {
        this.medicalStage = medicalStage;
    }

    public VBox createScene(Runnable back) {
        StackPane root = new StackPane();

        // Background image with blur
        Image bgImage = new Image("Assets/Images/personalInfoBackground.png");
        ImageView bgImageView = new ImageView(bgImage);
        bgImageView.setFitWidth(1300);
        bgImageView.setFitHeight(700);
        bgImageView.setPreserveRatio(false);
        bgImageView.setEffect(new GaussianBlur(20));
        bgImageView.setSmooth(true);

        // Main container box with shadow and transparency
        VBox container = new VBox(25);
        container.setPadding(new Insets(30));
        container.setAlignment(Pos.CENTER);
        container.setMaxWidth(600);
        container.setMaxHeight(500);
        container.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3); -fx-background-radius: 20;");
        container.setEffect(new DropShadow(18, Color.DARKGRAY));

        // Title with shadow
        Label title = new Label("Medical History Details");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
        title.setTextFill(Color.BLACK);
        DropShadow textShadow = new DropShadow(4, 2, 2, Color.GRAY);
        title.setEffect(textShadow);

        // Checkboxes vertically centered
        VBox checkboxBox = new VBox(10);
       // checkboxBox.setAlignment(Pos.CENTER);
       checkboxBox.setLayoutX(100);
       checkboxBox.setLayoutY(80);

        String[] conditions = {
            "Diabetes", "Blood Pressure", "Thyroid", "Asthma", "Allergy",
            "Heart Condition", "Cholesterol",  "Migraine",
            "Depression", "Anxiety", "Kidney Disease", "Liver Disease"
        };

        for (String condition : conditions) {
            CheckBox checkBox = new CheckBox(condition);
            checkBox.setFont(Font.font("Poppins", FontWeight.BOLD, 16));
            checkboxBox.getChildren().add(checkBox);
        }

        // Message label
        Label messageLabel = new Label("");
        messageLabel.setTextFill(Color.WHITESMOKE);
        messageLabel.setFont(Font.font("Poppins",FontWeight.EXTRA_BOLD, 24));

        // Horizontal button layout
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        Button submitBtn = new Button("Submit");
        submitBtn.setStyle("-fx-background-color: #132731ff; -fx-text-fill: white");
        submitBtn.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 18));

        Button editBtn = new Button("Edit");
        editBtn.setStyle("-fx-background-color: #122144ff; -fx-text-fill: white");
        editBtn.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 18));

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: #fcefbeff; -fx-text-fill: black");
        backBtn.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 18));

        buttonBox.getChildren().addAll(submitBtn, editBtn, backBtn);

        // Event handling
        submitBtn.setOnAction(e -> {
    // Collect selected conditions
    StringBuilder selectedConditions = new StringBuilder();
    checkboxBox.getChildren().forEach(node -> {
        if (node instanceof CheckBox checkBox && checkBox.isSelected()) {
            selectedConditions.append(checkBox.getText()).append(", ");
        }
    });

    // Remove the trailing comma and space
    if (selectedConditions.length() > 0) {
        selectedConditions.setLength(selectedConditions.length() - 2);
    }

    // Check if any condition was selected
    if (selectedConditions.length() == 0) {
        messageLabel.setText("Please select at least one condition.");
        messageLabel.setTextFill(Color.CRIMSON);
    } else {
        // Create a MedicalConditionModel with the selected conditions
        MedicalConditionModel userMedicalConditions = new MedicalConditionModel(selectedConditions.toString());

        // Save to Firebase
        try {
            UserProfileController.medicalConditionl(selectedConditions.toString());

            messageLabel.setText("Submitted Successfully!!!!");
            messageLabel.setTextFill(Color.FORESTGREEN);

            // Initialize and switch to the next page (diet page)
            intializeDietPage();
            medicalStage.setScene(dietScene);
        } catch (Exception ex) {
            messageLabel.setText("Failed to save data.");
            messageLabel.setTextFill(Color.CRIMSON);
            ex.printStackTrace();
        }
    }
});

        editBtn.setOnAction(e -> {
            checkboxBox.getChildren().forEach(node -> {
                if (node instanceof CheckBox checkBox) {
                    checkBox.setSelected(false);
                }
            });
            messageLabel.setText("");

        });
        backBtn.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event arg0) {
                back.run();
            }
            
        });

        // Add all elements to container
        container.getChildren().addAll(title, checkboxBox, buttonBox, messageLabel);

        // Add background and content to root
        root.getChildren().addAll(bgImageView, container);

        VBox scene = new VBox(root);

        return scene;
        


    }

     public void intializeDietPage(){
        DietTypePage s1 = new DietTypePage();
        s1.setDietPlanStage(medicalStage);
        dietScene= new Scene(s1.createScene(this:: handleBack),1300,700);
        s1.setDietPlanScene(dietScene);
    }
    public void handleBack() {
        medicalStage.setScene(medicalScene); 
    }
}
