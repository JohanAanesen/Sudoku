package no.ntnu.imt3281.sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.util.concurrent.ThreadLocalRandom;

public class Sudoku extends Application {

    static int[][] sudNr = new int[9][9];
    static TextField[][] textFields = new TextField[9][9];

    public Sudoku(){
        //initializes 2D array for holding all textfields programatically
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                textFields[i][j] = new TextField();
            }
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sudoku.fxml"));
        primaryStage.setTitle("Sudoku");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        new Sudoku();
        launch(args);
    }

    public static void setupBoard(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudNr[i][j] = ThreadLocalRandom.current().nextInt(0, 9 + 1);
            }


        }

    }
}
