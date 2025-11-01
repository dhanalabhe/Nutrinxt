package com.voidmaingirls.View;

import com.voidmaingirls.Controller.UserProfileController;
import com.voidmaingirls.model.UserProfileModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class PersonalInfo {

    Scene infoScene,primarygoalScene;
    Stage infoStage;
    public void setInfoScene(Scene infoScene) {
        this.infoScene = infoScene;
    }

    public void setInfoStage(Stage infoStage) {
        this.infoStage = infoStage;
    }

    public VBox createScene(Runnable back) {

        Image bgImage = new Image(getClass().getResourceAsStream("/Assets/Images/personalInfoBackground.png"));
        BackgroundImage bg = new BackgroundImage(
            bgImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(1.0, 1.0, true, true, false, false)
        );

        BorderPane root = new BorderPane();
        root.setBackground(new Background(bg));

        // Main white box container with shadow
        HBox mainBox = new HBox(30);
        mainBox.setPadding(new Insets(30));
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setBackground(new Background(
            new BackgroundFill(Color.rgb(255, 255, 255, 0.65), new CornerRadii(20), Insets.EMPTY)
        ));
        mainBox.setEffect(new DropShadow(20, Color.GRAY));

        // LEFT: Vertical image box
        VBox imageColumn = new VBox(10);
        imageColumn.setAlignment(Pos.TOP_CENTER);
         imageColumn.setPadding(new Insets(10, 0, 10, 20));

        

        // RIGHT: Form box
        VBox formBox = new VBox(10);
        formBox.setAlignment(Pos.CENTER_LEFT);
        formBox.setPadding(new Insets(20));

        Label heading = new Label("Personal Information");
        heading.setFont(Font.font("Tahoma", 40));
        heading.setTextFill(Color.DARKGREEN);
        heading.setStyle("-fx-font-weight: bold;");
        heading.setEffect(new DropShadow(2, Color.DARKGREY));

        TextField nameField = new TextField();
        nameField.setPromptText("Enter Name");
        nameField.setFont(Font.font("Tahoma", 20));
        nameField.setStyle("-fx-font-weight: bold;");

        TextField addressField = new TextField();
        addressField.setPromptText("Enter Address");
        addressField.setFont(Font.font("Tahoma", 20));
        addressField.setStyle("-fx-font-weight: bold;");


        ComboBox<String> genderBox = new ComboBox<>();
        genderBox.getItems().addAll("Male", "Female", "Other");
        genderBox.setPromptText("Select Gender");
       // genderBox.setFont(Font.font("Tahoma", 24));
        genderBox.setStyle("-fx-font-weight: bold;");


        TextField ageField = new TextField();
        ageField.setPromptText("Enter Age");
         ageField.setFont(Font.font("Poppins", 20));
        ageField.setStyle(" -fx-font-weight: Normal;");


         TextField heightField = new TextField();
        heightField.setPromptText("Enter Height (cm)");
        heightField.setFont(Font.font("Poppins", 20));
        heightField.setStyle("-fx-font-weight:Normal;");

        TextField weightField = new TextField();
        weightField.setPromptText("Enter Weight (kg)");
        weightField.setFont(Font.font("Poppins", 20));
        weightField.setStyle(" -fx-font-weight: Normal;");


        Button submitBtn = new Button("Submit");
        submitBtn.setStyle("-fx-background-color: olivedrab; -fx-text-fill: white");
        submitBtn.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 20));

            

       // submitBtn.setMinWidth(150);
        
        Button editBtn = new Button("Edit");
        editBtn.setStyle("-fx-background-color: olivedrab; -fx-text-fill: white");
        editBtn.setFont(Font.font("Poppins", FontWeight.SEMI_BOLD, 20));
         //editBtn.setMinWidth(150);
         
        Button nextBtn = new Button("Next ➡");
        nextBtn.setFont(Font.font("Poppins", FontWeight.BOLD, 30));
        nextBtn.setStyle("-fx-background-color:olivedrab; -fx-text-fill: white; -fx-background-radius: 15;");
        nextBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                initializePersonalGoal();
                infoStage.setScene(primarygoalScene);
            }
            
        });
        

       // submitBtn.setStyle("-fx-background-color: #2e8b57; -fx-text-fill: white; -fx-font-weight: bold;");
        submitBtn.setMinWidth(100);
        editBtn.setMinWidth(100);

        Label statusLabel = new Label();
        statusLabel.setTextFill(Color.DARKBLUE);

        submitBtn.setOnAction(e -> statusLabel.setText("Submitted Successfully"));
	submitBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                UserProfileModel user=new UserProfileModel(nameField.getText(), 
                        heightField.getText(), 
                        ageField.getText(), 
                        genderBox.getValue(), 
                        addressField.getText(), 
                        weightField.getText()); 
                        //(String name, String height, String age, String gender, String address, String weight)
                        try{
                UserProfileController.storeProfile(user.getName(),user.getHeight(),
                        user.getAge(),user.getGender(),user.getAddress(),user.getWeight());
                        }catch(Exception ie){
                            ie.printStackTrace();
                        }
                initializePersonalGoal();
                infoStage.setScene(primarygoalScene);
            }
            
        });
        editBtn.setOnAction(e -> {
            nameField.clear();
            addressField.clear();
            ageField.clear();
            genderBox.getSelectionModel().clearSelection();
            statusLabel.setText("");
        });

       Label nameLbl = new Label("Name");
       nameLbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
        Label addressLbl = new Label("Address");
        addressLbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
        Label genderLbl = new Label("Gender");
        genderLbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
        Label ageLbl = new Label("Age");
        ageLbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
         Label heightLbl = new Label("Height");
        heightLbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
        Label weightLbl = new Label("Weight");
        weightLbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));

    formBox.getChildren().addAll(
    heading,
    nameLbl, nameField,
    addressLbl, addressField,
    genderLbl, genderBox,
    ageLbl, ageField,
    heightLbl,heightField,
    weightLbl,weightField,
    new HBox(10, submitBtn, editBtn),
    statusLabel
);


        mainBox.getChildren().addAll(imageColumn, formBox);
        root.setCenter(mainBox);
        return new VBox(root);
    }

    
    public void initializePersonalGoal(){
        primarygoals s1 = new primarygoals();
        s1.setPrimarygoalStage(infoStage);
        primarygoalScene= new Scene(s1.createScene(this:: handleBack),1300,700);
        s1.setPrimarygoalsScene(primarygoalScene);

    }
    
    public void handleBack(){
        infoStage.setScene(infoScene);
    }
}