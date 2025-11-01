package com.voidmaingirls.View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

import org.fxmisc.richtext.InlineCssTextArea;

import com.voidmaingirls.Controller.FormatController;
import com.voidmaingirls.Controller.GeminiAPI;
import com.voidmaingirls.Controller.RecipeHubController;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
public class RecipeHub  {
   
    
    private TextField searchField;
    private final List<String> cartItems = new ArrayList<>();
    private final List<String> titles = new ArrayList<>();
    private final HBox highlightHBox = new HBox(15);

    private HBox currentlySelectedBox = null;
    private final List<HBox> categoryBoxes = new ArrayList<>();

    Scene recipeScene;
    Stage recipeStage;
    public void setRecipeScene(Scene recipeScene) {
        this.recipeScene = recipeScene;
    }

    public void setRecipeStage(Stage recipeStage) {
        this.recipeStage = recipeStage;
    }

    public VBox createScene(Runnable back) {

        Button backButton = new Button("← Back");
        backButton.setStyle(
            "-fx-font-size: 16px;" +
            "-fx-background-color: olivedrab;" +
            "-fx-text-fill: white;" +
            "-fx-padding: 8 16;" +
            "-fx-background-radius: 8;" +
            "-fx-font-family: 'Poppins';"
        );
        backButton.setOnAction(e -> back.run()); // Your existing handler

        // Wrap back button in an HBox for top-left alignment
        HBox topBar = new HBox(backButton);
        topBar.setPadding(new Insets(15, 0, 0, 15));
        topBar.setAlignment(Pos.TOP_LEFT);

        
        ProgressIndicator loadingIndicator = new ProgressIndicator();
        loadingIndicator.setMaxSize(60, 60);
        loadingIndicator.setVisible(false); // initially hidden

        TextArea responseArea = new TextArea();
        responseArea.setEditable(false);
        responseArea.setWrapText(true);
        responseArea.setStyle("-fx-font-size: 14px;");

        StackPane stackPane = new StackPane(responseArea, loadingIndicator);
        stackPane.setPrefHeight(200); // adjust as needed

      
        Text title = new Text("RecipeHub     ");
        title.setFont(Font.font("Tahoma", 92));
        title.setTextAlignment(javafx.scene.text.TextAlignment.RIGHT);
        //title.setTranslateX(300);

        Text infoText = new Text("Nourish To Flourish");
        infoText.setFont(Font.font("Verdana", 30));
        infoText.setStyle("-fx-text-fill: #333333");
        infoText.setRotate(90);
        infoText.setTranslateY(150);
        infoText.setTranslateX(135);

        // === Top Navigation Buttons ===
        Button exploreBtn = new Button("Explore");
        Button eatMeBtn = new Button("Eat Me");
        Button highlightBtn = new Button("Today's Highlight");

        String navBtnStyle = """
            -fx-background-color: transparent;
            -fx-text-fill: #4CAF50;
            -fx-font-size: 16;
            -fx-font-weight: bold;
            -fx-border-color: transparent;
            -fx-cursor: hand;
        """;
        exploreBtn.setStyle(navBtnStyle);
        eatMeBtn.setStyle(navBtnStyle);
        highlightBtn.setStyle(navBtnStyle);



        HBox navBar = new HBox(20, exploreBtn, eatMeBtn, highlightBtn);
        navBar.setAlignment(Pos.CENTER);
        navBar.setPadding(new Insets(10, 0, 10, 0));
        navBar.setStyle("""
            -fx-background-color: #ffffff;
            -fx-border-color: #dddddd;
            -fx-border-width: 0 0 1 0;
        """);

        Image dishImage = new Image("Assets/Images/p2.jpg");
        ImageView dishImageView = new ImageView(dishImage);
        dishImageView.setFitWidth(900);
        dishImageView.minHeight(100);
        dishImageView.setPreserveRatio(false);
        dishImageView.setStyle("-fx-background-radius: 41;");

        // For displaying Gemini's response in the UI
        InlineCssTextArea geminiResponseArea = new InlineCssTextArea();
        geminiResponseArea.setWrapText(true);
        geminiResponseArea.setMinHeight(300);
        geminiResponseArea.setMinWidth(900);
        geminiResponseArea.setEditable(false);
        geminiResponseArea.setStyle("-fx-font-size: 14px; -fx-background-color: #f9f9f9; -fx-padding: 10;");

                // Wrap geminiResponseArea and loader together
        StackPane geminiStack = new StackPane();
        geminiStack.getChildren().addAll(geminiResponseArea, loadingIndicator);
        StackPane.setAlignment(loadingIndicator, Pos.CENTER);
        loadingIndicator.setVisible(false); // hidden initially

        VBox geminiBox = new VBox(10, new Label("Gemini Recipe Suggestion:"), geminiStack);

        geminiBox.setPadding(new Insets(10));
        geminiBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #dddddd; -fx-border-width: 1; -fx-border-radius: 10;");


        Label ingredientLabel = new Label("Enter Ingredients:");
        ingredientLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        searchField = new TextField();
        searchField.setPromptText("e.g. Tomato, Onion Garlic,Chili");
        searchField.setPrefWidth(500);
        searchField.setPrefHeight(40);
        searchField.setLayoutX(300);
        searchField.setLayoutY(300);
        searchField.setStyle("""
            -fx-background-radius: 20;
            -fx-padding: 10;
            -fx-font-size: 14;
            -fx-text-fill: black
        """);

        Text explore = new Text("Explore");
        explore.setFont(new Font("Tahoma", 30));

        Button submitButton = new Button("Submit Ingredients");
        submitButton.setStyle("""
            -fx-background-color: #4CAF50;
            -fx-text-fill: white;
            -fx-background-radius: 20;
            -fx-padding: 8 16;
        """);

    submitButton.setOnAction(e -> {
    String input = searchField.getText();
    if (input == null || input.trim().isEmpty()) {
        System.out.println("⚠️ No input provided.");
        return;
    }

    String[] parts = input.split("[,;\\s]+");
    List<String> ingredientsList = new ArrayList<>();
    for (String part : parts) {
        String trimmed = part.trim();
        if (!trimmed.isEmpty()) {
            ingredientsList.add(trimmed);
        }
    }

    // Store in Firebase
    RecipeHubController.storeProfile(ingredientsList);

    // Prepare Gemini prompt
    String prompt = """
Given the following ingredients: """ + String.join(", ", ingredientsList) + """
Suggest a healthy recipe using these ingredients.

Then, create a separate section titled 'Additional Ingredients:' (on a new line).

In that section, list ONLY VEGETABLES that are NOT already in the input list and would improve the recipe.

Format the list clearly with each vegetable on a new line, starting with a dash (-). Do NOT include any explanations, just the list.

Example format:
Additional Ingredients:
- Broccoli
- Bell Pepper
- Spinach
""";
    loadingIndicator.setVisible(true);
    //geminiResponseArea.setText("");
    // Call Gemini in a background thread (JavaFX UI must not freeze)
    new Thread(() -> {
        String geminiResponse = GeminiAPI.getGeminiResponse(prompt);
        String cleaned = cleanResponse(geminiResponse); // clean the response
        System.out.println("Gemini Response: \n" + geminiResponse);
        javafx.application.Platform.runLater(() -> {
    FormatController formatter = new FormatController();
    formatter.displayRawResponse(geminiResponseArea, cleaned);
    loadingIndicator.setVisible(false);
            
    List<String> additionalIngredients = extractAdditionalIngredients(cleaned);

    if (additionalIngredients != null && !additionalIngredients.isEmpty()) {
        // Optional: clear old highlight cards
        highlightHBox.getChildren().clear();
        titles.clear();

        for (String ingredient : additionalIngredients) {
            String formatted = capitalize(ingredient);
            titles.add(formatted);
            VBox newCard = createHighlightCard(formatted);
            highlightHBox.getChildren().add(newCard);
        }
    } else {
        System.out.println("⚠️ No additional ingredients found.");
    }
});


    }).start();
});


HBox searchBox = new HBox(10, searchField, submitButton);
searchBox.setPadding(new Insets(10));
searchBox.setStyle("-fx-background-color: rgba(76, 88, 107, 0.62);");
searchBox.setMaxWidth(900);
searchBox.setAlignment(Pos.CENTER);

        Pane overlayPane = new Pane();
        overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3);");
        overlayPane.setMaxHeight(900);
        overlayPane.setMaxWidth(900);// same size as image
        overlayPane.setMouseTransparent(true); // So the image still responds to input if needed
       
        // Use StackPane to overlay searchBox and film over image
        StackPane dishImageStack = new StackPane(dishImageView, overlayPane,searchBox);
        dishImageStack.setAlignment(Pos.CENTER);        
        highlightHBox.setPadding(new Insets(10));
        highlightHBox.setAlignment(Pos.CENTER_LEFT);
        highlightHBox.setMinHeight(200);
        ScrollPane highlightScroll = new ScrollPane(highlightHBox);
        highlightScroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        highlightScroll.setVbarPolicy(ScrollBarPolicy.NEVER);
        highlightScroll.setFitToHeight(true);
        highlightScroll.setPannable(true);
        highlightScroll.setStyle("-fx-background-color: transparent;");
        highlightScroll.setPrefHeight(240);
        highlightScroll.setPrefWidth(900);

        Label highlightLabel = new Label("Additional Ingredients");
        highlightLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        highlightLabel.setTextFill(Color.web("#333333"));

        VBox highlightVBox = new VBox(10, highlightLabel, highlightScroll);
        highlightVBox.setAlignment(Pos.TOP_LEFT);
        highlightVBox.setPadding(new Insets(10));
        highlightVBox.setStyle("""
            -fx-background-color: #d9f6ffff;
            -fx-background-radius: 12;
            -fx-border-color: #dddddd;
            -fx-border-width: 1;
            -fx-border-radius: 12;
        """);
        highlightVBox.setMinHeight(300);

    
        VBox card = new VBox(10,topBar , navBar, title, dishImageStack,  geminiBox,highlightVBox);
        card.setPadding(new Insets(10));
        card.setAlignment(Pos.TOP_RIGHT);
        card.setPrefWidth(1350);
        card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0.3, 0, 4);");
       
        
        ScrollPane scrollPane = new ScrollPane(card);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-padding: 10;");
        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

        exploreBtn.setOnAction(e -> scrollToNode(dishImageStack, scrollPane).run());
        highlightBtn.setOnAction(e -> scrollToNode(highlightVBox, scrollPane).run());


        HBox root = new HBox( scrollPane);
        root.setPadding(new Insets(40));
        root.setSpacing(10);
        root.setAlignment(Pos.TOP_CENTER);

        VBox scene = new VBox(1,root);

        // Add click listener to unfocus when clicking outside category boxes
        scene.setOnMousePressed(event -> {
    // If the click target isn't inside any category box, remove focus
        boolean clickedOnCategory = categoryBoxes.stream()
        .anyMatch(box -> box.isHover());

    if (!clickedOnCategory && currentlySelectedBox != null) {
        currentlySelectedBox.setScaleX(1.0);
        currentlySelectedBox.setScaleY(1.0);
        currentlySelectedBox.setStyle("""
            -fx-background-color: #ffffff;
            -fx-background-radius: 10;
            -fx-border-color: #dddddd;
            -fx-border-width: 1;
            -fx-border-radius: 10;
        """);
        currentlySelectedBox = null;
         }
    });

    return scene;

    }
    private List<String> extractAdditionalIngredients(String response) {
    List<String> ingredients = new ArrayList<>();
    boolean foundSection = false;

    for (String line : response.split("\n")) {
        line = line.trim();

        if (line.equalsIgnoreCase("Additional Ingredients:")) {
            foundSection = true;
            continue;
        }

        if (foundSection) {
            if (line.isEmpty()) break;
            if (line.startsWith("-")) {
                String cleaned = line.substring(1).trim();
                if (cleaned.matches("^[A-Za-z\\s]+$")) {  // Accept letters and spaces only
                    ingredients.add(cleaned);
                }
            } else {
                break; // Stop if formatting breaks
            }
        }
    }

    return ingredients;
}


   private void openCartWindow() {
    Stage cartStage = new Stage();
    cartStage.setTitle("🛒 Shopping Cart");

    // ================== LEFT PANE (Cart Items) ==================
    Label cartTitle = new Label("Your Shopping Cart");
    cartTitle.setFont(Font.font("Arial", FontWeight.BOLD, 22));
    cartTitle.setTextFill(Color.web("#333"));

    VBox cartList = new VBox(15);
    cartList.setPadding(new Insets(10));

    double[] subtotal = {0.0};

    for (String item : cartItems) {
        // Dummy values — replace with actual item info
        String itemName = item;
        String itemColor = "Blue";
        int quantity = 1;
        double price = 70.0;
        subtotal[0] += price * quantity;

        Label nameLabel = new Label(itemName);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label colorLabel = new Label(itemColor);
        colorLabel.setTextFill(Color.GRAY);
        VBox details = new VBox(nameLabel, colorLabel);

        Label priceLabel = new Label(String.format(" Rs. %.2f ", price));
        priceLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        Button removeBtn = new Button("✖");
        removeBtn.setOnAction(e -> {
            cartItems.remove(item);
            cartStage.close();
            openCartWindow(); // Reload
        });

        HBox row = new HBox(10, details, priceLabel, removeBtn);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-padding: 10; -fx-background-color: #f2f2f2; -fx-background-radius: 8;");
        cartList.getChildren().add(row);
    }

    Label subtotalLabel = new Label(String.format("Subtotal:Rs. %.2f ", subtotal[0]));
    subtotalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

    Button backBtn = new Button("← Back to Shop");
    backBtn.setOnAction(e -> cartStage.close());

    VBox leftPane = new VBox(20, cartTitle, cartList, subtotalLabel, backBtn);
    leftPane.setPadding(new Insets(30));
    leftPane.setPrefWidth(400);
    leftPane.setStyle("-fx-background-color: #f5f7fa;");

    // ================== RIGHT PANE (Payment Form) ==================
    Label paymentTitle = new Label("Card Details");
    paymentTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    paymentTitle.setTextFill(Color.web("#FFD700")); // Yellow

    ToggleGroup cardGroup = new ToggleGroup();
    RadioButton visa = new RadioButton("Visa");
    visa.setTextFill(Color.WHITE);
    RadioButton master = new RadioButton("MasterCard");
    RadioButton verve = new RadioButton("Verve");
    master.setTextFill(Color.WHITE);
    verve.setTextFill(Color.WHITE);
    visa.setToggleGroup(cardGroup);
    master.setToggleGroup(cardGroup);
    verve.setToggleGroup(cardGroup);
    visa.setSelected(true);

    HBox cardOptions = new HBox(20, visa, master, verve);
    cardOptions.setAlignment(Pos.CENTER_LEFT);

    TextField cardNumber = new TextField();
    cardNumber.setPromptText("Card Number");

    TextField expiryDate = new TextField();
    expiryDate.setPromptText("MM/YY");

    TextField cvv = new TextField();
    cvv.setPromptText("CVV");

    HBox expiryCvv = new HBox(10, expiryDate, cvv);
    expiryCvv.setAlignment(Pos.CENTER_LEFT);

    Button checkoutBtn = new Button("Checkout");
    checkoutBtn.setStyle("""
        -fx-background-color: #FFD700;
        -fx-text-fill: black;
        -fx-font-weight: bold;
        -fx-font-size: 14px;
        -fx-padding: 10 20;
        -fx-background-radius: 10;
    """);

    checkoutBtn.setOnAction(e -> {
        RecipeHubController.storeRequiredItems(cartItems);
        cartItems.clear();
        cartStage.close();
        showConfirmation("✅ Payment successful! ");
    });

    VBox rightPane = new VBox(20, paymentTitle, cardOptions, cardNumber, expiryCvv, checkoutBtn);
    rightPane.setPadding(new Insets(30));
    rightPane.setPrefWidth(400);
    rightPane.setStyle("-fx-background-color: linear-gradient(to bottom, #2c2c2c, #1e1e1e);");

    for (Node node : rightPane.getChildren()) {
        if (node instanceof TextField tf) {
            tf.setStyle("-fx-background-radius: 6; -fx-padding: 8;");
            tf.setPrefWidth(250);
        }
    }

    // ================== Combine Layout ==================
    HBox root = new HBox(leftPane, rightPane);
    Scene scene = new Scene(root, 800, 500);
    cartStage.setScene(scene);
    cartStage.show();
}



    private VBox createHighlightCard(String title) {
    Label titleLabel = new Label(title);
    titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    titleLabel.setTextFill(Color.web("#333333"));

    ImageView imageView = new ImageView(new Image("\\Assets\\Images\\vegitablebasket.png"));
    imageView.setFitWidth(140);
    imageView.setFitHeight(100);
    imageView.setPreserveRatio(true);
    imageView.setStyle("-fx-background-color: #cccccc; -fx-background-radius: 10;");

    Button addToCartBtn = new Button("Add to Cart");
    addToCartBtn.setPrefWidth(140);
    addToCartBtn.setStyle("""
        -fx-background-color: #4CAF50;
        -fx-text-fill: white;
        -fx-font-size: 12;
        -fx-background-radius: 8;
        -fx-padding: 6 12;
    """);
    addToCartBtn.setOnAction(e -> {
        cartItems.add(title);
        openCartWindow(); // call method to show cart
    });


    VBox box = new VBox(10, titleLabel, imageView, addToCartBtn);
    box.setPadding(new Insets(10));
    box.setPrefWidth(160);
    box.setPrefHeight(200);
    box.setAlignment(Pos.TOP_CENTER);
    box.setStyle("""
        -fx-background-color: #ffffff;
        -fx-background-radius: 10;
        -fx-border-color: #dddddd;
        -fx-border-width: 1;
        -fx-border-radius: 10;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 4, 0.3, 0, 2);
    """);

    return box;
}
    private Runnable scrollToNode(Node node, ScrollPane scrollPane) {
    return () -> {
        double contentHeight = scrollPane.getContent().getBoundsInLocal().getHeight();
        double scrollPaneHeight = scrollPane.getViewportBounds().getHeight();

        // Get node position relative to the scrollPane content
        double nodeY = node.localToScene(0, 0).getY() - scrollPane.getContent().localToScene(0, 0).getY();

        // Calculate scroll value (ensure it's in [0, 1])
        double scrollValue = (nodeY) / (contentHeight - scrollPaneHeight);
        scrollValue = Math.max(0, Math.min(1, scrollValue)); // Clamp to [0,1]

        scrollPane.setVvalue(scrollValue);
    };
}

private String capitalize(String word) {
    if (word == null || word.isEmpty()) return word;
    return word.substring(0, 1).toUpperCase() + word.substring(1);
}

private void showConfirmation(String confirmationMessage) {
    Stage dialog = new Stage();
    dialog.setTitle("✅ Payment Confirmation");

    // ✅ Confirmation icon (could use an emoji or a styled Label)
    Label checkIcon = new Label("✔");
    checkIcon.setStyle("-fx-font-size: 60px; -fx-text-fill: #4CAF50;");

    // ✅ Main heading
    Label heading = new Label("Your Order is Confirmed");
    heading.setFont(Font.font("Arial", FontWeight.BOLD, 26));
    heading.setTextFill(Color.web("#333333"));

    // ✅ Subtext
    Label thankYou = new Label("Thank you for your purchase!");
    thankYou.setFont(Font.font("Arial", 16));
    thankYou.setTextFill(Color.web("#666666"));

    // ✅ Confirmation message from the caller
    Label message = new Label(confirmationMessage);
    message.setFont(Font.font("Arial", 14));
    message.setWrapText(true);
    message.setTextFill(Color.web("#444444"));
    message.setAlignment(Pos.CENTER);

    // ✅ Done Button
    Button okBtn = new Button("DONE");
    okBtn.setOnAction(e -> dialog.close());
    okBtn.setStyle("""
        -fx-background-color: #4CAF50;
        -fx-text-fill: white;
        -fx-font-size: 14px;
        -fx-font-weight: bold;
        -fx-padding: 10 20;
        -fx-background-radius: 10;
        -fx-cursor: hand;
    """);

    VBox content = new VBox(20, checkIcon, heading, thankYou, message, okBtn);
    content.setAlignment(Pos.CENTER);
    content.setPadding(new Insets(40));
    content.setStyle("""
        -fx-background-color: white;
        -fx-border-radius: 15;
        -fx-background-radius: 15;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0.3, 0, 4);
    """);

    StackPane layout = new StackPane(content);
    layout.setStyle("-fx-background-color: #f4f6fa;");

    Scene scene = new Scene(layout, 500, 400);
    dialog.setScene(scene);
    dialog.show();
}



private String cleanResponse(String raw) {
            raw = raw.replaceAll("\\*", "-");
            raw = raw.replaceAll("-{2,}", "");
            raw = raw.replaceAll("\\n\\s*\\n", "\n\n");
            return raw.trim();
        }


}