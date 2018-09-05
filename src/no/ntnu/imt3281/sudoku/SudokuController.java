package no.ntnu.imt3281.sudoku;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;

import java.io.IOException;

import static no.ntnu.imt3281.sudoku.Sudoku.readSudokuFromFile;


public class SudokuController {

    private int[][] board = new int[9][9];
    private int[][] originalBoard = new int[9][9];
    private boolean legalNumber = true;
    private int select_row, select_col;

    @FXML
    private GridPane grid;

    @FXML
    private Button knapp1;

    @FXML
    private Button knapp2;

    @FXML
    private Text message;

    @FXML
    private Canvas canvas;

    @FXML
    void selectSquare(MouseEvent event) {
        int x = (int)Math.floor(event.getSceneX())/50;
        int y = (int)Math.floor(event.getSceneY())/50;
        if(x < 10 && y < 10){
            select_col = x;
            select_row = y;
        }

        drawBoard();
    }

    @FXML
    void newInput(KeyEvent event) {
        int nr = 0;
        String nrString = ""+event.getCode();
        switch(nrString){
            case "DIGIT1": nr = 1; break;
            case "DIGIT2": nr = 2; break;
            case "DIGIT3": nr = 3; break;
            case "DIGIT4": nr = 4; break;
            case "DIGIT5": nr = 5; break;
            case "DIGIT6": nr = 6; break;
            case "DIGIT7": nr = 7; break;
            case "DIGIT8": nr = 8; break;
            case "DIGIT9": nr = 9; break;
            case "DIGIT0": nr = -1; break;
            case "UP": if(select_col>0){select_col--;} break;
            case "DOWN": if(select_col<8){select_col++;} break;
            case "LEFT": if(select_row>0){select_row--;} break;
            case "RIGHT": if(select_row<8){select_row++;} break;
            default: nr = 0; break;
        }

        if(isLegal(nr)){
            if(originalBoard[select_col][select_row] == 0) {
                board[select_col][select_row] = nr;
            }
        }else if(nr == -1){
            if(originalBoard[select_col][select_row] == 0) {
                board[select_col][select_row] = 0;
            }
        }

        drawBoard();
    }

    @FXML
    public void drawBoard(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());

        System.out.println("Rad: "+select_row + "\tCol: "+select_col);

        //Mark selected square
        if(legalNumber) {
            gc.setFill(Color.GREENYELLOW);
        }else{
            gc.setFill(Color.RED);
            legalNumber = true;
        }
        gc.fillRect(select_col*50+1,select_row*50+1, 48,48);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(board[i][j] != 0){
                    if(originalBoard[i][j] != 0) { //original numbers gets a grey background
                        gc.setFill(Color.LIGHTGRAY);
                        gc.fillRect(i * 50 + 1, j * 50 + 1, 48, 48);
                    }
                    gc.setFill(Color.BLACK);
                    gc.setFont(new Font(20d));
                    gc.fillText(""+board[i][j], i*50d +20, j*50d+32); //drawing number to canvas
                }
            }
        }
    }

    private boolean isLegal(int nr){
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

        legalNumber = legal;
        return legal;
    }

    @FXML
    void createSudoku(ActionEvent event) throws IOException {
        board = readSudokuFromFile(); //reads new board from file
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                originalBoard[i][j] = 0; //resets board
                originalBoard[i][j] = board[i][j]; //copies the new board
            }
        }
        drawBoard();
    }

    @FXML
    void flip(ActionEvent event) {
        for (int i = 0; i < 9; i++) {
            for (int j = i; j < 9; j++) {
                int temp = this.board[i][j];
                this.board[i][j] = this.board[j][i];
                this.board[j][i] = temp;
            }
        }


        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                originalBoard[i][j] = 0; //resets board
                originalBoard[i][j] = board[i][j]; //copies the new board
            }
        }

        drawBoard();
    }
}
