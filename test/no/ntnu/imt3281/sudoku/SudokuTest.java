package no.ntnu.imt3281.sudoku;

import org.hamcrest.CustomMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.Assert.*;

public class SudokuTest {

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
        sudoku.setNumber(3,1, 1);

        legal = sudoku.isLegal(0,1, 1); //should trigger every exception

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

    @Test
    public void changeNumbers(){
        Sudoku sudoku = new Sudoku();

        sudoku.setNumber(0,0, 5);   //set 2 pairs of numbers in the board
        sudoku.setNumber(8,8,5);

        sudoku.setNumber(0,1, 7);
        sudoku.setNumber(8,7,7);

        sudoku.changeNumbers(); //change numbers

        int number1 = sudoku.getNumber(0,0); //get the number of the first tile
        int number2 = sudoku.getNumber(0,1); //get the number of the 2nd tile/pair

        assertEquals(number1, sudoku.getNumber(8,8));   //assert equal to its mate
        assertEquals(number2, sudoku.getNumber(8,7));   //same
    }

    @Test
    public void isFinished(){
        Sudoku  sudoku = new Sudoku();

        assertFalse(sudoku.isFinished());

        //now we feed the board a completed sudoku
        int[][] completedBoard =  new int[][]{  {5,3,4,6,7,8,9,1,2},
                {6,7,2,1,9,5,3,4,8},
                {1,9,8,3,4,2,5,6,7},
                {8,5,9,7,6,1,4,2,3},
                {4,2,6,8,5,3,7,9,1},
                {7,1,3,9,2,4,8,5,6},
                {9,6,1,5,3,7,2,8,4},
                {2,8,7,4,1,9,6,3,5},
                {3,4,5,2,8,6,1,7,9}};

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudoku.setNumber(i,j, completedBoard[i][j]);
            }
        }

        assertTrue(sudoku.isFinished());
    }

    @Test
    public void testColException() {
        Sudoku sudoku = new Sudoku();
        sudoku.setNumber(0,0, 1);

        try {
            sudoku.checkCol(0, 1);
        }catch(BadNumberException e){
            assertEquals(e.getMessage(), "Number exists in Col 0 and Row 0");
        }
    }

    @Test
    public void testRowException() {
        Sudoku sudoku = new Sudoku();
        sudoku.setNumber(0,0, 1);

        try {
            sudoku.checkRow(0, 1);
        }catch(BadNumberException e){
            assertEquals(e.getMessage(), "Number exists in Col 0 and Row 0");
        }
    }

    @Test
    public void testBoxException() {
        Sudoku sudoku = new Sudoku();
        sudoku.setNumber(0,0, 1);

        try {
            sudoku.checkBox(0, 0, 1);
        }catch(BadNumberException e){
            assertEquals(e.getMessage(), "Number exists in Col 0 and Row 0");
        }
    }


}