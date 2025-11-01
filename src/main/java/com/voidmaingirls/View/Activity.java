package com.voidmaingirls.View;

import com.voidmaingirls.Controller.UserProfileController;

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

public class Activity {

    Scene activityScene,medicalHistoryScene;
    public void setActivityScene(Scene activityScene) {
        this.activityScene = activityScene;
    }

    Stage activityStage;
    public void setActivityStage(Stage activityStage) {
        this.activityStage = activityStage;
    }

    public VBox createScene(Runnable back) {
        StackPane root = new StackPane();

        // Background image with blur
        Image bgImage = new Image(getClass().getResource("/Assets/Images/personalInfoBackground.png").toExternalForm());
        ImageView bgImageView = new ImageView(bgImage);
        bgImageView.setFitWidth(1300);
        bgImageView.setFitHeight(700);
        bgImageView.setPreserveRatio(false);
        bgImageView.setEffect(new GaussianBlur(25));

        // Main container
        VBox container = new VBox(30);
        container.setPadding(new Insets(40));
        container.setAlignment(Pos.CENTER);
        container.setMaxWidth(800);
        container.setMaxHeight(500);
        container.setStyle(
            "-fx-background-color: rgba(255, 255, 255, 0.35);" +
            "-fx-background-radius: 25;" +
            "-fx-border-radius: 25;" +
            "-fx-border-color: #ffffff60;" +
            "-fx-border-width: 2;"
        );
        container.setEffect(new DropShadow(20, Color.gray(0.4)));

        // Title
        Label title = new Label("ACTIVITY");
        title.setFont(Font.font("Poppins", FontWeight.BOLD, 44));
        title.setTextFill(Color.web("#202020"));

        // Activity level dropdown
        ComboBox<String> activityLevel = new ComboBox<>();
        activityLevel.getItems().addAll("Low", "Moderate", "High");
        activityLevel.setPromptText("Select Physical Activity Level");
        activityLevel.setMaxWidth(350);
        activityLevel.setStyle(
            "-fx-background-color:  #b2ebf2;" +
            "-fx-border-color: #4dd0e1;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;" +
            "-fx-padding: 10 12;" +
            "-fx-font-size: 16px;" +
            "-fx-font-family: 'Poppins';"
        );

        // Calorie scroll bar
        ScrollBar calorieScroll = new ScrollBar();
        calorieScroll.setMin(1000);
        calorieScroll.setMax(5000);
        calorieScroll.setValue(2500);
        calorieScroll.setPrefWidth(350);
        calorieScroll.setBlockIncrement(100);
        calorieScroll.setStyle(
            "-fx-background-color: #004d40;" +
            "-fx-control-inner-background: #fafafa;" +
            "-fx-thumb-background-color: #76c7c0;" +
            "-fx-thumb-hover-color: #4db6ac;" +
            "-fx-thumb-border-color: #004d40;" +
            "-fx-background-insets: 0, 1;" +
            "-fx-padding: 8px;"
        );

        Label scrollValueLabel = new Label("Goal Calories per Day: 2500");
        scrollValueLabel.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 18));
        scrollValueLabel.setTextFill(Color.web("#222"));

        calorieScroll.valueProperty().addListener((obs, oldVal, newVal) -> {
            scrollValueLabel.setText("Goal Calories per Day: " + newVal.intValue());
        });

        // Buttons
        Button submitBtn = new Button("Submit");
        Button nextBtn = new Button("Next ▶");
        Button backBtn = new Button("◀ Back");

        String buttonStyle =
            "-fx-background-color:  #185a9d;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 18px;" +
            "-fx-padding: 10 22;" +
            "-fx-background-radius: 10;" +
            "-fx-cursor: hand;" +
            "-fx-font-family: 'Poppins';";

        submitBtn.setStyle(buttonStyle);
        nextBtn.setStyle(buttonStyle);
        backBtn.setStyle(buttonStyle);
        

        HBox buttonBox = new HBox(20, backBtn, submitBtn, nextBtn);
        buttonBox.setAlignment(Pos.CENTER);

        // Message label
        Label messageLabel = new Label("");
        messageLabel.setFont(Font.font("Poppins", FontWeight.MEDIUM, 18));
        messageLabel.setTextFill(Color.DARKBLUE);

        // Button functionality
        submitBtn.setOnAction(e -> {
            if (activityLevel.getValue() != null) {
                messageLabel.setText("Submitted Successfully! Goal: " + (int) calorieScroll.getValue());
                messageLabel.setTextFill(Color.FORESTGREEN);

            } else {
                messageLabel.setText("Please select your activity level.");
                messageLabel.setTextFill(Color.CRIMSON);
            }

            try {
                UserProfileController.activityLevel(activityLevel.getValue(), String.valueOf((int) calorieScroll.getValue()));
            } catch (Exception ex) {
                messageLabel.setText("Error saving data.");
                messageLabel.setTextFill(Color.CRIMSON);
            }
        });

        backBtn.setOnAction(e -> back.run());
        nextBtn.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event arg0) {
                intializeMedicalHistory();
                activityStage.setScene(medicalHistoryScene);
            }
            
        
        
        });

        // Add everything to container
        container.getChildren().addAll(
            title,
            activityLevel,
            scrollValueLabel,
            calorieScroll,
            buttonBox,
            messageLabel
        );

        // Root layer
        root.getChildren().addAll(bgImageView, container);

        // Final VBox layout
        VBox layout = new VBox(root);
        return layout;
    }

    public void intializeMedicalHistory(){
        MedicalHistoryPrompt s1 = new MedicalHistoryPrompt();
        s1.setMedicalPromptStage(activityStage);
        medicalHistoryScene= new Scene(s1.createScene(this:: handleBack),1300,700);
        s1.setMedicalPromptScene(medicalHistoryScene);
}
public void handleBack() {
    activityStage.setScene(activityScene);  // return to home scene
    }
}
