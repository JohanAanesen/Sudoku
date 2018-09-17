package no.ntnu.imt3281.sudoku;

import org.junit.Test;

import static org.junit.Assert.*;

public class SudokuTest {

    @Test
    public void start() {

    }

    @Test
    public void main() {
    }

    @Test
    public void getNumber() {
        Sudoku sudoku = new Sudoku();
        sudoku.setNumber(5,5, 5);

        int number = 5;
        assertEquals(number, sudoku.getNumber(5,5));
    }

    @Test
    public void setNumber() {
        Sudoku sudoku = new Sudoku();
        int number = 5;
        sudoku.setNumber(5,5, number);

        assertEquals(5, sudoku.getNumber(5,5));
    }

    @Test
    public void readSudokuFromFile() {
    }
}