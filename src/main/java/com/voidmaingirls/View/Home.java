package com.voidmaingirls.View;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.animation.*;
import javafx.util.Duration;

public class Home extends Application {
    private Scene homeScene;
    Scene SignUpScene, weekScene, recipeScene, caloricScene, DietPlanScene,drinkupScene,botScene;
    private Stage homeStage;
    public void setHomeScene(Scene homeScene) {
        this.homeScene = homeScene;
    }

    public void setHomeStage(Stage homeStage) {
        this.homeStage = homeStage;
    }

    @Override
    public void start(Stage myStage) {
        // Load background image
        

        ImageView userIcon = new ImageView(new Image("/Assets/Images/signup.png"));
        userIcon.setFitHeight(110);
        userIcon.setFitWidth(110);

        VBox icon = new VBox(0,userIcon);
        icon.setLayoutX(1150);
        icon.setLayoutY(1);

        icon.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event arg0) {
                initializeSignUp();
                homeStage.setScene(SignUpScene);

            }

        });


        Text home = new Text("HOME");
        home.setFill(Color.BEIGE);
        home.setFont(new Font("Roboto", 24));
        Text weekBite = new Text("WEEKBITE");
        weekBite.setFill(Color.BEIGE);
        weekBite.setFont(new Font("Roboto", 24));
        weekBite.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                initializeWeekBites();
                homeStage.setScene(weekScene);
            }
        });
        Text recipeHub = new Text("RECIPEHUB");
        recipeHub.setFill(Color.BEIGE);
        recipeHub.setFont(new Font("Roboto", 24));
        recipeHub.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                initializeRecipehub();
                homeStage.setScene(recipeScene);
            }
        });
        Text calorieVault = new Text("CALORIEVAULT");
        calorieVault.setFill(Color.BEIGE);
        calorieVault.setFont(new Font("Roboto", 24));
        calorieVault.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                initializeCalori();
                homeStage.setScene(caloricScene);
            }
        });
        Text drinkUp = new Text("DRINKUP");
        drinkUp.setFill(Color.BEIGE);
        drinkUp.setFont(new Font("Roboto", 24));
        drinkUp.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                initializeDrinkup();
                homeStage.setScene(drinkupScene);
            }
        });

        HBox header = new HBox(20,home,weekBite,recipeHub,calorieVault,drinkUp,icon);
        header.setStyle("-fx-background-color: rgba(102, 142, 20, 1);");
        header.setMinWidth(1300);
        header.setMinHeight(90);
        header.setAlignment(Pos.CENTER_RIGHT);
        header.setPadding(new Insets(0,160,0,0));

        Image nutri = new Image("/Assets/Images/Nutri.png");

        ImageView homeText = new ImageView(nutri);
        homeText.setLayoutX(85); 
        homeText.setLayoutY(250);
        homeText.setFitHeight(180);
        homeText.setFitWidth(550);

        Image nxt = new Image("/Assets/Images/nxt.png");

        ImageView homeText1 = new ImageView(nxt);
        homeText1.setLayoutX(150);
        homeText1.setLayoutY(440);
        homeText1.setFitHeight(120);
        homeText1.setFitWidth(400);

        // === Animation for Nutri image ===
homeText.setTranslateX(-600); // Start off-screen left
TranslateTransition slideInNutri = new TranslateTransition(Duration.seconds(2), homeText);
slideInNutri.setFromX(-600);
slideInNutri.setToX(0);
slideInNutri.setInterpolator(Interpolator.EASE_OUT);

TranslateTransition bounceUpNutri = new TranslateTransition(Duration.millis(200), homeText);
bounceUpNutri.setByY(-15);

TranslateTransition bounceDownNutri = new TranslateTransition(Duration.millis(200), homeText);
bounceDownNutri.setByY(15);

TranslateTransition settleNutri = new TranslateTransition(Duration.millis(150), homeText);
settleNutri.setByY(-5);

SequentialTransition nutriSequence = new SequentialTransition(
    slideInNutri, bounceUpNutri, bounceDownNutri, settleNutri
);

// === Animation for Nxt image ===
homeText1.setTranslateX(-600); // Start off-screen left
TranslateTransition slideInNxt = new TranslateTransition(Duration.seconds(2), homeText1);
slideInNxt.setFromX(-600);
slideInNxt.setToX(0);
slideInNxt.setInterpolator(Interpolator.EASE_OUT);

TranslateTransition bounceUpNxt = new TranslateTransition(Duration.millis(200), homeText1);
bounceUpNxt.setByY(-15);

TranslateTransition bounceDownNxt = new TranslateTransition(Duration.millis(200), homeText1);
bounceDownNxt.setByY(15);

TranslateTransition settleNxt = new TranslateTransition(Duration.millis(150), homeText1);
settleNxt.setByY(-5);

SequentialTransition nxtSequence = new SequentialTransition(
    slideInNxt, bounceUpNxt, bounceDownNxt, settleNxt
);
nxtSequence.setDelay(Duration.seconds(0.5)); // Nxt starts 0.5 sec after Nutri

// Play both animations
nutriSequence.play();
nxtSequence.play();



        Text homeText2 = new Text("YOUR DIET, YOUR WAY !!!");
        homeText2.setFont(new Font("Segoe Script", 40));
        homeText2.setLayoutX(140);
        homeText2.setLayoutY(630);
        homeText2.setFill(Color.OLIVEDRAB);

        VBox menuContainer = new VBox(10); // fixed spacing between sections
menuContainer.setAlignment(Pos.CENTER);
//menuContainer.setStyle("-fx-background-color: #111111;"); // dark background like reference
menuContainer.setPadding(new Insets(10, 40, 10, 150));

// Add all four sections
menuContainer.getChildren().addAll(
    createMenuSection("                 Weekbite",
        "Get a personalized weekly diet plan by entering your age,\nweight, goals, and medical conditions. Our AI analyzes your\nprofile to create meals that match your health needs.\nWhether you aim to lose weight, build muscle, or eat better,\nthis feature makes healthy eating simple and effective.",
        "/Assets/Images/menu111.png",
        () -> {
            initializeWeekBites();
            homeStage.setScene(weekScene);
        }, true), // text first

    createMenuSection("             Recipe Hub",
        "Recipe Hub is a smart cooking assistant that suggests\nrecipes based on the ingredients you have. Simply put\nwhat’s in your kitchen, and it instantly provides delicious\nmeal ideas. It’s perfect for healthy meals, saving time,\nand discovering new dishes tailored to what you already own.",
        "/Assets/Images/rec44.png",
        () -> {
            initializeRecipehub();
            homeStage.setScene(recipeScene);
        }, false), // image first

    createMenuSection("               Calorie Vault",
        "Calorie Vault is a smart nutrition tracker that\nlets users log meals and monitor calories, proteins,\nfats, and carbs. With personalized goals and detailed\nnutrient breakdowns, it supports weight loss, muscle\ngain, or balanced eating. Simple, secure, and\ninsightful — your daily guide to better health and\ninformed food choices.",
        "/Assets/Images/cal33.png",
        () -> {
            initializeCalori();
            homeStage.setScene(caloricScene);
        }, true), // text first

    createMenuSection("           Drinkup",
        "The DrinkUp offers a curated collection of smoothie\nrecipes with ingredients, preparation steps and\nnutritional information. A healthy boost, a post-workout\ndrink, or a refreshing treat, DrinkUp makes it easy to\ndiscover, prepare, and enjoy delicious smoothies\ntailored to your taste and health goals.",
        "/Assets/Images/drinkup22.png",
        () -> {
            initializeDrinkup();
            homeStage.setScene(drinkupScene);
        }, false) // image first
);
        // --- Title and Subtitle ---
Label aboutTitle = new Label("About Us");
aboutTitle.setFont(Font.font("Poppins", FontWeight.BOLD, 46));
aboutTitle.setTextFill(Color.BEIGE);

Label subheading = new Label("Your smart health companion");
subheading.setFont(Font.font("Poppins", FontWeight.MEDIUM, 22));
subheading.setTextFill(Color.BEIGE);

// --- Description Text ---
Label description = new Label(
    "NutriNxt helps you track your nutrition, \n" +
    "build healthier habits, and achieve your goals.\n\n" +
    "From meal planning to progress tracking, we're with you\n" +
    "at every step of your health journey.\n\n" 
);

description.setFont(Font.font("Poppins", 18));
description.setTextFill(Color.BEIGE);
description.setWrapText(true);

// --- Left Column ---
VBox textColumn = new VBox(20, aboutTitle, subheading, description);
textColumn.setAlignment(Pos.TOP_LEFT);
textColumn.setPadding(new Insets(20));
textColumn.setPrefWidth(600);
textColumn.setMaxWidth(600);

HBox mainContent = new HBox();
mainContent.setAlignment(Pos.CENTER);
mainContent.setSpacing(30); 
mainContent.getChildren().addAll(textColumn);
mainContent.setMaxWidth(1200);

VBox aboutSection = new VBox(30, mainContent); // Was 50
aboutSection.setPadding(new Insets(60, 40, 60, 40));     // Was 80
aboutSection.setStyle("-fx-background-color: rgba(102, 142, 20, 1)");
aboutSection.setAlignment(Pos.CENTER);
aboutSection.setPrefWidth(1300);
aboutSection.setLayoutY(2700); // adjust as needed

        Pane root = new Pane();

        Region backgroundBox = new Region();
        backgroundBox.setPrefSize(1300, 2500);
        backgroundBox.setStyle(
            "-fx-background-color: linear-gradient(to bottom right, #e8f5e9, #ffffff);" +
            "-fx-background-radius: 0;" +
            "-fx-border-radius: 0;"
        );


        // Add green circular shape
        Circle greenCircle = new Circle(400, Color.web("#cde5b3"));
        greenCircle.setLayoutX(1000); // Adjust to position the circle properly
        greenCircle.setLayoutY(150);

        // Large "Let's Eat" text
        Text letsEat = new Text("Let's Eat");
        letsEat.setFont(Font.font("Georgia", FontWeight.BOLD, 90));
        letsEat.setFill(Color.rgb(255, 255, 200, 0.9));
        letsEat.setLayoutX(800); // Adjust position
        letsEat.setLayoutY(220);

        // Bowl image - placeholder (leave commented)
        ImageView bowlImage = new ImageView(new Image("/Assets/Images/bowl2.png"));
        bowlImage.setFitWidth(450);
        bowlImage.setPreserveRatio(true);
        bowlImage.setLayoutX(780); // adjust positioning
        bowlImage.setLayoutY(230);
        
        StackPane mainRoot = new StackPane();

        // Scrollable content
        ScrollPane scroll = new ScrollPane(root);
        // Rotation effect on scroll
        scroll.vvalueProperty().addListener((obs, oldVal, newVal) -> {
    double scrollPos = newVal.doubleValue();

    // Rotate bowl image
    bowlImage.setRotate(scrollPos * 360); // full rotation over scroll

    // Rotate each menu section image
    menuContainer.getChildren().forEach(section -> {
        if (section instanceof VBox) {
            VBox vbox = (VBox) section;
            vbox.getChildren().forEach(node -> {
                if (node instanceof HBox) {
                    HBox hbox = (HBox) node;
                    hbox.getChildren().forEach(child -> {
                        if (child instanceof StackPane) {
                            StackPane stack = (StackPane) child;
                            stack.getChildren().forEach(imgNode -> {
                                if (imgNode instanceof ImageView) {
                                    ((ImageView) imgNode).setRotate(scrollPos * 360);
                                }
                            });
                        }
                    });
                }
            });
        }
    });

    

});

        scroll.setStyle(
            "-fx-background: transparent;" +
            "-fx-background-color: transparent;" +
            "-fx-padding: 0;"
        );
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(false);

        mainRoot.getChildren().addAll(scroll);

        backgroundBox.toBack();
        backgroundBox.setStyle(
            "-fx-background-color: #e5f0dcff;" +
            "-fx-background-radius: 30;" +
            "-fx-border-radius: 30;" 
        );
    menuContainer.setLayoutY(700); // adjust based on spacing below header

   Button chatBotBtn = new Button("💬NutriBot");
chatBotBtn.setStyle(
    "-fx-font-size: 28px;" +
    "-fx-background-color: rgba(102, 142, 20, 0.9);" +
    "-fx-background-radius: 25%;" +
    "-fx-text-fill: white;" +
    "-fx-cursor: hand;"
);
chatBotBtn.setMaxWidth(400);
chatBotBtn.setOnAction(e -> {
    initializeChatbot();
    homeStage.setScene(botScene);
});

Pane floatingButtonPane = new Pane(chatBotBtn);
floatingButtonPane.setPickOnBounds(false); // Let other clicks pass through
chatBotBtn.setLayoutX(1090); // Position from left
chatBotBtn.setLayoutY(620);  // Position from top



    root.getChildren().addAll(backgroundBox, greenCircle, letsEat, bowlImage, header, icon, homeText, homeText1, homeText2, floatingButtonPane
, menuContainer, aboutSection);

    Scene sc = new Scene(mainRoot, 1300, 700);
        myStage.setScene(sc);
        homeScene=sc;
        homeStage = myStage;
        myStage.setTitle("Home");
        myStage.show();
        
}

    private void initializeSignUp(){
        SignUp s1 = new SignUp();
        s1.setSignUpStage(homeStage);
        SignUpScene= new Scene(s1.createScene(this:: handleBack),1300,700);
        s1.setSignUpScene(SignUpScene);
    }

    private void initializeChatbot() {
    NutriBotController botController = new NutriBotController();

    // Create a new small popup Stage for chatbot
    Stage botStage = new Stage();
    botStage.initStyle(StageStyle.UNDECORATED);  // No close/maximize/minimize
    botStage.setResizable(false);                // Fixed size

    Scene botScene = new Scene(botController.createChatView(() -> botStage.close()), 340, 680);
    botController.setBotScene(botScene);
    botController.setBotStage(botStage);

    botStage.setScene(botScene);
    botStage.setX(1060);        // Open at extreme left
    botStage.setY(60);      // Adjust vertically if needed
    botStage.show();
}


    public void handleBack() {
    homeStage.setScene(homeScene);  // return to home scene
    }

    private void initializeWeekBites(){
        WeekBites s1 = new WeekBites();
        s1.setWeekStage(homeStage);
        weekScene= new Scene(s1.createScene(this:: handleBack),1300,700);
        s1.setWeekScene(weekScene);
    }

    private void initializeCalori(){
        Calorie s1 = new Calorie();
        s1.setCalorieStage(homeStage);
        caloricScene= new Scene(s1.createScene(this:: handleBack),1300,700);
        s1.setCalorieScene(caloricScene);
    }

    private void initializeRecipehub(){
        RecipeHub s1 = new RecipeHub();
        s1.setRecipeStage(homeStage);
        recipeScene= new Scene(s1.createScene(this:: handleBack),1300,700);
        s1.setRecipeScene(recipeScene);
    }
    private void initializeDrinkup(){
        Drinkup s1 = new Drinkup();
        s1.setDrinkupStage(homeStage);
        drinkupScene= new Scene(s1.createScene(this:: handleBack),1300,700);
        s1.setDrinkupScene(drinkupScene);
    }

private VBox createMenuSection(String title, String description, String imagePath, Runnable onClickAction, boolean isImageFirst) {
    // === Image Setup ===
    ImageView imageView = new ImageView(new Image(imagePath));
    imageView.setFitWidth(430);
    imageView.setFitHeight(430);
    imageView.setPreserveRatio(true);
    imageView.setSmooth(true);
    imageView.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.15)));

    StackPane imageContainer = new StackPane(imageView);
    imageContainer.setAlignment(Pos.CENTER);
    imageContainer.setPadding(new Insets(10));

    // === Title ===
    Label titleText = new Label(title);
    titleText.setFont(Font.font("Poppins", FontWeight.EXTRA_BOLD, 45));
    titleText.setTextFill(Color.BLACK); // black text
    titleText.setStyle("-fx-text-fill: black;"); // explicit CSS (optional fallback)

    // === Description ===
    Label descText = new Label(description);
    descText.setFont(Font.font("Poppins",FontWeight.SEMI_BOLD, 22));
    descText.setTextFill(Color.BLACK); // black text
    descText.setWrapText(true);
    descText.setMaxWidth(720);
    descText.setStyle("-fx-text-fill: black;"); // optional fallback

    VBox textContainer = new VBox(10, titleText, descText);
    textContainer.setAlignment(Pos.CENTER_LEFT);
    textContainer.setPadding(new Insets(10));

    // === Combine Text and Image ===
    HBox contentBox = new HBox(30);
    contentBox.setAlignment(Pos.CENTER);
    contentBox.setPadding(new Insets(20));

    if (isImageFirst) {
    imageContainer.setId("image");
    textContainer.setId("text");
    contentBox.getChildren().addAll(imageContainer, textContainer);
} else {
    textContainer.setId("text");
    imageContainer.setId("image");
    contentBox.getChildren().addAll(textContainer, imageContainer);
}


    // === Card Wrapper ===
    VBox card = new VBox(contentBox);
    card.setAlignment(Pos.CENTER);
    card.setPadding(new Insets(10));

    return card;
}


}


