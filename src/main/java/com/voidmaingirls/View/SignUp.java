package com.voidmaingirls.View;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
//import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import com.voidmaingirls.Controller.signupauth;
import com.voidmaingirls.model.Authmodel;

public class SignUp {
     private final List<String> imagePaths = new ArrayList<>();
     private int currentIndex = 0;
     private ImageView imageView;
     Scene signUpScene,loginScene,personalInfoScene,farmerInfoScene,deliveryInfoScene;
     Stage signUpStage;

    public void setSignUpScene(Scene scene) {
        this.signUpScene = scene;
    }

    public void setSignUpStage(Stage stage) {
        this.signUpStage = stage;
    }

    public VBox createScene(Runnable back) {
        // Load background image
        Image homeBackImage = new Image("\\Assets\\Images\\loginSingup.png");
        BackgroundSize backgroundSize = new BackgroundSize(1300, 700, false, false, false, false);

        BackgroundImage backgroundImage = new BackgroundImage(
            homeBackImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            backgroundSize
        );
        Background background = new Background(backgroundImage);

        imagePaths.add("Assets/images/signupslideshow4.jpg");
        imagePaths.add("Assets/images/signupslideshow5.jpg");
        imagePaths.add("Assets/images/signupslideshow3.jpg");

        imageView = new ImageView(new Image(imagePaths.get(0)));
        imageView.setFitHeight(550);
        imageView.setFitWidth(550);
        imageView.setPreserveRatio(false);
        

        GridPane root = new GridPane();

        HBox hBox = new HBox(imageView);
        root.add(hBox, 0, 0);
        root.setStyle("-fx-background-radius: 45");

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), e -> {
            currentIndex = (currentIndex + 1) % imagePaths.size();
            imageView.setImage(new Image(imagePaths.get(currentIndex)));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        VBox leftPanel = new VBox(10,root);
        leftPanel.setLayoutX(100);
        leftPanel.setLayoutY(100);

        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);

        Label signUp = new Label("Sign Up");
        signUp.setFont(Font.font("Cooper Black", FontWeight.BOLD, 32));
        signUp.setStyle("-fx-text-fill: #7b9f33ff");

        Text user = new Text("Login As User");
        user.setFont(Font.font("Poppins", 18));
        user.setTextAlignment(javafx.scene.text.TextAlignment.LEFT);
        

        Text userName = new Text("Name");
        userName.setFont(Font.font("Poppins", 18));
        userName.setTextAlignment(javafx.scene.text.TextAlignment.LEFT);

        TextField userField = new TextField();
        userField.setPromptText("Enter  Name");
        userField.setMaxWidth(300);
        userField.setMinHeight(35);
        userField.setStyle("-fx-background-color : #D9D9D9; -fx-text-fill: black");
        

        Text email = new Text("Email");
        email.setFont(Font.font("Poppins", 18));
        email.setTextAlignment(javafx.scene.text.TextAlignment.LEFT);

        TextField emailField = new TextField();
        emailField.setPromptText("Enter Email ");
        emailField.setMaxWidth(300);
        emailField.setMinHeight(35);
        emailField.setStyle("-fx-background-color : #D9D9D9; -fx-text-fill: black");


        Text password = new Text("Password");
        password.setFont(Font.font("Poppins",  18));
        password.setTextAlignment(javafx.scene.text.TextAlignment.LEFT);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);
        passwordField.setMinHeight(35);
        passwordField.setStyle("-fx-background-color : #D9D9D9; -fx-text-fill: black");

        Text conPassword = new Text("Confirm Password");
        conPassword.setFont(Font.font("Poppins", 18));
        conPassword.setTextAlignment(javafx.scene.text.TextAlignment.LEFT);

        PasswordField conPasswordField = new PasswordField();
        conPasswordField.setPromptText(" Confirm Password");
        conPasswordField.setMaxWidth(300);
        conPasswordField.setMinHeight(35);
        conPasswordField.setStyle("-fx-background-color : #D9D9D9; -fx-text-fill: black");

        Text userText = new Text("User Type");
        userText.setFont(Font.font("Poppins", 18));
        userText.setTextAlignment(javafx.scene.text.TextAlignment.LEFT);

        ComboBox<String> usertype = new ComboBox<>();
        usertype.getItems().addAll("User", "Farmer", "Delivery");
        usertype.setPromptText("Choose User Type");
        usertype.setMaxWidth(300);
        usertype.setMaxHeight(50);

        Button signupBtn = new Button("Sign Up");
        signupBtn.setStyle("-fx-background-color: #37474F; -fx-text-fill: white");
        signupBtn.setFont(Font.font("Poppins", FontWeight.BOLD, 24));
        signupBtn.setMaxWidth(300);
        signupBtn.setMinHeight(35);
        signupBtn.setOnMouseClicked(new EventHandler<Event>() {

    @Override
    public void handle(Event arg0) {

        if (emailField.getText().isEmpty() || passwordField.getText().isEmpty() || userField.getText().isEmpty() || conPasswordField.getText().isEmpty()) {
            System.out.println("Please fill all fields.");
            errorLabel.setText("Please fill all fields.");
            return;
        }
        if (!passwordField.getText().equals(conPasswordField.getText())) {
            errorLabel.setText("Passwords do not match.");
            return;
        }

        String selectedType = usertype.getValue();
        System.out.println("User Type: " + selectedType);


        if (selectedType == null) {
            System.out.println("Please select a user type.");
            errorLabel.setText("Please select a user type.");
            return;
        }
        Authmodel authModel = new Authmodel(emailField.getText());
        

        switch (selectedType) {
            case "User":
                initializePersonaInfo();
                signUpStage.setScene(personalInfoScene);
                break;
            case "Farmer":
                initializefarmerPersonaInfo();
                signUpStage.setScene(farmerInfoScene);
                break;
            case "Delivery":
                // Initialize Delivery Info Scene here
                initializeDeliveryInfo(); 
                signUpStage.setScene(deliveryInfoScene);
                break;
            default:
                System.out.println("Unknown user type.");
        }

        signupauth.loginStatus(emailField.getText(), passwordField.getText());


    }

});
        Text tx1 = new Text("Already User ? ");
        tx1.setFont(Font.font("Poppins", FontWeight.NORMAL, 14));

        Text logIn = new Text("Login");
        logIn.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 16));
        logIn.setOnMouseClicked(event -> {
            initializeLoginPage();
            signUpStage.setScene(loginScene);
            
        });

        HBox loginBox = new HBox(5, tx1, logIn);
        loginBox.setAlignment(Pos.CENTER);

        

        VBox rightPanel = new VBox(10,signUp,userName,userField,email,emailField,password,passwordField,conPassword,conPasswordField,userText,usertype,errorLabel,signupBtn,loginBox);
        rightPanel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3); -fx-background-radius: 41;");
        rightPanel.setMinHeight(550);
        rightPanel.setMinWidth(500);
        rightPanel.setLayoutX(680);
        rightPanel.setLayoutY(100);
        rightPanel.setAlignment(Pos.TOP_CENTER);


        Button backButton = new Button(" ← ");
        backButton.setLayoutX(20);
        backButton.setLayoutY(20);
        backButton.setStyle("-fx-font-size: 20px; -fx-background-color: #303b02ff; -fx-text-fill: white;-fx-background-radius: 60;");
        backButton.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                back.run();
            }
        });

        // Root pane with background
        Pane root1 = new Pane();
        root1.getChildren().addAll(leftPanel,rightPanel,backButton);
        root1.setBackground(background);
        root1.setPrefSize(1300, 700);

        // Wrap in VBox (optional, but lets you add more vertical layout if needed)
        VBox vb = new VBox(root1);
        return vb;
    }

    public void initializeLoginPage(){
        Login s1 = new Login();
        s1.setLoginStage(signUpStage);
        loginScene= new Scene(s1.createScene(this:: handleBack),1300,700);
        s1.setLoginScene(loginScene);
    }
    public void handleBack(){
        signUpStage.setScene(signUpScene);
    }
    public void initializePersonaInfo(){
        PersonalInfo s1 = new PersonalInfo();
        s1.setInfoStage(signUpStage);
        personalInfoScene= new Scene(s1.createScene(this:: handleBack),1300,700);
        s1.setInfoScene(personalInfoScene);

    }

    public void initializefarmerPersonaInfo(){
        farmerPersonalinfo s1 = new farmerPersonalinfo();
        s1.setFarmerPersonalInfoStage(signUpStage);
        farmerInfoScene= new Scene(s1.createScene(this:: handleBack),1300,700);
        s1.setFarmerPersonalInfoScene(farmerInfoScene);

    }
    
    public void initializeDeliveryInfo() {
        DelivaryInfoPage s1 = new DelivaryInfoPage();
        s1.setDeliveryInfoStage(signUpStage);
        deliveryInfoScene = new Scene(s1.createScene(this::handleBack), 1300, 700);
        s1.setDeliveryInfoScene(deliveryInfoScene);
    }

    

}
