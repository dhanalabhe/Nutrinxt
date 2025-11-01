package com.voidmaingirls.View;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.fxmisc.richtext.InlineCssTextArea;
import com.voidmaingirls.Controller.FormatController;
import com.voidmaingirls.Controller.GeminiAPI;
import com.voidmaingirls.Controller.WeekBiteController;
import com.voidmaingirls.model.Authmodel;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class WeekBites {

    Scene weekScene,breakfastScene,subscrptionScene;
    Stage weekStage;

    public void setWeekScene(Scene weekScene) {
        this.weekScene = weekScene;
    }

    public void setWeekStage(Stage weekStage) {
        this.weekStage = weekStage;
    }

    public VBox createScene(Runnable back) {
        //left pannel
        Text tx1 = new Text("Create your weekly plan");
        tx1.setFont(new Font("Poppins", 28));
        tx1.setFill(Color.BLACK);
        tx1.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        tx1.setStyle("-fx-font-weight:Bold");

        Text tx2 = new Text("Select a preferable program");
        tx2.setFont(new Font("Poppins", 13));
        tx2.setFill(Color.BLACK);
        tx1.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        //tx2.setLayoutX(300);

        Button Protein = new Button("Proteins");
        Protein.setStyle("-fx-background-radius: 20; -fx-background-color: white; ");
        Protein.setMinHeight(50);
        Protein.setMinWidth(90);

        Button Keto = new Button("Keto");
        Keto.setStyle("-fx-background-radius: 20; -fx-background-color: white; ");
        Keto.setMinHeight(50);
        Keto.setMinWidth(90);

        Button nocarb = new Button("Low Carb");
        nocarb.setStyle("-fx-background-radius: 20; -fx-background-color: white; ");
        nocarb.setMinHeight(50);
        nocarb.setMinWidth(90);

        Button Vegan = new Button("Vegan");
        Vegan.setStyle("-fx-background-radius: 20; -fx-background-color: white; ");
        Vegan.setMinHeight(50);
        Vegan.setMinWidth(90);

        Button nocarb2 = new Button("No Sugar");
        nocarb2.setStyle("-fx-background-radius: 20; -fx-background-color: white; ");
        nocarb2.setMinHeight(50);
        nocarb2.setMinWidth(90);

        Button Balanced = new Button("Mediterranean");
        Balanced.setStyle("-fx-background-radius: 20; -fx-background-color: white; ");
        Balanced.setMinHeight(50);
        Balanced.setMinWidth(90);

        
        
        List<String> selectedDiets = new ArrayList<>();

        Protein = createDietToggleButton("Protein", "🥩", selectedDiets);
        Keto = createDietToggleButton("Keto", "🥑", selectedDiets);
        Vegan = createDietToggleButton("Vegan", "🥦", selectedDiets);
        Balanced = createDietToggleButton("Balanced", "🥗", selectedDiets);
        nocarb = createDietToggleButton("Low Carb", "🥬", selectedDiets);
        nocarb2 = createDietToggleButton("High Carb", "🍚", selectedDiets);
        Button Mediterranean = createDietToggleButton("Mediterranean", "🧄", selectedDiets);
        Button Paleo = createDietToggleButton("Paleo", "🍖", selectedDiets);


        HBox diet1 = new HBox(15, Protein, Keto, Vegan, Mediterranean);
        HBox diet2 = new HBox(15, Balanced, nocarb, nocarb2, Paleo);
        VBox diet = new VBox(15, diet1, diet2);
        diet.setAlignment(Pos.CENTER_LEFT);



        VBox dietHeader = new VBox(10, tx1, tx2,diet);
        dietHeader.setStyle("-fx-background-color: #ffffffff; -fx-background-radius: 41;\"-fx-border-color: #101E5B;-fx-background-radius: 10;-fx-border-radius: 8;");
        // dietHeader.setMinHeight(157);
        // dietHeader.setMaxWidth(700);
        dietHeader.setPadding(new Insets(10));
        dietHeader.setAlignment(Pos.CENTER);
        
        Text title = new Text("              Week Bite");
        title.setFont(Font.font("Poppins", FontWeight.BOLD, 64));
        title.setFill(Color.web("#101E5B"));
        

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        

        HBox cardContainer = new HBox(30); // 30px gap between cards
        cardContainer.setAlignment(Pos.CENTER);
        //cardContainer.setPadding(new Insets(20, 20, 20, 20)); // top/bottom only
        InlineCssTextArea geminiResponseArea = new InlineCssTextArea();
        geminiResponseArea.setWrapText(true);
        geminiResponseArea.setPrefHeight(300);
        geminiResponseArea.setPrefWidth(1200);
        geminiResponseArea.setEditable(false);
        geminiResponseArea.setStyle(
            "-fx-background-color: #ffead4ff;" +
            "-fx-control-inner-background: #fcfcfcff;"+
            "-fx-text-fill: #333;" +
            "-fx-border-color: #ffead4ff;" +
            "-fx-border-radius: 10;" +
            "-fx-padding: 30;" +
            "-fx-font-size: 20px;" +
            "-fx-font-family: 'Poppins';"

        );
        geminiResponseArea.setPadding(new Insets(30, 30, 30, 20)); 

        

        VBox diet4 =  new  VBox(20,dietHeader);
        diet4.setPadding(new Insets(10));

        HBox hb1 = new HBox(10,title);

        ProgressIndicator loadingIndicator = new ProgressIndicator();
        loadingIndicator.setMaxSize(80, 80);
        loadingIndicator.setVisible(false); // hide initially

        StackPane geminiStack = new StackPane(geminiResponseArea, loadingIndicator);
        geminiStack.setPrefHeight(350);
        geminiStack.setPrefWidth(1200);
        geminiStack.setStyle(
            "-fx-background-color: #ffffff;" +
            "-fx-border-color: #dddddd;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;"
        );

        VBox vb = new VBox(10, hb1, diet4, geminiStack); // more spacing
        vb.setPadding(new Insets(30)); // more consistent padding
        

        //left pannel
        Text startPlan2 = new Text("Start Today");
        startPlan2.setFont(new Font("Poppins", 60));
        startPlan2.setStyle("-fx-text-fill:Grey");

        Text startPlan1 = new Text("Start Date");
        startPlan1.setFont(new Font("Poppins", 24));
        startPlan1.setStyle("-fx-text-fill:Grey");

        Text startPlan = new Text("EndDate");
        startPlan.setFont(new Font("Poppins", 24));
        startPlan.setStyle("-fx-text-fill:Blue");

        HBox date = new HBox(70,startPlan1,startPlan);

        DatePicker startDate = new DatePicker();
        startDate.setPromptText("dd//mm/yyyy");
        startDate.setFocusTraversable(false);
        startDate.setStyle(
            "-fx-background-color: #243880ff; " +
            "-fx-text-fill: white; " +
            "-fx-font-weight: bold;"
        );
        DatePicker endDate = new DatePicker();
        endDate.setPromptText("dd//mm/yyyy");
        
        endDate.setFocusTraversable(false);
        endDate.setStyle(
            "-fx-background-color: #243880ff; " +
            "-fx-text-fill: white; " +
            "-fx-font-weight: bold;"
        );

        HBox dates = new HBox(0, startDate, endDate);

        VBox combine = new VBox(10,date,dates);

        Text selectPlan = new Text("Select Meal Plan");
        selectPlan.setFont(new Font("Poppins", 24));
        selectPlan.setStyle("-fx-text-fill:Blue");

        Button breakfast = new Button("Breakfast");
        breakfast.setMinWidth(110);
        breakfast.setMinHeight(40);
        breakfast.setTextFill(Color.BLACK);
        breakfast.setStyle(
        "-fx-background-color: #f8fdfcff;" +
            "-fx-text-fill: black;" +
            "-fx-border-color: black;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 20px;" +
            "-fx-font-family: 'Poppins';"
        );

        Button lunch = new Button("Lunch");
        lunch.setMinWidth(110);
        lunch.setMinHeight(40);
        lunch.setTextFill(Color.BLACK);
        lunch.setStyle(
            "-fx-background-color: #fcfcfcff;" +        // Button background
            "-fx-text-fill: black;" +                 // Text color
            "-fx-border-color: black;" +              // Border color                 // Border thickness
            "-fx-border-radius: 8;" +                 // Rounded border
            "-fx-background-radius: 8;" +             // Rounded background
            "-fx-font-weight: bold;" +                // Bold text
            "-fx-font-size: 20px;" +                  // Font size
            "-fx-font-family: 'Poppins';" 
        );

        Button dinner = new Button("Dinner");
        dinner.setMinWidth(110);
        dinner.setMinHeight(40);
        dinner.setTextFill(Color.BLACK);
        dinner.setStyle(
        "-fx-background-color: #fcfffeff;" +        // Button background
            "-fx-text-fill: black;" +                 // Text color
            "-fx-border-color: black;" +              // Border color                 // Border thickness
            "-fx-border-radius: 8;" +                 // Rounded border
            "-fx-background-radius: 8;" +             // Rounded background
            "-fx-font-weight: bold;" +                // Bold text
            "-fx-font-size: 20px;" +                  // Font size
            "-fx-font-family: 'Poppins';" 
        );

        
        
        List<String> selectedMealTypes = new ArrayList<>();

         breakfast = createToggleButton("Breakfast", selectedMealTypes);
         lunch = createToggleButton("Lunch", selectedMealTypes);
         dinner = createToggleButton("Dinner", selectedMealTypes);

        HBox plans = new HBox(0, breakfast, lunch, dinner);



       Button generate = new Button("Generate Plan");
        generate.setMinWidth(400);
        generate.setMinHeight(50);
        generate.setTextFill(Color.WHITE); // matches -fx-text-fill in CSS

        generate.setStyle(
    "-fx-background-color: #243880;" +  // deep navy blue
    "-fx-text-fill: white;" +
    "-fx-font-size: 18px;" +
    "-fx-font-weight: bold;" +
    "-fx-background-radius: 12;" +
    "-fx-padding: 10 20;" +
    "-fx-font-family: 'Poppins';"
);

        generate.setOnAction(e -> {
    if (Authmodel.getEmail() == null || Authmodel.getEmail().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Login Required");
        alert.setHeaderText(null);
        alert.setContentText("Please log in or sign up to generate a plan.");
        alert.showAndWait();
        return;
    }

    LocalDate start = startDate.getValue();
    LocalDate end = endDate.getValue();

    if (selectedDiets.isEmpty() || selectedMealTypes.isEmpty() || start == null || end == null) {
        System.out.println("❌ Please fill in all fields before generating a plan.");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Incomplete Fields");
        alert.setHeaderText(null);
        alert.setContentText("Please select at least one diet, one meal type, and choose start and end dates.");
        alert.showAndWait();
        return;
    }

    try {
    // Generate prompt for Gemini
    String prompt = "Generate a personalized weekly meal plan for the following:\n" +
        "Diet Types: " + String.join(", ", selectedDiets) + "\n" +
        "Meal Types: " + String.join(", ", selectedMealTypes) + "\n" +
        "Start Date: " + start.toString() + "\n" +
        "End Date: " + end.toString();

        // Fetch Gemini result in background thread
        geminiResponseArea.clear();          // clear old text
        loadingIndicator.setVisible(true);
        new Thread(() -> {
        String raw = GeminiAPI.getGeminiResponse(prompt); // fetch raw response
        String cleaned = cleanResponse(raw); // clean the response
        

        javafx.application.Platform.runLater(() -> {
        loadingIndicator.setVisible(false); // hide spinner after done
        FormatController formatter = new FormatController();
        formatter.formatAndDisplayResponse(geminiResponseArea, cleaned);

            // Now store everything into Firestore
            try {
                WeekBiteController.storeProfile(
                    selectedDiets,
                    start.toString(),
                    end.toString(),
                    selectedMealTypes,
                    cleaned 
                );
                System.out.println("✅ Plan generated and stored in Firebase!");
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("❌ Failed to store plan.");
            }
        });
    }).start();

} catch (Exception ex) {
    ex.printStackTrace();
    System.out.println("❌ Failed to generate Gemini response.");
}

});
generate.setOnMouseEntered(e -> generate.setStyle(
    "-fx-background-color: #1e2f70;" +
    "-fx-text-fill: white;" +
    "-fx-font-size: 18px;" +
    "-fx-font-weight: bold;" +
    "-fx-background-radius: 12;" +
    "-fx-padding: 10 20;" +
    "-fx-font-family: 'Poppins';"
));
generate.setOnMouseExited(e -> generate.setStyle(
    "-fx-background-color: #243880;" +
    "-fx-text-fill: white;" +
    "-fx-font-size: 18px;" +
    "-fx-font-weight: bold;" +
    "-fx-background-radius: 12;" +
    "-fx-padding: 10 20;" +
    "-fx-font-family: 'Poppins';"
));

        Button buyPlan = new Button(" Subscribe to Plan");
        buyPlan.setMinWidth(400);
        buyPlan.setMinHeight(60);
        buyPlan.setTextFill(Color.WHITE); // matches -fx-text-fill in CSS

        buyPlan.setStyle(
    "-fx-background-color: #356690;" +  // soft blue
    "-fx-text-fill: white;" +
    "-fx-font-size: 18px;" +
    "-fx-font-weight: bold;" +
    "-fx-background-radius: 12;" +
    "-fx-padding: 10 20;" +
    "-fx-font-family: 'Poppins';"
);

        buyPlan.setOnMouseClicked(e->{
            intializeSubscrptionPlan();
            weekStage.setScene(subscrptionScene);

        });


        VBox vb4 = new VBox(30, startPlan2,combine, selectPlan, plans, generate, buyPlan);

        vb4.setPadding(new Insets(20));
        vb4.setAlignment(Pos.CENTER_LEFT);

        HBox finalhBox = new HBox(10,vb,vb4);

        Button backButton = new Button("← Back");
        backButton.setStyle(
            "-fx-font-size: 16px;" +
            "-fx-background-color: olivedrab;" +
            "-fx-text-fill: white;" +
            "-fx-padding: 8 16;" +
            "-fx-background-radius: 8;" +
            "-fx-font-family: 'Poppins';"
        );
        VBox.setMargin(backButton, new Insets(15, 0, 10, 20)); // top, right, bottom, left

        backButton.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                back.run();
            }
        });

        VBox finalVBox =  new VBox(backButton,finalhBox);
        finalVBox.setStyle("-fx-background-color: white;");
        finalVBox.setPrefHeight(700);
        ScrollPane sc = new ScrollPane(finalVBox);
        sc.setFitToWidth(true); 
        VBox vbfinal = new VBox(sc);
        vbfinal.setPrefHeight(1200);
        


        return vbfinal;
    }

    public void handleBack() {
    weekStage.setScene(weekScene);  // return to home scene
    }

    private Button createToggleButton(String label, List<String> selectionList) {
    Button button = new Button(label);
    button.setStyle(getUnselectedStyle());
    button.setMinWidth(120);
    button.setMinHeight(40);
    button.setOnAction(e -> {
        if (selectionList.contains(label)) {
            selectionList.remove(label);
            button.setStyle(getUnselectedStyle());
        } else {
            selectionList.add(label);
            button.setStyle(getSelectedStyle());
        }
    });
    return button;
}

        private String cleanResponse(String raw) {
            raw = raw.replaceAll("\\*", "-");
            raw = raw.replaceAll("-{2,}", "");
            raw = raw.replaceAll("\\n\\s*\\n", "\n\n");
            return raw.trim();
        }

        public void intializeSubscrptionPlan(){
        Sabscription s1 = new Sabscription();
        s1.setSubscribeStage(weekStage);
        subscrptionScene= new Scene(s1.createScene(this:: handleBack),1300,700);
        s1.setSubscribeScene(subscrptionScene);
    }
    private Button createDietToggleButton(String label, String emoji, List<String> selectionList) {
    Button button = new Button(emoji + " " + label);
    button.setStyle(getUnselectedStyle());
    button.setMinWidth(170);
    button.setMinHeight(50);
    button.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 16));

    button.setOnAction(e -> {
        if (selectionList.contains(label)) {
            selectionList.remove(label);
            button.setStyle(getUnselectedStyle());
        } else {
            selectionList.add(label);
            button.setStyle(getSelectedStyle());
        }
    });

    return button;
}
private String getSelectedStyle() {
    return "-fx-background-color: #102643; " +
           "-fx-text-fill: white; " +
           "-fx-border-color: #102643; " +
           "-fx-border-radius: 10; " +
           "-fx-background-radius: 10; " +
           "-fx-font-size: 16px; " +
           "-fx-font-family: 'Poppins';";
}

private String getUnselectedStyle() {
    return "-fx-background-color: #ffffff; " +
           "-fx-text-fill: #000000; " +
           "-fx-border-color: #cccccc; " +
           "-fx-border-radius: 10; " +
           "-fx-background-radius: 10; " +
           "-fx-font-size: 16px; " +
           "-fx-font-family: 'Poppins';";
}


    

} 