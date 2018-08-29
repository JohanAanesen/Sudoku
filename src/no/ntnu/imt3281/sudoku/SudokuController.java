package no.ntnu.imt3281.sudoku;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import java.util.concurrent.ThreadLocalRandom;

import static no.ntnu.imt3281.sudoku.Sudoku.setupBoard;
import static no.ntnu.imt3281.sudoku.Sudoku.sudNr;


public class SudokuController {

    @FXML
    private GridPane grid;

    @FXML
    private Button knapp1;

    @FXML
    void test(ActionEvent Event){
        int nr = 0;

        setupBoard();

        for(int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                TextField text = new TextField();
                text.setText(Integer.toString(sudNr[i][j]));
                text.setFont(Font.font(20));
                grid.add(text, i, j);
            }
        }
    }
}
