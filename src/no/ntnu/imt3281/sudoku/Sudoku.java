package no.ntnu.imt3281.sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.Arrays;
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


    public static int[][] readSudokuFromFile() throws IOException {
        File file = new File("src/no/ntnu/imt3281/sudoku/sudokus/sudoku1.json");
        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytes);
        fis.close();

        String[] valueStr = new String(bytes).trim().split("[^0-9]+");

        int[][] board = new int[9][9];

        int count = 1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = Integer.parseInt(valueStr[count++]);
            }
        }

        return board;
    }

}
