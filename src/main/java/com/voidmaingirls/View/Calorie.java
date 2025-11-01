package com.voidmaingirls.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Calorie {
    Scene calorieScene;
    Stage calorieStage;

    public void setCalorieScene(Scene calorieScene) {
        this.calorieScene = calorieScene;
    }

    public void setCalorieStage(Stage calorieStage) {
        this.calorieStage = calorieStage;
    }

    private VBox mealSection;
    private Arc progressArc;
    private Label centerText;
    private Label eatenValue;
    private Label proteinLabel, carbLabel, fatLabel;
    private ProgressBar proteinBar, carbBar, fatBar;

    public VBox createScene(Runnable back) {
        Button backButton = new Button(" ← Back");
        backButton.setStyle(
            "-fx-font-size: 16px;" +
            "-fx-background-color: olivedrab;" +
            "-fx-text-fill: white;" +
            "-fx-padding: 8 16;" +
            "-fx-background-radius: 8;" +
            "-fx-font-family: 'Poppins';"
        );
        backButton.setOnMouseClicked(event -> back.run());

        Label headingLabel = new Label("Calorie Vault");
        headingLabel.setFont(Font.font("Montserrat", FontWeight.EXTRA_BOLD, 52));
        headingLabel.setTextFill(Color.web("#1ee280ff"));
        HBox headingBox = new HBox(headingLabel);
        headingBox.setAlignment(Pos.TOP_CENTER);

        HBox greenBox = new HBox(5);
        greenBox.setPadding(new Insets(30));
        greenBox.setAlignment(Pos.CENTER);
        greenBox.setStyle("-fx-background-color: #FAFFEC; -fx-background-radius: 16;");
        greenBox.setMaxWidth(1000);
        greenBox.setMinHeight(500);
        greenBox.setEffect(new DropShadow(15, Color.rgb(0, 0, 0, 0.2)));

        Label topCenterLabel = new Label("Today's Progress ");
        topCenterLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 38));
        topCenterLabel.setTextFill(Color.web("#5dc26aff"));

        TextField foodInput = new TextField();
        foodInput.setPromptText("Enter Dish");
        foodInput.setMaxWidth(350);
        foodInput.setMinHeight(40);
        foodInput.setFont(Font.font("Poppins", 28));
        foodInput.setStyle("-fx-background-radius: 8; -fx-border-color: #B0BEC5; -fx-border-radius: 8;");

        Button countButton = new Button("Count");
        countButton.setStyle("-fx-background-color: #265F48; -fx-background-radius: 8; -fx-text-fill: white; -fx-font-weight: bold;");
        countButton.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 30));
        countButton.setPrefWidth(350);
        countButton.setMinHeight(40);

        VBox inputBox = new VBox(12, foodInput, countButton);
        inputBox.setAlignment(Pos.CENTER);

        StackPane ringStack = new StackPane();
//    ringStack.setPrefSize(320, 320);

// Background ring (static border)
Circle borderRing = new Circle(120);
borderRing.setStroke(Color.web("#D0EED2")); // lighter border
borderRing.setFill(Color.TRANSPARENT);
borderRing.setStrokeWidth(20);

// Progress arc (remaining calories)
progressArc = new Arc(0, 0, 100, 100, 90, 360); // Full circle initially
progressArc.setType(ArcType.OPEN);
progressArc.setStroke(Color.web("#FF7043")); // Orange stroke
progressArc.setStrokeWidth(18);
progressArc.setFill(Color.TRANSPARENT);
progressArc.setStrokeLineCap(javafx.scene.shape.StrokeLineCap.ROUND);
progressArc.setEffect(new DropShadow(10, Color.rgb(255, 112, 67, 0.3)));
progressArc.setStroke(new LinearGradient(
    0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
    new Stop(0, Color.web("#FFA726")),
    new Stop(1, Color.web("#EF5350"))
));



// Inner white circle
Circle innerCircle = new Circle(90, Color.WHITE);

// Center labels
centerText = new Label("Remaining\n2000 kcal");
centerText.setFont(Font.font("Poppins", FontWeight.BOLD, 20));
centerText.setTextFill(Color.web("#37474F"));
centerText.setAlignment(Pos.CENTER);
centerText.setStyle("-fx-text-alignment: center;");

eatenValue = new Label("0 kcal");
eatenValue.setFont(Font.font("Poppins", FontWeight.NORMAL, 14));
eatenValue.setTextFill(Color.web("#546E7A"));

VBox ringLabels = new VBox(6, centerText, eatenValue);
ringLabels.setAlignment(Pos.CENTER);

ringStack.getChildren().addAll(borderRing, progressArc, innerCircle, ringLabels);

        ringStack.setPrefSize(320, 320);

        HBox ringRow = new HBox(ringStack);
        ringRow.setAlignment(Pos.CENTER);

        proteinBar = new ProgressBar(0);
        carbBar = new ProgressBar(0);
        fatBar = new ProgressBar(0);
        proteinLabel = new Label("Protein:\n 0g");
        carbLabel = new Label("Carbs:\n 0g");
        fatLabel = new Label("Fats:\n 0g");

        VBox setting2Box = new VBox(30, topCenterLabel, inputBox, ringRow);

        HBox macrosBox = new HBox(20,
                createMacroRow(proteinLabel, proteinBar, "#66BB6A"),
                createMacroRow(carbLabel, carbBar, "#FFA726")
        );
        VBox macrBox = new VBox(10, createMacroRow(fatLabel, fatBar, "#EF5350"));
        macrosBox.setAlignment(Pos.CENTER);
        macrosBox.setPadding(new Insets(20));
        macrBox.setAlignment(Pos.CENTER);
        macrBox.setPadding(new Insets(20));

        VBox settingBox = new VBox(30, macrosBox, macrBox);
        settingBox.setAlignment(Pos.CENTER);
        settingBox.setPadding(new Insets(20));

        Label mealLabel = new Label("📝 Meals Logged");
        mealLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 26));
        mealLabel.setTextFill(Color.web("#263238"));
        mealSection = new VBox(15);
        mealSection.setPadding(new Insets(10));
        mealSection.setSpacing(10);
        //mealSection.setFon
        mealSection.setAlignment(Pos.BASELINE_CENTER);
        mealSection.setStyle("-fx-background-color: #FAFFEC; -fx-background-radius: 16; -fx-border-color: #CFD8DC; -fx-border-radius: 16; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 6, 0, 0, 2);");
        mealSection.setMaxWidth(900);
        mealSection.getChildren().add(mealLabel);

        VBox hb = new VBox(20, settingBox);
        hb.setAlignment(Pos.CENTER);

        greenBox.getChildren().addAll(setting2Box, hb);
        VBox mainBox = new VBox(30, greenBox, mealSection);
        VBox header = new VBox(10, backButton, headingBox);
        VBox mainLayout = new VBox(20, mainBox);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(30));
        mainLayout.setStyle("-fx-background-color: #e3f2e1;");

        countButton.setOnAction(e -> {
            String food = foodInput.getText().trim();
            if (food.isEmpty()) return;

            countButton.setDisable(false);
            countButton.setText("Loading...");

            new Thread(() -> {
                String prompt = "Give me only the calorie (kcal), protein (g), carbs (g), and fats (g) of the food item: " + food +
                        ". Respond only in this format:\nCalories: xxx kcal\nProtein: xx g\nCarbs: xx g\nFats: xx g";

                String response = com.voidmaingirls.Controller.GeminiAPI.getGeminiResponse(prompt);

                int cal = 0;
                double protein = 0, carbs = 0, fat = 0;
                try {
                    for (String line : response.split("\n")) {
                        if (line.toLowerCase().contains("calories")) cal = Integer.parseInt(line.replaceAll("[^0-9]", ""));
                        else if (line.toLowerCase().contains("protein")) protein = Double.parseDouble(line.replaceAll("[^0-9.]", ""));
                        else if (line.toLowerCase().contains("carbs") || line.toLowerCase().contains("carbohydrates")) carbs = Double.parseDouble(line.replaceAll("[^0-9.]", ""));
                        else if (line.toLowerCase().contains("fats") || line.toLowerCase().contains("fat")) fat = Double.parseDouble(line.replaceAll("[^0-9.]", ""));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                final int finalCal = cal;
                final double finalProtein = protein, finalCarbs = carbs, finalFat = fat;
                double progress = Math.min(1.0, finalCal / 2000.0);
                double remaining = 1.0 - progress;
                double arcLength = remaining * 360;

                progressArc.setStartAngle(90);
                progressArc.setLength(-arcLength);

                javafx.application.Platform.runLater(() -> {
                    eatenValue.setText(finalCal + " kcal");
                    centerText.setText("Remaining\n" + Math.max(0, 2000 - finalCal) + " kcal");
                    
                    proteinLabel.setText("Protein: " + (int) finalProtein + "g");
                    carbLabel.setText("Carbs: " + (int) finalCarbs + "g");
                    fatLabel.setText("Fats: " + (int) finalFat + "g");

                    proteinBar.setProgress(finalProtein / 100);
                    carbBar.setProgress(finalCarbs / 300);
                    fatBar.setProgress(finalFat / 100);

                    Label mealEntry = new Label("🍽 " + food + " - " + finalCal + " kcal");
                    mealEntry.setFont(Font.font("Poppins", FontWeight.MEDIUM, 18));
                    mealEntry.setTextFill(Color.web("#37474F"));
                    mealEntry.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8; -fx-padding: 8 12 8 12;");
                    mealEntry.setMaxWidth(850);
                    mealEntry.setWrapText(true);

                    mealSection.getChildren().add(mealEntry);
                    countButton.setDisable(false);
                    countButton.setText("Count");
                });
            }).start();
        });

        HBox fullLayout = new HBox(40, mainBox, mealSection);
        VBox vb = new VBox(10, header, fullLayout);
        HBox full = new HBox(40, vb);
        full.setAlignment(Pos.TOP_CENTER);
        full.setPadding(new Insets(40, 30, 30, 30));

        ScrollPane scrollPane = new ScrollPane(full);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #FFFFF4;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        return new VBox(scrollPane);
    }

    private VBox createMacroRow(Label valueLabel, ProgressBar bar, String color) {
        valueLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 22));
        valueLabel.setTextFill(Color.web("#263238"));

        bar.setPrefWidth(180);
        bar.setPrefHeight(24);
        bar.setStyle("-fx-accent: " + color + "; -fx-background-radius: 6;");

        String emoji = "";
        String bgColor = "#FFFFFF";
        String labelText = valueLabel.getText().toLowerCase();
        if (labelText.contains("protein")) {
            emoji = "🍗";
            bgColor = "#c6fccaff";
        } else if (labelText.contains("carb")) {
            emoji = "🍞";
            bgColor = "#fddeadff";
        } else if (labelText.contains("fat")) {
            emoji = "🥑";
            bgColor = "#fcb6afff";
        }

        Label emojiLabel = new Label(emoji);
        emojiLabel.setFont(Font.font(30));
        emojiLabel.setAlignment(Pos.CENTER);

        VBox contentBox = new VBox(10, valueLabel, bar);
        contentBox.setAlignment(Pos.CENTER);

        VBox squareBox = new VBox(12, emojiLabel, contentBox);
        squareBox.setPadding(new Insets(15));
        squareBox.setAlignment(Pos.CENTER);
        squareBox.setStyle("-fx-background-color: " + bgColor + "; -fx-background-radius: 16; -fx-border-color: #CFD8DC; -fx-border-radius: 16;");
        squareBox.setPrefSize(180, 180);
        squareBox.setEffect(new DropShadow(5, Color.rgb(0, 0, 0, 0.1)));

        return squareBox;
    }
}