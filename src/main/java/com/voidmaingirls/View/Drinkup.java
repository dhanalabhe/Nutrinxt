package com.voidmaingirls.View;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Drinkup {

    Scene drinkupScene;
    Stage drinkupStage;

    public void setDrinkupScene(Scene drinkupScene) {
        this.drinkupScene = drinkupScene;
    }

    public void setDrinkupStage(Stage drinkupStage) {
        this.drinkupStage = drinkupStage;
    }

    private final List<DrinkTheme> drinkList = new ArrayList<>();
    private int currentIndex = 0;

    private ImageView centerCan;
    private Label bgText, drinkNameLabel, drinkDescriptionLabel;
    private StackPane root;

    public VBox createScene(Runnable back) {
        initializeDrinks();

        DrinkTheme theme = drinkList.get(0);

        root = new StackPane();
        root.setBackground(new Background(new BackgroundFill(Color.web(theme.bgColor), CornerRadii.EMPTY, Insets.EMPTY)));

        // 🍏 Background Label
        bgText = new Label(theme.name.toUpperCase());
        bgText.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 160));
        bgText.setTextFill(Color.web("#00000035"));
        StackPane.setAlignment(bgText, Pos.TOP_RIGHT);
        StackPane.setMargin(bgText, new Insets(120, 50, 0, 0));

        // ⬆⬇ Arrows (EXTREME LEFT)
        Button upArrow = createArrowButton("↑", e -> showPreviousDrink());
        Button downArrow = createArrowButton("↓", e -> showNextDrink());
        VBox arrowBox = new VBox(30, upArrow, downArrow);
        arrowBox.setAlignment(Pos.CENTER_LEFT);
        arrowBox.setPadding(new Insets(0, 120, 0, 20));

        // 🍹 Main Drink Image
        centerCan = new ImageView(new Image(getClass().getResourceAsStream(theme.imagePath)));
        centerCan.setFitWidth(340);
        centerCan.setTranslateY(80);
        centerCan.setTranslateX(-100);
        centerCan.setPreserveRatio(true);
        centerCan.setEffect(new DropShadow(30, Color.rgb(0, 0, 0, 0.3)));

        VBox canBox = new VBox(centerCan);
        canBox.setAlignment(Pos.CENTER_LEFT);

        HBox leftSide = new HBox(arrowBox, canBox);
        leftSide.setAlignment(Pos.CENTER_LEFT);
        leftSide.setSpacing(20);
        leftSide.setPadding(new Insets(0, 0, 0, 20));

        // 🍸 Name + Description + Shop
        drinkNameLabel = new Label(theme.name);
        drinkNameLabel.setFont(Font.font("Poppins", FontWeight.EXTRA_BOLD, 54));
        drinkNameLabel.setTextFill(Color.WHITE);

        drinkDescriptionLabel = new Label(theme.description + "\n\nThis refreshing juice is packed with natural flavor and nutrients to quench your thirst and energize your body. Perfect for hot days, workouts, and relaxing afternoons.");
        drinkDescriptionLabel.setFont(Font.font("Poppins", 24));
        drinkDescriptionLabel.setTextFill(Color.WHITE);
        drinkDescriptionLabel.setWrapText(true);
        drinkDescriptionLabel.setMaxWidth(400);

        VBox rightContent = new VBox(30, drinkNameLabel, drinkDescriptionLabel);
        rightContent.setAlignment(Pos.CENTER_RIGHT);
        rightContent.setPadding(new Insets(30, 60, 0, 0));

        // ⬅ Back Button
        Button backButton = new Button("←");
        backButton.setStyle(
            "-fx-font-size: 24px;" +
            "-fx-background-color:  rgba(255,255,255,0.5);" +
            "-fx-text-fill: white;" +
            "-fx-cursor: hand;"
        );
        backButton.setPrefSize(65, 65);

        backButton.setOnMouseClicked(e -> back.run());
        StackPane.setAlignment(backButton, Pos.TOP_LEFT);
        StackPane.setMargin(backButton, new Insets(20, 0, 0, 20));
        

        // 🔤 Title
        Label title = new Label("DrinkUp");
        title.setFont(Font.font("Poppins", FontWeight.BOLD, 100));
        title.setTextFill(Color.WHITE);
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setMargin(title, new Insets(20, 300, 0, 0));

        // 🧃 Main layout
        HBox layout = new HBox(40, leftSide, rightContent);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(60);

        root.getChildren().addAll(bgText, title, layout, backButton);
        root.setMinHeight(700);
        return new VBox(root);
    }

    private Button createArrowButton(String arrow, javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        Button arrowBtn = new Button(arrow);
        arrowBtn.setStyle("-fx-font-size: 28px; -fx-background-color: rgba(255,255,255,0.5); -fx-text-fill: white;");
        arrowBtn.setPrefSize(55, 55);
        arrowBtn.setOnAction(handler);
        return arrowBtn;
    }

    private void updateDrink(int index) {
        currentIndex = index;
        DrinkTheme theme = drinkList.get(currentIndex);

        centerCan.setImage(new Image(getClass().getResourceAsStream(theme.imagePath)));
        bgText.setText(theme.name.toUpperCase());
        drinkNameLabel.setText(theme.name);
        drinkDescriptionLabel.setText(theme.description + "\n\nThis refreshing juice is packed with natural flavor and nutrients to quench your thirst and energize your body. Perfect for hot days, workouts, and relaxing afternoons.");

        FadeTransition fade = new FadeTransition(Duration.seconds(0.5), root);
        fade.setFromValue(0.4);
        fade.setToValue(1.0);
        fade.play();

        Background newBg = new Background(new BackgroundFill(Color.web(theme.bgColor), CornerRadii.EMPTY, Insets.EMPTY));
        Timeline bgChange = new Timeline(new KeyFrame(Duration.seconds(0.3), new KeyValue(root.backgroundProperty(), newBg)));
        bgChange.play();

        ScaleTransition bounce = new ScaleTransition(Duration.seconds(0.3), centerCan);
        bounce.setFromX(1.1); bounce.setFromY(1.1);
        bounce.setToX(1); bounce.setToY(1);
        bounce.play();
    }

    private void showNextDrink() {
        currentIndex = (currentIndex + 1) % drinkList.size();
        updateDrink(currentIndex);
    }

    private void showPreviousDrink() {
        currentIndex = (currentIndex - 1 + drinkList.size()) % drinkList.size();
        updateDrink(currentIndex);
    }

    private void initializeDrinks() {
    drinkList.add(new DrinkTheme("Kiwi", "/Assets/Images/kiwi2.png", "#A5D6A7", "Sweet and refreshing green pear juice with a crisp finish."));
    drinkList.add(new DrinkTheme("Blue Berry", "/Assets/Images/malbery.png", "#e4bcffff", "Tropical blend of pineapple, papaya, and mango."));
    drinkList.add(new DrinkTheme("Peach", "/Assets/Images/peach.png", "#ff9fc7ff", "Peach juice is a naturally sweet and juicy drink loaded with vitamins A and C."));
    drinkList.add(new DrinkTheme("Watermelon", "/Assets/Images/watermelon.png", "#ff8282ff", "A hydrating and cooling juice, watermelon juice is perfect for hot days."));
    drinkList.add(new DrinkTheme("Orange", "/Assets/Images/orangejucice.png", "#ffce48ff", "A classic citrus delight, orange juice is a powerhouse of vitamin C."));
    drinkList.add(new DrinkTheme("Banana", "/Assets/Images/banana.png", "#FFF176", "Naturally sweet and creamy, banana juice is rich in potassium."));
    drinkList.add(new DrinkTheme("Beetroot", "/Assets/Images/beatroot.png", "#ff7070ff", "A vibrant blend of red fruits packed with antioxidants and natural sweetness."));
    drinkList.add(new DrinkTheme("Avocado", "/Assets/Images/Avacado.png", "#B2D88C", "Creamy and nutrient-dense, avocado juice is a unique drink."));
    drinkList.add(new DrinkTheme("Apple", "/Assets/Images/applejuicce.png", "#FFAFA1", "Fresh and juicy red apple juice to energize your day."));
    drinkList.add(new DrinkTheme("Mango", "/Assets/Images/mangoJuice.png", "#FFE082", "Rich and pulpy Alphonso mango juice bursting with flavor."));
    drinkList.add(new DrinkTheme("Strawberry", "/Assets/Images/Strawberry.png", "#f5a3a3ff", "Strawberry juice is a deliciously sweet and tangy drink loaded with antioxidants."));
}


    static class DrinkTheme {
        String name, imagePath, bgColor, description;

        public DrinkTheme(String name, String imagePath, String bgColor, String description) {
            this.name = name;
            this.imagePath = imagePath;
            this.bgColor = bgColor;
            this.description = description;
        }
    }
}