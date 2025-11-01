package com.voidmaingirls.View;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class delivaryhome {
    private Text logoutText; // Changed from ImageView
    Scene delivaryScene, pickupScene, dropScene;
    Stage delivaryStage;

    public void setDelivaryScene(Scene scene) {
        this.delivaryScene = scene;
    }

    public void setDelivaryStage(Stage stage) {
        this.delivaryStage = stage;
    }

    public VBox createScene(Runnable back) {
        StackPane root = new StackPane();

        

        // Beige base background
        Region beigeBackground = new Region();
        beigeBackground.setPrefSize(1300, 700);
        beigeBackground.setStyle("-fx-background-color: #f4f2e9;");

        // Green rounded top panel
        Region greenHeader = new Region();
        greenHeader.setPrefSize(1000, 350);
        greenHeader.setStyle(
            "-fx-background-color: #607D2B; " + // Deep farm green
            "-fx-background-radius: 0 0 40 40; " + // Rounded bottom corners
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0.2, 0, 2);"
        );

        // Place green header at top using VBox
        VBox backgroundLayers = new VBox();
        backgroundLayers.getChildren().addAll(greenHeader, new Region()); // stretch rest of scene
        VBox.setVgrow(backgroundLayers.getChildren().get(1), Priority.ALWAYS);

        // Logout Text replacing ImageView
        logoutText = new Text("LOGOUT");
        logoutText.setFont(Font.font("Poppins", FontWeight.BOLD, 24));
        logoutText.setFill(Color.web("#FF6961"));
        logoutText.setUnderline(true);
        logoutText.setStyle("-fx-cursor: hand;");
        


        // Style and hover
        logoutText.setOnMouseEntered(e -> logoutText.setFill(Color.LIGHTYELLOW));
        logoutText.setOnMouseExited(e -> logoutText.setFill(Color.WHITE));

        // Click event
        logoutText.setOnMouseClicked(e -> {
            openHomePage();
            System.out.println("Log out clicked");
        });

        // Manual position similar to image
        logoutText.setTranslateX(550);
        logoutText.setTranslateY(-280);

        // Left Heading Box
        VBox headingBox = new VBox(10);
        headingBox.setPadding(new Insets(10, 0, 0, 30));
        headingBox.setAlignment(Pos.TOP_LEFT);

        Text mainHeading = new Text("NutriNxt");
        Text subHeading = new Text("Welcome Delivery Agent !!!");
        mainHeading.setFont(Font.font("Poppins", FontWeight.EXTRA_BOLD, 120));
        mainHeading.setFill(Color.web("#fcfef8ff")); // Themed green
        mainHeading.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 4, 0.5, 2, 2);");

        subHeading.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 42));
        subHeading.setFill(Color.web("#0d0e0cff"));

        headingBox.getChildren().addAll(mainHeading, subHeading);

        // Right Buttons
        VBox rightBox = new VBox(20);
        rightBox.setPadding(new Insets(100, 50, 50, 0));
        rightBox.setAlignment(Pos.CENTER_LEFT);

        VBox pickupBox = createShadowBox("Pickup from Farmer", "pickup");
        VBox dropBox = createShadowBox("Drop at Customer", "drop");

        rightBox.getChildren().addAll(pickupBox, dropBox);

        BorderPane layout = new BorderPane();
        layout.setLeft(headingBox);
        layout.setPadding(new Insets(40, 60, 40, 60));

        StackPane.setAlignment(rightBox, Pos.CENTER);
        StackPane.setMargin(rightBox, new Insets(200, 50, 80, 400));

        // Add overlay for better text readability
        Region overlay = new Region();
        overlay.setPrefSize(1300, 700);
        overlay.setStyle("-fx-background-color: rgba(255, 255, 255, 0.4);");

        // Add base layers first
        root.getChildren().addAll(beigeBackground, backgroundLayers, layout, logoutText, rightBox);


        return new VBox(root);
    }

    private VBox createShadowBox(String text, String action) {
        VBox box = new VBox();
        box.setMinHeight(150);
        box.setMaxWidth(500);
        box.setBackground(new Background(new BackgroundFill(Color.web("#ffffffcc"), new CornerRadii(20), Insets.EMPTY)));
        box.setStyle("-fx-border-color: #7b9f33; -fx-border-width: 2px; -fx-border-radius: 20;");
        box.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.2)));
        box.setOnMouseEntered(e -> box.setStyle("-fx-border-color: #5b7f28; -fx-border-width: 2px; -fx-border-radius: 20; -fx-cursor: hand;"));
        box.setOnMouseExited(e -> box.setStyle("-fx-border-color: #7b9f33; -fx-border-width: 2px; -fx-border-radius: 20;"));
        box.setAlignment(Pos.CENTER);

        Text label = new Text(text);
        label.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 32));
        label.setFill(Color.web("#37474F"));
        box.getChildren().add(label);

        box.setOnMouseClicked(e -> {
            if (action.equals("pickup")) {
                initializePickupPage();
                delivaryStage.setScene(pickupScene);
            } else if (action.equals("drop")) {
                initializeDropPage();
                delivaryStage.setScene(dropScene);
            }
        });

        box.setOnMouseEntered(e -> {
            FadeTransition fade = new FadeTransition(Duration.millis(200), box);
            fade.setToValue(0.9);
            fade.play();
        });
        box.setOnMouseExited(e -> {
            FadeTransition fade = new FadeTransition(Duration.millis(200), box);
            fade.setToValue(1.0);
            fade.play();
        });

        return box;
    }

    public void initializePickupPage() {
        PickupPage pickupPage = new PickupPage();
        pickupPage.setPickupStage(delivaryStage);
        pickupScene = new Scene(pickupPage.createScene(this::handleBack), 1300, 700);
        pickupPage.setPickupScene(pickupScene);
    }

    public void initializeDropPage() {
        DropPage dropPage = new DropPage();
        dropPage.setDropStage(delivaryStage);
        dropScene = new Scene(dropPage.createScene(this::handleBack), 1300, 700);
        dropPage.setDropScene(dropScene);
    }

    private void handleBack() {
        delivaryStage.setScene(delivaryScene);
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