package no.ntnu.imt3281.sudoku;

import static org.junit.Assert.*;

import org.junit.Test;

public class SudokuTest {

	private Sudoku sudoku;

	@Before
	public void setup(){
		sudoku = new Sudoku();
	}

	@Test
	public void testEmptyConstructor() {
		Sudoku sudoku = new Sudoku();
		assertTrue(sudoku instanceof Sudoku);
	}

	@Test
	public void testGetNumber() {
		int number = 5;


	}
}
