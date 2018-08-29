package no.ntnu.imt3281.sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


public class Sudoku extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sudoku.fxml"));
        primaryStage.setTitle("Sudoku");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    public static int[][] readSudokuFromFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/no/ntnu/imt3281/sudoku/sudokus/sudoku1.json"));
        int[] nrs = new int [81];
        int i = 0;
        while(scanner.hasNextInt()){
            nrs[i++] = scanner.nextInt();
        }

        int[][] board = new int[9][9];
        int p = 0;
        for (int j = 0; j < 9; j++) {
            for (int k = 0; k < 9; k++) {
                board[j][k] = nrs[p++];
            }
        }

        return board;
    }

}
