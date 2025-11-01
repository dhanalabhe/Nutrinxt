package com.voidmaingirls.View;




import com.voidmaingirls.Controller.UserProfileController;
import com.voidmaingirls.model.Diettype;

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

public class DietTypePage {

    Scene DietPlanScene,HomeScene;
    public void setDietPlanScene(Scene dietPlanScene) {
        DietPlanScene = dietPlanScene;
    }

    public void setDietPlanStage(Stage dietPlanStage) {
        DietPlanStage = dietPlanStage;
    }

    Stage DietPlanStage;

    public VBox createScene(Runnable back) {
        StackPane root = new StackPane();

        // Background ImageView with blur effect
        Image bgImage = new Image("Assets/Images/personalInfoBackground.png");
        ImageView bgImageView = new ImageView(bgImage);
        bgImageView.setFitWidth(1300);
        bgImageView.setFitHeight(700);
        bgImageView.setPreserveRatio(false);
        bgImageView.setEffect(new GaussianBlur(20)); // Blur effect
        bgImageView.setSmooth(true);

        // White rounded container box
        VBox container = new VBox(50);
        container.setPadding(new Insets(30));
        container.setAlignment(Pos.CENTER);
        container.setMaxWidth(600);
        container.setMaxHeight(500);
        container.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3); -fx-background-radius: 20;");
        container.setEffect(new DropShadow(18, Color.GRAY));

        Label title = new Label("Select Your Diet Type");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
        title.setTextFill(Color.BLACK);

        ComboBox<String> dietCombo = new ComboBox<>();
        dietCombo.getItems().addAll("Vegetarian", "Non-Vegetarian", "Vegan", "Keto", "Low-Carb");
        dietCombo.setPromptText("Choose Diet Type");
        dietCombo.setMaxWidth(300);
        dietCombo.setMaxHeight(50);

        // Buttons
        Button submitBtn = new Button("Submit");
        submitBtn.setStyle("-fx-background-color: #0d202aff; -fx-text-fill: white");
        submitBtn.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 20));
        submitBtn.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event arg0) {
                String selectedDiet = dietCombo.getValue();
                if (selectedDiet == null || selectedDiet.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Please select a diet type.");
                    alert.showAndWait();
                    return;
                }
                Diettype dietType = new Diettype(selectedDiet);
                try {
                    UserProfileController.dietType(dietType.getDietType());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                
                intializeHOme();
                DietPlanStage.setScene(HomeScene); // return to home scene
            }
        });

        Button editBtn = new Button("Edit");
        editBtn.setStyle("-fx-background-color: #192447ff; -fx-text-fill: white");
        editBtn.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 20));

        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: #ede996ff; -fx-text-fill: white");
        backBtn.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 20));

        // Horizontal button layout
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(submitBtn, editBtn, backBtn);

        Label messageLabel = new Label("");
        messageLabel.setTextFill(Color.WHITESMOKE);
        messageLabel.setFont(Font.font("Poppins", 20));

        submitBtn.setOnAction(e -> messageLabel.setText("Submitted Successfully"));
        editBtn.setOnAction(e -> {
            dietCombo.getSelectionModel().clearSelection();
            messageLabel.setText("");
        });
        backBtn.setOnAction(e -> back.run());

        container.getChildren().addAll(title, dietCombo, buttonBox, messageLabel);
        root.getChildren().addAll(bgImageView, container);

        VBox scene = new VBox(root);
        return scene;
        
    }

    public void intializeHOme(){
        DashBoard s1 = new DashBoard();
        s1.setDashboardStage(DietPlanStage);
        HomeScene= new Scene(s1.createScene(this:: handleBack),1300,700);
        s1.setDashboardScene(HomeScene);
    }
    public void handleBack() {
        DietPlanStage.setScene(DietPlanScene);  // return to home scene
     }
}
