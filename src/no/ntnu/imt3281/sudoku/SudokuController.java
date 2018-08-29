package no.ntnu.imt3281.sudoku;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;


public class SudokuController {

    private int[][] board = new int[9][9];
    private int select_row, select_col;
    private boolean squareSelected = false;

    @FXML
    private GridPane grid;

    @FXML
    private Button knapp1;

    @FXML
    private Canvas canvas;

    @FXML
    void selectSquare(MouseEvent event) {
        int x = (int)Math.floor(event.getSceneX())/50;
        int y = (int)Math.floor(event.getSceneY())/50;
        if(x < 10 && y < 10){
            select_row = x;
            select_col = y;
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
            default: nr = 0; break;
        }

        if(nr!=0){
            board[select_row][select_col] = nr;
            drawBoard();
        }

        drawBoard();
    }

    @FXML
    void test(ActionEvent event) {

    }

    @FXML
    public void drawBoard(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());

        //Mark selected square
        gc.setFill(Color.GREENYELLOW);
        gc.fillRect(select_row*50,select_col*50, 50,50);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(board[i][j] != 0){
                    gc.setFill(Color.BLACK);
                    gc.setFont(new Font(20d));
                    gc.fillText(""+board[i][j], i*50d +20, j*50d+30);

                }
            }
        }
    }
}
