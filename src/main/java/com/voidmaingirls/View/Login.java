package com.voidmaingirls.View;

import java.util.ArrayList;
import java.util.List;

import com.voidmaingirls.Controller.loginauth;
import com.voidmaingirls.Controller.signupauth;
import com.voidmaingirls.model.Authmodel;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Login {
    
    Stage loginStage;
    Scene loginScene,signupScene,dashboardScene,farmerHomeScene,deliveryHomeScene;
    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }
    public void setLoginScene(Scene loginScene) {
        this.loginScene = loginScene;
    }
    private final List<String> imagePaths = new ArrayList<>();
     private int currentIndex = 0;
     private ImageView imageView;

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

        imagePaths.add("Assets/images/signupslideshow1.jpg");
        imagePaths.add("Assets/images/signupslideshow2.jpg");
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

        Text tx = new Text();

        Label welcome = new Label("Welcome !");
        welcome.setFont(Font.font("Cooper Black", FontWeight.BOLD, 32));
        welcome.setStyle("-fx-text-fill: #374914");
        

        

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

        ComboBox<String> usertype = new ComboBox<>();
        usertype.getItems().addAll("User", "Farmer", "Delivery");
        usertype.setPromptText("Choose Role");
        usertype.setMaxWidth(300);
        usertype.setMaxHeight(50);

        Label errorLabel = new Label();
        errorLabel.setFont(Font.font("Poppins", FontWeight.NORMAL, 14));
        errorLabel.setTextFill(javafx.scene.paint.Color.RED);
        errorLabel.setVisible(false); // Hide initially


        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color: #37474F; -fx-text-fill: white");
        loginBtn.setFont(Font.font("Poppins", FontWeight.BOLD, 24));
        loginBtn.setMaxWidth(300);
        loginBtn.setMinHeight(35);
        loginBtn.setOnMouseClicked(new EventHandler<Event>() {
    @Override
    public void handle(Event event) {
        errorLabel.setVisible(false); // reset visibility

        if (emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            errorLabel.setText("Please fill all fields.");
            errorLabel.setVisible(true);
            return;
        }

        String selectedType = usertype.getValue();
        if (selectedType == null) {
            errorLabel.setText("Please select a user type.");
            errorLabel.setVisible(true);
            return;
        }

        boolean loginSuccess = loginauth.loginStatus(emailField.getText(), passwordField.getText());
if (loginSuccess) {
    Authmodel.setEmail(emailField.getText());  // ✅ Store email globally
    System.out.println("Login successful");
    switch (selectedType) {
        case "User":
            intializeHOme();
            loginStage.setScene(dashboardScene);
            break;
        case "Farmer":
            intializefarmerHome();
            loginStage.setScene(farmerHomeScene);
            break;
        case "Delivery":
            intializedDelivaryHome();
            loginStage.setScene(deliveryHomeScene);
            break;
        default:
            System.out.println("Unknown user type.");
    }
} else {
    errorLabel.setText("Invalid email or password.");
    errorLabel.setVisible(true);
}

    }
});

        Text space = new Text();


        Text tx1 = new Text("Don't have an account ? ");
        tx1.setFont(Font.font("Poppins", FontWeight.NORMAL, 14));

        Text signup = new Text("signup");
        signup.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 16));
        signup.setOnMouseClicked(event -> {
            back.run();
        });

        HBox signupBox = new HBox(5, tx1, signup);
        signupBox.setAlignment(Pos.CENTER);

        Text userText = new Text("User Type");
        userText.setFont(Font.font("Poppins", 18));
        userText.setTextAlignment(javafx.scene.text.TextAlignment.LEFT);

        
        VBox rightPanel = new VBox(10, tx, welcome, email, emailField, password, passwordField, userText, usertype, errorLabel, space, loginBtn, signupBox);
        rightPanel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3); -fx-background-radius: 41;");
        rightPanel.setMinHeight(550);
        rightPanel.setMinWidth(500);
        rightPanel.setLayoutX(680);
        rightPanel.setLayoutY(100);
        rightPanel.setAlignment(Pos.CENTER);


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

        
        Pane root1 = new Pane();
        root1.getChildren().addAll(leftPanel,rightPanel,backButton);
        root1.setBackground(background);
        root1.setPrefSize(1300, 700);

        
        VBox vb = new VBox(root1);
        return vb;
    }

    public void intializeHOme(){
        DashBoard s1 = new DashBoard();
        s1.setDashboardStage(loginStage);
        dashboardScene= new Scene(s1.createScene(this:: handleBack),1300,700);
        s1.setDashboardScene(dashboardScene);
    }
    public void handleBack() {
        loginStage.setScene(loginScene);  // return to home scene
     }

     public void intializefarmerHome(){
        FarmerHome s1 = new FarmerHome();
        s1.setFarmerHomeStage(loginStage);
        farmerHomeScene= new Scene(s1.createScene(this:: handleBack),1300,700);
        s1.setFarmerHomeScene(farmerHomeScene);
    }

    public void intializedDelivaryHome(){
        delivaryhome s1 = new delivaryhome();
        s1.setDelivaryStage(loginStage);
        deliveryHomeScene= new Scene(s1.createScene(this:: handleBack),1300,700);
        s1.setDelivaryScene(deliveryHomeScene);
    }

    
}
