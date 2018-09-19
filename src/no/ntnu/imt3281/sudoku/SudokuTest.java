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
        Sudoku sudoku = new Sudoku();

        int number1 = 5;
        int number2 = 2;
        int number3 = 7;

        sudoku.setNumber(0,0, number1);
        sudoku.setNumber(1,3, number2);
        sudoku.setNumber(2,7, number3);

        sudoku.mirror();

        //asserting all spaces we filled earlier are now 0/empty
        assertEquals(0, sudoku.getNumber(0, 0));
        assertEquals(0, sudoku.getNumber(1, 3));
        assertEquals(0, sudoku.getNumber(2, 7));

        //asserting that the opposite spaces are now the numbers from earlier
        assertEquals(number1, sudoku.getNumber(8, 0));
        assertEquals(number2, sudoku.getNumber(8-1, 3));
        assertEquals(number3, sudoku.getNumber(8-2, 7));

    }

    @Test
    public void flip() {
        Sudoku sudoku = new Sudoku();

        int number1 = 5;
        int number2 = 2;
        int number3 = 7;

        sudoku.setNumber(0,0, number1);
        sudoku.setNumber(3,1, number2);
        sudoku.setNumber(7,2, number3);

        sudoku.flip();

        //asserting all spaces we filled earlier are now 0/empty
        assertEquals(0, sudoku.getNumber(0, 0));
        assertEquals(0, sudoku.getNumber(3, 1));
        assertEquals(0, sudoku.getNumber(7, 1));

        //asserting that the opposite spaces are now the numbers from earlier
        assertEquals(number1, sudoku.getNumber(0, 8));
        assertEquals(number2, sudoku.getNumber(3, 8-1));
        assertEquals(number3, sudoku.getNumber(7, 8-2));
    }

    @Test
    public void flipBlueLine() {
        Sudoku sudoku = new Sudoku();

        int number1 = 5;
        int number2 = 2;
        int number3 = 7;

        sudoku.setNumber(8,0, number1);
        sudoku.setNumber(8,1, number2);
        sudoku.setNumber(7,0, number3);

        sudoku.flipBlueLine();

        //asserting all spaces we filled earlier are now 0/empty
        assertEquals(0, sudoku.getNumber(8, 0));
        assertEquals(0, sudoku.getNumber(8, 1));
        assertEquals(0, sudoku.getNumber(7, 0));

        //asserting that the opposite spaces are now the numbers from earlier
        assertEquals(number1, sudoku.getNumber(0, 8));
        assertEquals(number2, sudoku.getNumber(1, 8));
        assertEquals(number3, sudoku.getNumber(0, 7));
    }

    @Test
    public void flipRedLine() {
        Sudoku sudoku = new Sudoku();

        int number1 = 5;
        int number2 = 2;
        int number3 = 7;

        sudoku.setNumber(0,0, number1);
        sudoku.setNumber(0,1, number2);
        sudoku.setNumber(1,0, number3);

        sudoku.flipRedLine();

        //asserting all spaces we filled earlier are now 0/empty
        assertEquals(0, sudoku.getNumber(0, 0));
        assertEquals(0, sudoku.getNumber(0, 1));
        assertEquals(0, sudoku.getNumber(1, 0));

        //asserting that the opposite spaces are now the numbers from earlier
        assertEquals(number1, sudoku.getNumber(8, 8));
        assertEquals(number2, sudoku.getNumber(7, 8));
        assertEquals(number3, sudoku.getNumber(8, 7));
    }
}