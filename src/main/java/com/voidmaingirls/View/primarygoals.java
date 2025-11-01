package com.voidmaingirls.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class primarygoals {

    Scene primarygoalsScene;
    public void setPrimarygoalsScene(Scene primarygoalsScene) {
        this.primarygoalsScene = primarygoalsScene;
    }

    Stage primarygoalStage;

    public void setPrimarygoalStage(Stage primarygoalStage) {
        this.primarygoalStage = primarygoalStage;
    }

    private VBox selectedBox = null;
    private String selectedGoalName = "";

    public VBox createScene( Runnable back) {
    

        BorderPane root = new BorderPane();

        // Background image
        Image backgroundImage = new Image(getClass().getResource("/Assets/Images/personalInfoBackground.png").toExternalForm());
        ImageView bgImageView = new ImageView(backgroundImage);
        bgImageView.setFitWidth(1300);
        bgImageView.setFitHeight(700);
        bgImageView.setPreserveRatio(false);
        root.getChildren().add(bgImageView);

        VBox container = new VBox(30);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(150, 0, 0, 0));

        Text heading = new Text("Select Your Primary Goal");
        heading.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 38));
        heading.setFill(Color.OLIVEDRAB);

        HBox goalsRow = new HBox(30);
        goalsRow.setAlignment(Pos.CENTER);

        goalsRow.getChildren().addAll(
                createGoalBox("Weight\nLoss"),
                createGoalBox("Muscle\nGain" ),
                createGoalBox("Tone &\nSculpt"),
                createGoalBox("Weight\n Gain")
        );

        container.getChildren().addAll(heading, goalsRow);

        StackPane centerPane = new StackPane(container);
        centerPane.setAlignment(Pos.CENTER);
        root.setCenter(centerPane);

        VBox vb = new VBox(root);
        return vb;
    }

    private VBox createGoalBox(String name) {
    VBox box = new VBox();
    box.setAlignment(Pos.CENTER);
    box.setSpacing(10);
    box.setPrefSize(220, 250);
    box.setPadding(new Insets(15));
    box.setStyle("-fx-background-color: #94b55ccc; -fx-background-radius: 15; -fx-border-color: #94b55ccc; -fx-border-radius: 15;");
    box.setEffect(new DropShadow(8, Color.rgb(200, 200, 200, 0.6)));

    // Add emoji to goal name
    String emoji = getEmojiForGoal(name);
    Text label = new Text(emoji + " " + name.replace("\n", " "));
    label.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 22));
    label.setFill(Color.web("#222"));

    box.getChildren().add(label);

    box.setOnMouseEntered(e -> applyHoverEffect(box));
    box.setOnMouseExited(e -> {
        if (box != selectedBox) removeHoverEffect(box);
    });

    box.setOnMouseClicked(e -> {
        if (selectedBox != null) {
            removeHoverEffect(selectedBox);
            selectedBox.setStyle("-fx-background-color: #94b55ccc; -fx-background-radius: 15; -fx-border-color: #94b55ccc; -fx-border-radius: 15;");
        }

        selectedBox = box;
        selectedGoalName = name;
        box.setStyle("-fx-background-color: #3a8e34ff; -fx-background-radius: 15; -fx-border-color: #3a8e34ff; -fx-border-width: 2; -fx-border-radius: 15;");
        box.setEffect(new DropShadow(20, Color.web("#3a8e34ff")));

        // Save to Firebase
        try {
            com.voidmaingirls.Controller.UserProfileController.storePrimaryGoal(selectedGoalName);
            System.out.println("Stored goal: " + selectedGoalName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Transition to Activity scene
        Activity activity = new Activity();
        activity.setActivityStage(primarygoalStage);
        primarygoalsScene = new Scene(activity.createScene(this::handleBack), 1300, 700);
        activity.setActivityScene(primarygoalsScene);
        primarygoalStage.setScene(primarygoalsScene);
    });

    return box;
}

private String getEmojiForGoal(String goalName) {
    if (goalName.toLowerCase().contains("weight") && goalName.toLowerCase().contains("loss")) return "⚖️";
    if (goalName.toLowerCase().contains("weight") && goalName.toLowerCase().contains("gain")) return "🍔";
    if (goalName.toLowerCase().contains("muscle")) return "💪";
    if (goalName.toLowerCase().contains("tone")) return "🎯";
    return "🎯";
}

public void handleBack() {
    primarygoalStage.setScene(primarygoalsScene);  // return to home scene
    }

private void applyHoverEffect(VBox box) {
    box.setScaleX(1.05);
    box.setScaleY(1.05);
    box.setStyle("-fx-background-color: #94b55ccc; -fx-background-radius: 15; -fx-border-color: #94b55ccc; -fx-border-radius: 15;");
    box.setEffect(new DropShadow(10, Color.web("#b4e1b1")));
}

private void removeHoverEffect(VBox box) {
    box.setScaleX(1);
    box.setScaleY(1);
    box.setEffect(new DropShadow(8, Color.rgb(200, 200, 200, 0.6)));
    box.setStyle("-fx-background-color: #94b55ccc; -fx-background-radius: 15; -fx-border-color: #94b55ccc; -fx-border-radius: 15;");
}

}
