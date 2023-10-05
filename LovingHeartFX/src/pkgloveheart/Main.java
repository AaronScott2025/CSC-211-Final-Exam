package pkgloveheart;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Arrays;

public class Main extends Application {
    static Questions questions = new Questions();
    CheckBox[] checkBoxes;
    FXMLLoader loader;
    Answers answerManager;
    Button btr;
    Button bte;
    Alert results = new Alert(Alert.AlertType.INFORMATION);
    Alert exit = new Alert(Alert.AlertType.INFORMATION);

    public Object loadData() { // Method for loading the questions in Questions.java
        try {
            String[] loadedQuestions = questions.loadQuestions();
            System.out.println("Testing Questions:");
            System.out.println(Arrays.toString(loadedQuestions)); // Testing print method
            return loadedQuestions; // Returns the questions
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("No workie");
        return null;
     
    }

    public void processLoadedData(Object loadedData) { // Sets questions to checkbox text
        if (loadedData instanceof String[]) {
            String[] loadedQuestions = (String[]) loadedData;
            for (int i = 0; i < loadedQuestions.length; i++) {
                String checkboxId = "ch" + (i + 1);
                CheckBox checkBox = (CheckBox) loader.getNamespace().get(checkboxId);
                if (checkBox != null) {
                    checkBox.setText(loadedQuestions[i]);
                }
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception { // Starts, displays, and loads questions in scene
        loader = new FXMLLoader(getClass().getResource("LovingHeartscr.fxml"));
        Parent root = loader.load(); //Scene loads

        // Initialize checkboxes
        checkBoxes = new CheckBox[10];
        for (int i = 0; i < checkBoxes.length; i++) {
            String checkboxId = "ch" + (i + 1);
            checkBoxes[i] = (CheckBox) loader.getNamespace().get(checkboxId);
        }

        bte = (Button) loader.getNamespace().get("btne");
        btr = (Button) loader.getNamespace().get("btnr");

        bte.setOnAction(this::handleExit); //Exit button
        btr.setOnAction(this::handleResults); //Results Button

        // Calls load method, and method to process the loaded data
        Object loadedData = loadData();
        processLoadedData(loadedData);

        answerManager = new Answers(); //Accesses everything in Answers()

        for (CheckBox checkBox : checkBoxes) {
            checkBox.setOnAction(this::handleCheckboxAction);
        }

        Scene scene = new Scene(root);
        primaryStage.setTitle("LovingHeart");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleCheckboxAction(ActionEvent event) { // Keeps score
        CheckBox checkBox = (CheckBox) event.getSource();
        if (checkBox.isSelected()) {
            answerManager.AnswerINC();//Check increases
        }
        if (!checkBox.isSelected()) {
            answerManager.AnswerDEC(); //Uncheck decreases
        }
    }

    public void handleResults(ActionEvent event) { //Result calculator
        int Answer = answerManager.getAnswerCount(); //Score
        System.out.println("Score : " + Answer); //Testing purposes
        if (Answer > 5) { // In Love
            results.setTitle("Results:");
            results.setHeaderText("It's a Match!");
            results.setContentText("You are likely in love! Go get 'em, champ!");
            results.showAndWait(); // Waits for user to press button

            if (results.getResult() == ButtonType.OK) {
                System.out.println("Complete");
                Platform.exit(); // Close Program

            }
        } else { // Not in love
            results.setTitle("Results:");
            results.setHeaderText("Not a Match!");
            results.setContentText("You likely love this person platonically, that isnt a bad thing though!");
            results.showAndWait();// Waits for user to press button

            if (results.getResult() == ButtonType.OK) {
            	System.out.println("Complete");
                Platform.exit(); // Close Program

            }
        }
    }

    public void handleExit(ActionEvent event) { //Exit button
        exit.setTitle("Exit");
        exit.setHeaderText("Thank you for using LovingHeart!");
        exit.showAndWait();

        if (exit.getResult() == ButtonType.OK) {
        	System.out.println("Complete");
            Platform.exit(); // Close Program
        }
    }

    public static void main(String[] args) { //launch
        launch(args);
    }
}
