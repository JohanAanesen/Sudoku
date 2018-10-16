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

/**
 * Class SudokuController
 * Desc: Link between UI and Sudoku
 */
public class SudokuController {

    private Sudoku sudoku = new Sudoku();

    private boolean legalNumber = true;
    private int selectRow;
    private int selectCol;

    @FXML
    private GridPane grid;

    @FXML
    private Button knapp1;

    @FXML
    private Button knapp2;

    @FXML
    private Button knapp3;

    @FXML
    private Button knapp4;

    @FXML
    private Button knapp5;

    @FXML
    private Canvas canvas;

    @FXML
    private Text text1;

    @FXML
    void selectSquare(MouseEvent event) {
        int x = (int)Math.floor(event.getSceneX())/50;
        int y = (int)Math.floor(event.getSceneY())/50;
        if(x < 10 && y < 10){
            selectCol = x;
            selectRow = y;
        }

        drawBoard();
    }

    @FXML
    void newInput(KeyEvent event) {
        int nr = 0;
        String nrString = "" + event.getCode();
        switch (nrString) {
            case "DIGIT1": nr = 1;break;
            case "DIGIT2": nr = 2;break;
            case "DIGIT3": nr = 3;break;
            case "DIGIT4": nr = 4;break;
            case "DIGIT5": nr = 5;break;
            case "DIGIT6": nr = 6;break;
            case "DIGIT7": nr = 7;break;
            case "DIGIT8": nr = 8;break;
            case "DIGIT9": nr = 9;break;
            case "DIGIT0": nr = -1;break;
            case "UP": if (selectRow > 0) { selectRow--; }break;
            case "DOWN": if (selectRow < 8) { selectRow++; }break;
            case "LEFT": if (selectCol > 0) { selectCol--; }break;
            case "RIGHT": if (selectCol < 8) { selectCol++; }break;
            default: nr = 0;break;
        }

        prosessInput(nr);

        drawBoard(); //update board
    }

    public void prosessInput(int nr){
        if (sudoku.isLegal(selectCol, selectRow, nr)) {
            if (sudoku.getOriginalNumber(selectCol, selectRow) == 0) {
                sudoku.setNumber(selectCol, selectRow, nr);
            }
        } else if (nr == -1) {
            if (sudoku.getOriginalNumber(selectCol, selectRow) == 0) {
                sudoku.setNumber(selectCol, selectRow, 0);
            }
        }else if(sudoku.getNumber(selectCol, selectRow) != nr){
            legalNumber = false;
        }

        //Check if user has finished board
        boolean finished = sudoku.isFinished();

        if(finished){
            text1.setOpacity(1); //show you won text
        }else{
            text1.setOpacity(0); //hide text
        }

    }

    @FXML
    public void drawBoard(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());

        //Mark selected square
        if(legalNumber) {
            gc.setFill(Color.GREENYELLOW);
        }else{
            gc.setFill(Color.RED);
            legalNumber = true;
        }
        gc.fillRect(selectCol*(double)50+1,selectRow*(double)50+1, 48,48);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(sudoku.getNumber(i, j) != 0){
                    if(sudoku.getOriginalNumber(i,j) != 0) { //original numbers gets a grey background
                        gc.setFill(Color.LIGHTGRAY);
                        gc.fillRect(i * (double)50 + 1, j * (double)50 + 1, 48, 48);
                    }
                    gc.setFill(Color.BLACK);
                    gc.setFont(new Font(20d));
                    gc.fillText(""+sudoku.getNumber(i,j), i*50d +20, j*50d+32); //drawing number to canvas
                }
            }
        }
    }

    @FXML
    void createSudoku(ActionEvent event) throws IOException {
        sudoku.readSudokuFromFile();
        drawBoard();
    }

    @FXML
    void mirror(ActionEvent event) {
        sudoku.mirror();
        drawBoard();
    }

    @FXML
    void flip(ActionEvent event) {
        sudoku.flip();
        drawBoard();
    }

    @FXML
    void flipBlueLine(ActionEvent event) {
        sudoku.flipBlueLine();
        drawBoard();
    }

    @FXML
    void flipRedLine(ActionEvent event) {
        sudoku.flipRedLine();
        drawBoard();
    }

    @FXML
    void changeNumbers(ActionEvent event){
        sudoku.changeNumbers();
        drawBoard();
    }
}
