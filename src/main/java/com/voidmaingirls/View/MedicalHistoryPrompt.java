package com.voidmaingirls.View;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MedicalHistoryPrompt {

    public Scene medicalPromptScene,medicalScene,dietScene;

    public void setMedicalPromptScene(Scene medicalPromptScene) {
        this.medicalPromptScene = medicalPromptScene;
    }

    public Stage medicalPromptStage;


    public void setMedicalPromptStage(Stage medicalPromptStage) {
        this.medicalPromptStage = medicalPromptStage;
    }

    public VBox createScene(Runnable back) {
        StackPane root = new StackPane();

        // Background image with blur
        Image bgImage = new Image(getClass().getResource("/Assets/Images/personalInfoBackground.png").toExternalForm());
        ImageView bgImageView = new ImageView(bgImage);
        bgImageView.setFitWidth(1300);
        bgImageView.setFitHeight(700);
        bgImageView.setPreserveRatio(false);
        bgImageView.setEffect(new GaussianBlur(20));
        bgImageView.setSmooth(true);

         Button backButton = new Button("← Back");
        backButton.setLayoutX(30);
        backButton.setLayoutY(30);
        backButton.setStyle("-fx-font-size: 16px; -fx-background-color: olivedrab; -fx-text-fill: white;");
        backButton.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                back.run();
            }
        });

        // Main container
        VBox container = new VBox(25,backButton);
        container.setPadding(new Insets(40));
        container.setAlignment(Pos.CENTER);
        container.setMaxWidth(500);
        container.setMaxHeight(250);
        container.setStyle("-fx-background-color: rgba(255, 255, 255, 0.25); -fx-background-radius: 15;");
        container.setEffect(new DropShadow(15, Color.rgb(0, 0, 0, 0.2)));

        // Question Label
        Label question = new Label("Do you have a medical history?");
        question.setFont(Font.font("Segoe UI", 30));
        question.setTextFill(Color.web("#222222"));
        question.setWrapText(true);
        question.setAlignment(Pos.CENTER);

        // Buttons
        HBox buttons = new HBox(25);
        buttons.setAlignment(Pos.CENTER);

        Button yesBtn = new Button("Yes");
        yesBtn.setStyle("""
                -fx-background-color: #3F51B5;
                -fx-text-fill: white;
                -fx-font-size: 16px;
                -fx-font-family: 'Segoe UI';
                -fx-background-radius: 8;
                -fx-padding: 8 20;
                """);

        Button noBtn = new Button("No");
        noBtn.setStyle("""
                -fx-background-color: #607D8B;
                -fx-text-fill: white;
                -fx-font-size: 16px;
                -fx-font-family: 'Segoe UI';
                -fx-background-radius: 8;
                -fx-padding: 8 20;
                """);

        // Button Actions
        yesBtn.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event arg0) {
               intializeMedical();
               medicalPromptStage.setScene(medicalScene);

            }
            
        });
         noBtn.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event arg0) {
               intializeDietPage();
               medicalPromptStage.setScene(dietScene);

            }
            
        });
        // noBtn.setOnAction(e -> {
        //     DietTypePage pg = new DietTypePage();
        //     pg.setPrimaryStage(medicalPromptStage);
        //     pg.createScene();
        //     medicalPromptStage.setScene(pg.getScene());
        // });

    

        buttons.getChildren().addAll(yesBtn, noBtn);
        container.getChildren().addAll(question, buttons);

        root.getChildren().addAll(bgImageView, container);
        medicalPromptScene = new Scene(root, 1300, 700);

        VBox vb = new VBox(root);
        return vb;
    }
    public void intializeMedical(){
        MedicalDetailsPage s1 = new MedicalDetailsPage();
        s1.setMedicalStage(medicalPromptStage);
        medicalScene= new Scene(s1.createScene(this:: handleBack),1300,700);
        s1.setMedicalScene(medicalScene);
}
public void handleBack() {
    medicalPromptStage.setScene(medicalPromptScene);  // return to home scene
    }

    
     public void intializeDietPage(){
        DietTypePage s1 = new DietTypePage();
        s1.setDietPlanStage(medicalPromptStage);
        dietScene= new Scene(s1.createScene(this:: handleBack),1300,700);
        s1.setDietPlanScene(dietScene);
    }
}
