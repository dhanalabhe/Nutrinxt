package com.voidmaingirls.View;

import com.voidmaingirls.model.DeliveryOrder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FarmerPickupTracking {

     Scene farmerTrackScene;
     Stage farmerTrackStage;
     DeliveryOrder deliveryOrder; // ✅ Hold the delivery data here

    public void setFarmerTrackScene(Scene farmerTrackScene) {
        this.farmerTrackScene = farmerTrackScene;
    }

    public void setFarmerTrackStage(Stage farmerTrackStage) {
        this.farmerTrackStage = farmerTrackStage;
    }

    public void setDeliveryOrder(DeliveryOrder order) {
        this.deliveryOrder = order;
    }
   public VBox createScene(Runnable back) {
    StackPane root = new StackPane();              // line 31
    root.setStyle("-fx-background-color: #f4f4f4;");

    VBox container = new VBox();
    container.setAlignment(Pos.CENTER);

    VBox infoBox = new VBox(25);
    infoBox.setPadding(new Insets(20));
    infoBox.setMaxWidth(900);
    infoBox.setAlignment(Pos.TOP_CENTER);
    infoBox.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(15), Insets.EMPTY)));
    infoBox.setEffect(new DropShadow(10, Color.GRAY));

    Text heading = new Text("Track Farmer");
    heading.setFont(Font.font("Verdana", FontWeight.BOLD, 26));
    heading.setFill(Color.BLACK);

    ImageView mapView = new ImageView(new Image(getClass().getResource("/Assets/images/delvarytracking.jpg").toExternalForm()));
    mapView.setFitWidth(800);
    mapView.setFitHeight(350);
    mapView.setPreserveRatio(true);
    mapView.setSmooth(true);
    mapView.setEffect(new DropShadow(8, Color.LIGHTGRAY));

    VBox detailBox = new VBox(10);
    detailBox.setPadding(new Insets(15));
    detailBox.setAlignment(Pos.TOP_LEFT);
    detailBox.setMaxWidth(500);
    detailBox.setBackground(new Background(new BackgroundFill(Color.web("#f9f9f9"), new CornerRadii(10), Insets.EMPTY)));
    detailBox.setEffect(new DropShadow(5, Color.LIGHTGRAY));


    Button backBtn = new Button("Back");
    backBtn.setStyle("-fx-background-color: #f0ad4e; -fx-text-fill: white;");
    backBtn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    backBtn.setOnAction(e -> back.run());

    HBox backBtnContainer = new HBox(backBtn);
    backBtnContainer.setAlignment(Pos.TOP_LEFT);

    Button deliverBtn = new Button("Deliver");
    deliverBtn.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white;");
    deliverBtn.setFont(Font.font("Arial", FontWeight.BOLD, 14));

    Text deliveredText = new Text("Delivered Successfully!!!");
    deliveredText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    deliveredText.setFill(Color.DARKGREEN);
    deliveredText.setVisible(false);

    deliverBtn.setOnAction(e -> {
         back.run();
    });

    VBox deliverBox = new VBox(10, deliverBtn, deliveredText);
    deliverBox.setAlignment(Pos.CENTER);

    infoBox.getChildren().addAll(backBtnContainer, heading, detailBox, mapView, deliverBox);
    container.getChildren().add(infoBox);
    root.getChildren().add(container);

    return new VBox(root);
}


}
