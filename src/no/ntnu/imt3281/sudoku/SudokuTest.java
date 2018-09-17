package no.ntnu.imt3281.sudoku;

import org.junit.Test;

import java.io.IOException;

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
    public void getOriginalNumber() {
        Sudoku sudoku = new Sudoku();

        int number = 5;
        sudoku.originalBoard[0][0] = number;

        assertEquals(number, sudoku.getOriginalNumber(0,0));
    }

    @Test
    public void setNumber() {
        Sudoku sudoku = new Sudoku();
        int number = 5;
        sudoku.setNumber(5,5, number);

        assertEquals(5, sudoku.getNumber(5,5));
    }

    @Test
    public void readSudokuFromFile() throws IOException {
        Sudoku sudoku = new Sudoku();

        assertEquals(0, sudoku.getNumber(0,0));
        sudoku.readSudokuFromFile(); //we know that 0,0 should be 5
        assertEquals(5, sudoku.getNumber(0,0));
    }

    @Test
    public void resetOriginalBoard() {
        Sudoku sudoku = new Sudoku();

        int number = 5;
        sudoku.originalBoard[0][0] = number;

        sudoku.resetOriginalBoard();

        assertNotEquals(number, sudoku.getOriginalNumber(0,0));
    }

    @Test
    public void isLegal() {
        Sudoku sudoku = new Sudoku();
        boolean legal;
        sudoku.setNumber(0,0, 1);

        legal = sudoku.isLegal(0,1, 1);

        assertFalse(legal);

        legal = sudoku.isLegal(0,1, 2);

        assertTrue(legal);
    }

    @Test
    public void mirror() {
    }

    @Test
    public void flip() {
    }

    @Test
    public void flipBlueLine() {
    }

    @Test
    public void flipRedLine() {
    }
}