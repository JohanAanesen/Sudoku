package no.ntnu.imt3281.sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class Sudoku extends Application {

    protected int[][] board = new int[9][9];
    protected int[][] originalBoard = new int[9][9];

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

    protected int getNumber(int y, int x){
        return board[y][x];
    }

    protected int getOriginalNumber(int y, int x){
        return originalBoard[y][x];
    }

    protected void setNumber(int y, int x, int value){
        board[y][x] = value;
    }


    protected void readSudokuFromFile() throws IOException {
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

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                originalBoard[i][j] = 0; //resets board
                originalBoard[i][j] = board[i][j]; //copies the new board
            }
        }

        this.board = board;
    }

    protected void resetOriginalBoard(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                originalBoard[i][j] = 0; //resets board
                originalBoard[i][j] = board[i][j]; //copies the new board
            }
        }
    }

    protected boolean isLegal(int select_col, int select_row, int nr){
        boolean legal = true;

        if(originalBoard[select_col][select_row] != 0){ //you can't edit a original number
            return false;
        }

        if(nr < 1){return false;}

        for (int i = 0; i < 9; i++) {
            //check if number is not occurring in the same row
            if(board[select_col][i] == nr){ legal = false;}
            //check if number is not occurring in the same column
            if(board[i][select_row] == nr){ legal = false; }
        }

        //check if number is not occurring in the same block
        int blockX = (select_col/3)*3; //split by and time by 3 to find start of block
        int blockY = (select_row/3)*3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(board[blockX+i][blockY+j] == nr){ legal = false; }
            }
        }

        return legal;
    }


    protected void mirror() {
        for (int i = 0; i < 9/2; i++) {
            for (int j = 0; j < 9; j++) {
                //int temp = this.board[i][j];
                //this.board[i][j] = this.board[8-i][j];
                //this.board[8-i][j] = temp;

                int temp = getNumber(i, j);
                setNumber(i, j, getNumber(8-i, j));
                setNumber(8-i, j, temp);
            }
        }
    }
    //DET ER DA FAEN IKKE SAMME KODE FUCK OFF
    protected void flip() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9/2; j++) {
                //int temp = this.board[i][j];
                //this.board[i][j] = this.board[i][8-j];
                //this.board[i][8-j] = temp;

                int temp = getNumber(i, j);
                setNumber(i, j, getNumber(i, 8-j));
                setNumber(i, 8-j, temp);
            }
        }
    }

    protected void flipBlueLine() {
        for (int i = 0; i < 9; i++) {
            for (int j = i; j < 9; j++) {
                //int temp = this.board[i][j];
                //this.board[i][j] = this.board[j][i];
                //this.board[j][i] = temp;

                int temp = getNumber(i, j);
                setNumber(i, j, getNumber(j, i));
                setNumber(j, i, temp);
            }
        }
    }

    protected void flipRedLine() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9 - i; j++) {
                //int temp = this.board[i][j];
                //this.board[i][j] = this.board[8-j][8-i];
                //this.board[8-j][8-i] = temp;

                int temp = getNumber(i, j);
                setNumber(i, j, getNumber(8 - j, 8 - i));
                setNumber(8 - j, 8 - i, temp);
            }
        }
    }
}
