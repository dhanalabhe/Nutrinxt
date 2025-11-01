package com.voidmaingirls.View;



import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class DelivaryStatusFarmer {
    
    Stage pickupStage;
    public void setPickupStage(Stage pickupStage) {
        this.pickupStage = pickupStage;
    }
    Scene pickupScene;
    public void setPickupScene(Scene pickupScene) {
        this.pickupScene = pickupScene;
    }

    public VBox createScene(Runnable back){

        StackPane root = new StackPane();

        VBox content = new VBox(20);
        content.setPadding(new Insets(40));
        content.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Order Tracking");
        title.setFont(Font.font("Roboto", FontWeight.BOLD, 60));
        title.setTextFill(Color.BLACK);

        VBox trackingSteps = createTrackingSteps();

        ScrollPane scrollPane = new ScrollPane(trackingSteps);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(400);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setMaxWidth(700);

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #2E7D32; -fx-text-fill: white; -fx-font-size: 16;");
        backButton.setOnAction(e -> back.run());

        content.getChildren().addAll(title, scrollPane, backButton);
        content.setAlignment(Pos.CENTER);
        root.getChildren().add(content);

        VBox vb = new VBox(root);
        return vb;
    }

    private VBox createTrackingSteps() {
        String[][] steps = {
            {"9:30 AM", "Order Placed", "Your order was placed and is scheduled for delivery."},
            {"9:35 AM", "Pending", "Awaiting confirmation from the delivery partner."},
            {"9:55 AM", "Pick-up Confirmed", "The delivery agent has confirmed pick-up."},
            {"10:10 AM", "Processing", "The agent is on the way to pick up your items."},
            {"10:30 AM", "Picked Up", "The items have been successfully picked up."}
        };

        VBox stepList = new VBox(15);
        stepList.setPadding(new Insets(10));

        for (String[] step : steps) {
            VBox card = new VBox(5);
            card.setPadding(new Insets(15));
            card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10; -fx-border-color: #4CAF50; -fx-border-radius: 10;");
            card.setMaxWidth(650);

            Label title = new Label(step[1]);
            title.setFont(Font.font("Roboto", FontWeight.BOLD, 18));
            title.setTextFill(Color.BLACK);

            Label description = new Label(step[2]);
            description.setFont(Font.font("Roboto", 14));
            description.setWrapText(true);
            description.setTextFill(Color.BLACK);

            Label time = new Label(step[0]);
            time.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
            time.setTextFill(Color.BLACK);

            card.getChildren().addAll(title, description, time);
            stepList.getChildren().add(card);
        }

        return stepList;
    }


}
