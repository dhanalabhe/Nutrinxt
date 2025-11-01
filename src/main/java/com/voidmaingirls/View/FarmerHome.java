package com.voidmaingirls.View;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FarmerHome {
    private Text logoutText; // Changed from ImageView
    Scene homeScene, orderScene, historyScene, deliveryScene, home2Scene;
    Stage homeStage;

    public void setFarmerHomeScene(Scene scene) {
        this.homeScene = scene;
    }

    public void setFarmerHomeStage(Stage stage) {
        this.homeStage = stage;
    }

    public VBox createScene(Runnable back) {
        Image homeBackImage = new Image("Assets/Images/farmerBackground.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
                homeBackImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1300, 700, false, false, false, false)
        );
        Background background = new Background(backgroundImage);

        // Header texts
        Text home = new Text("HOME");
        Text about = new Text("ABOUT");
        Text help = new Text("HELP");

        for (Text t : new Text[]{home, about, help}) {
            t.setFill(Color.BEIGE);
            t.setFont(new Font("Roboto", 28));
        }

        // Logout Text replacing ImageView
        logoutText = new Text("LOGOUT");
        logoutText.setFont(Font.font("Roboto", FontWeight.BOLD, 24));
        logoutText.setFill(Color.BEIGE);
        logoutText.setStyle("-fx-cursor: hand;");

        // Hover effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.web("#aacc77"));
        dropShadow.setRadius(15);

        logoutText.setOnMouseEntered(e -> {
            logoutText.setEffect(dropShadow);
            logoutText.setFill(Color.LIGHTGREEN);
        });

        logoutText.setOnMouseExited(e -> {
            logoutText.setEffect(null);
            logoutText.setFill(Color.BEIGE);
        });

        // Click to go home
        logoutText.setOnMouseClicked(e -> openHomePage());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(30, home, about, help, spacer, logoutText);
        header.setPadding(new Insets(0, 30, 0, 30));
        header.setStyle("-fx-background-color: rgba(102, 142, 20, 1);");
        header.setMinWidth(1300);
        header.setMinHeight(80);
        header.setAlignment(Pos.CENTER);

        for (Text t : new Text[]{home, about, help}) {
            t.setOnMouseEntered(e -> t.setFill(Color.web("#cce68c")));
            t.setOnMouseExited(e -> t.setFill(Color.BEIGE));
        }

        // Center logo and text
        ImageView homeText = new ImageView(new Image("/Assets/Images/logo.png"));
        homeText.setLayoutX(110);
        homeText.setLayoutY(230);
        homeText.setFitHeight(300);
        homeText.setFitWidth(700);

        Text homeText2 = new Text("Farm to Doorstep!!");
        homeText2.setFont(Font.font("Poppins", FontWeight.BOLD, 32));
        homeText2.setLayoutX(270);
        homeText2.setLayoutY(560);
        homeText2.setFill(Color.BEIGE);

        // Orders button
        StackPane ordersCircle = createCardButton("Order Request", 950, 120);
        ordersCircle.setOnMouseClicked(e -> {
            try {
                initializeOrderRequestPage();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            homeStage.setScene(orderScene);
        });

        // Delivery button
        StackPane deliveryCircle = createCardButton("Delivery Tracking", 950, 410);
        deliveryCircle.setOnMouseClicked(e -> {
            initializeDeliveryPage();
            homeStage.setScene(deliveryScene);
        });

        Rectangle overlay = new Rectangle(1300, 700, Color.rgb(255, 255, 255, 0.20));

        Pane root = new Pane();
        root.getChildren().addAll(overlay, header, homeText, homeText2, ordersCircle, deliveryCircle);
        root.setBackground(background);
        root.setMaxHeight(750);

        VBox buttonBox = new VBox(60, ordersCircle, deliveryCircle);
        buttonBox.setLayoutX(850);
        buttonBox.setLayoutY(200);
        root.getChildren().add(buttonBox);

        VBox vb = new VBox(root);
        vb.setMinHeight(850);
        vb.setMaxWidth(1300);

        return new VBox(vb);
    }

    private StackPane createCardButton(String label, double x, double y) {
        Rectangle card = new Rectangle(350, 180);
        card.setArcWidth(25);
        card.setArcHeight(25);
        card.setFill(Color.BEIGE);
        card.setStroke(Color.OLIVEDRAB);
        card.setStrokeWidth(2);
        card.setEffect(new DropShadow(10, Color.web("#b2cc67")));

        Text text = new Text(label);
        text.setFill(Color.web("#668e14"));
        text.setFont(Font.font("Poppins", FontWeight.BOLD, 24));

        StackPane cardStack = new StackPane(card, text);
        cardStack.setLayoutX(x);
        cardStack.setLayoutY(y);
        cardStack.setStyle("-fx-cursor: hand;");

        applyHoverEffect(cardStack);
        return cardStack;
    }

    private void applyHoverEffect(StackPane node) {
        Glow glow = new Glow(0.3);
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), node);
        scaleUp.setToX(1.2);
        scaleUp.setToY(1.2);

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), node);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);

        DropShadow shadow = new DropShadow(15, Color.web("#aacc77"));

        node.setOnMouseEntered(e -> {
            node.setEffect(shadow);
            scaleUp.playFromStart();
        });
        node.setOnMouseExited(e -> {
            node.setEffect(null);
            scaleDown.playFromStart();
        });
    }

    private void initializeOrderRequestPage() throws Exception {
        OrderRequest od = new OrderRequest();
        od.setOrderRequestStage(homeStage);
        orderScene = new Scene(od.createScene(this::handleBackButton), 1300, 700);
        od.setOrderRequestScene(orderScene);
    }

    private void handleBackButton() {
        homeStage.setScene(homeScene);
    }

    private void initializeDeliveryPage() {
        DelivaryStatusFarmer od = new DelivaryStatusFarmer();
        od.setPickupStage(homeStage);
        deliveryScene = new Scene(od.createScene(this::handleBackButton), 1300, 700);
        od.setPickupScene(deliveryScene);
    }

    private void openHomePage() {
        try {
            Home homePage = new Home();
            homePage.start((Stage) logoutText.getScene().getWindow());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}