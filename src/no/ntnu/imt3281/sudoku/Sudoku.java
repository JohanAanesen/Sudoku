package no.ntnu.imt3281.sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class:        Sudoku
 * Description:  Sudoku class holds all functions and data related to playing the game
 */
public class Sudoku extends Application {

    private static final Logger LOGGER = Logger.getLogger( Sudoku.class.getName() );

    protected int[][] board = new int[9][9];
    protected int[][] originalBoard = new int[9][9];

    @Override
    public void start(Stage primaryStage) throws Exception{

        ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle", new Locale("en", "US"));

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sudoku.fxml"), bundle);
        primaryStage.setTitle("Sudoku");
        primaryStage.setScene(new Scene(root));
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
    }

    /**
     * Title: Main
     * @param args arguments
     */
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
        if(getOriginalNumber(y,x) == 0) {
            board[y][x] = value;
        }
    }

    protected void setOriginalNumber(int y, int x, int value){
        originalBoard[y][x] = value;
    }


    /**
     * Title:   ReadSudokuFromFile
     * @throws  IOException     IOException if something is wrong reading file
     */
    protected void readSudokuFromFile() throws IOException {
        File file = new File("sudoku1.json");
        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fis = null;
        int len = 0;


        try (FileInputStream fis2 = new FileInputStream(file)){
                fis = fis2;
                len = fis.read(bytes);
        }catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new IOException(e);
        } finally {
            if(fis != null) {
                fis.close();
            }
        }

        if(len != bytes.length){
            return;
        }

        String[] valueStr = new String(bytes, Charset.defaultCharset()).trim().split("[^0-9]+");

        int[][] newBoard = new int[9][9];

        int count = 1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                newBoard[i][j] = Integer.parseInt(valueStr[count++]);
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //resets board
                originalBoard[i][j] = 0;
                //copies the new board
                originalBoard[i][j] = newBoard[i][j];
            }
        }

        this.board = newBoard;
    }

    /**
     * Title:   resetOriginalBoard
     * Desc:    Resets OriginalBoard to be equal Board before it gets tampered with :)
     */
    protected void resetOriginalBoard(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //resets board
                originalBoard[i][j] = 0;
                //copies the new board
                originalBoard[i][j] = board[i][j];
            }
        }
    }

    /**
     * Title:   isLegal
     * Desc:    Asserts that a number is a legal number in a given sudoku tile,
     *          will check that same number is not occuring on the same row, column
     *          or 'box'
     * @param   selectCol   selected column
     * @param   selectRow   selected row
     * @param   nr          number from input
     * @return  boolean     true|false
     */
    protected boolean isLegal(int selectCol, int selectRow, int nr){
        boolean legal = true;

        //you can't edit a original number
        if(originalBoard[selectCol][selectRow] != 0){
            return false;
        }

        //if number is already there then it must be legal
        if(board[selectCol][selectRow] == nr){
            return true;
        }

        //number must be > 0
        if(nr < 1){
            return false;
        }

        //Check col
        try{
            checkCol(selectCol, nr);
        }catch (BadNumberException e){
            legal = false;
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

        //Check row
        try{
            checkRow(selectRow, nr);
        }catch (BadNumberException e){
            legal = false;
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

        //Check box
        try{
            checkBox(selectCol, selectRow, nr);
        }catch (BadNumberException e){
            legal = false;
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

        return legal;
    }

    /**
     * Title:                       checkCol
     * Desc:                        checks selected column and throws BadNumberException if nr already exists
     * @param selectCol             selected column
     * @param nr                    number
     * @throws BadNumberException   exception
     */
    protected void checkCol(int selectCol, int nr) throws BadNumberException{
        IterateCol iterateCol = new IterateCol(selectCol);
        while(iterateCol.hasNext()){
            if((int)iterateCol.next() == nr){
                throw new BadNumberException("Col Exception:", selectCol, iterateCol.pos-1);
            }
        }
    }

    /**
     * Title:                       checkRow
     * Desc:                        checks selected row and throws BadNumberException if nr already exists
     * @param selectRow             selected row
     * @param nr                    number
     * @throws BadNumberException   exception
     */
    protected void checkRow(int selectRow, int nr) throws BadNumberException{
        IterateRow iterateRow = new IterateRow(selectRow);
        while(iterateRow.hasNext()){
            if((int)iterateRow.next() == nr){
                throw new BadNumberException("Row Exception:", iterateRow.pos-1, selectRow);
            }
        }
    }

    /**
     * Title:                       checkBox
     * Desc:                        checks selected box and throws BadNumberException if nr already exists
     * @param selectCol             selected col
     * @param selectRow             selected row
     * @param nr                    number
     * @throws BadNumberException   exception
     */
    protected void checkBox(int selectCol, int selectRow, int nr) throws BadNumberException{
        IterateBox iterateBox = new IterateBox(selectCol, selectRow);
        while(iterateBox.hasNext()){
            if((int)iterateBox.next() == nr){
                throw new BadNumberException("Box Exception:", iterateBox.col+iterateBox.posy, iterateBox.row+iterateBox.posx);
            }
        }
    }

    /**
     * Title:   isFinished
     * Desc:    The function iterates the board and returns true if it is completed, false if not
     * @return  boolean     true|false
     */
    protected boolean isFinished(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(getNumber(i,j) == 0 || (getNumber(i,j) != 0 && getOriginalNumber(i,j) == 0 && !isLegal(i,j, getNumber(i,j)))) {
                    //sudoku is not complete if any tile is 0
                    return false;
                }
            }
        }
        //all tiles are legal and filled, sudoku is complete :)
        return true;
    }

    /**
     * Title:   mirror
     * Desc:    The function mirrors the board, e.g top-left number becomes top-right
     */
    protected void mirror() {
        for (int i = 0; i < 9/2; i++) {
            for (int j = 0; j < 9; j++) {
                int temp = getNumber(i, j);
                setNumber(i, j, getNumber(8-i, j));
                setNumber(8-i, j, temp);
            }
        }
        resetOriginalBoard();
    }

    /**
     * Title:   flip
     * Desc:    The function flips the board, e.g top-left becomes bottom-left
     */
    protected void flip() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9/2; j++) {
                int temp = getNumber(i, j);
                setNumber(i, j, getNumber(i, 8-j));
                setNumber(i, 8-j, temp);
            }
        }
        resetOriginalBoard();
    }

    /**
     * Title:   flipBlueLine
     * Desc:    The function flips the board over the 'blue line'
     *          (line from top-left to bottom-right corner)
     *          e.g bottom-left number becomes top-right number
     */
    protected void flipBlueLine() {
        for (int i = 0; i < 9; i++) {
            for (int j = i; j < 9; j++) {
                int temp = getNumber(i, j);
                setNumber(i, j, getNumber(j, i));
                setNumber(j, i, temp);
            }
        }
        resetOriginalBoard();
    }

    /**
     * Title:   flipRedLine
     * Desc:    The function flips the board over the 'red line'
     *          (line from bottom-left to top-right corner)
     *          e.g top-left number becomes bottom-right number
     */
    protected void flipRedLine() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9 - i; j++) {
                int temp = getNumber(i, j);
                setNumber(i, j, getNumber(8 - j, 8 - i));
                setNumber(8 - j, 8 - i, temp);
            }
        }
        resetOriginalBoard();
    }

    /**
     * Title:   changeNumbers
     * Desc:    The function will first create
     */
    protected void changeNumbers(){

        boolean[] numbers = new boolean[9]; //array keeps track of which numbers have been randomly assigned

        Random rnd = new Random();

        Arrays.fill(numbers, Boolean.TRUE);

        for (int k = 0; k < 9; k++) {                           //for every number (1-9)

            int newNumber = 0;

            boolean lookingForNumber = true;
            while(lookingForNumber){                            //get a random number that hasn't been taken before
                int temp = rnd.nextInt(9);              //get random number
                if(numbers[temp]){                              //make sure it hasn't been taken before
                    numbers[temp] = false;                      //take it
                    newNumber = temp+1;                         //set the new number
                    lookingForNumber = false;                   //not looking for number anymore
                }
            }

            for (int i = 0; i < 9; i++) {                       //iterate all numbers in sudoku
                for (int j = 0; j < 9; j++) {
                    if(getOriginalNumber(i,j) == k+1){          //if a number in originalNumber is = k
                        setNumber(i,j, newNumber);              //set that number = newNumber

                    }
                }
            }
        }
        resetOriginalBoard();                                   //reset Original Board
    }


    /**
     * Class iterateRow
     * Desc: Custom iterator for row
     */
    public class IterateRow implements Iterator{
        int row;
        int pos;

        /**
         * Constructor
         * @param row row to be iterated
         */
        public IterateRow(int row){
            this.row = row;
            this.pos = 0;
        }

        @Override
        public boolean hasNext() {
            return this.pos < 9;
        }

        @Override
        public Object next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            return board[pos++][row];
        }
    }

    /**
     * Class iterateCol
     * Desc: Custom iterator for col
     */
    public class IterateCol implements Iterator{
        int col;
        int pos;

        /**
         * Constructor
         * @param col column to be iterated
         */
        public IterateCol(int col){
            this.col = col;
            this.pos = 0;
        }

        @Override
        public boolean hasNext() {
            return this.pos < 9;
        }

        @Override
        public Object next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            return board[col][pos++];
        }
    }

    /**
     * Class IterateBox
     * Desc: Custom iterator for box
     */
    public class IterateBox implements Iterator{
        int row;
        int col;
        int posx;
        int posy;
        int pos;

        /**
         * constructor
         * @param col column to be iterated
         * @param row row to be iterated
         */
        public IterateBox(int col, int row){
            this.col = col/3*3;
            this.row = row/3*3;
            this.posx = 0;
            this.posy = 0;
            this.pos = 0;
        }

        @Override
        public boolean hasNext() {
            return this.pos < 9;
        }

        @Override
        public Object next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }

            switch(pos){
                case 0: posx = 0; posy = 0; break;
                case 1: posx = 1; posy = 0; break;
                case 2: posx = 2; posy = 0; break;
                case 3: posx = 0; posy = 1; break;
                case 4: posx = 1; posy = 1; break;
                case 5: posx = 2; posy = 1; break;
                case 6: posx = 0; posy = 2; break;
                case 7: posx = 1; posy = 2; break;
                case 8: posx = 2; posy = 2; break;
                default: pos = 8; break;
            }
            pos++;
            return board[col+posy][row+posx];

        }
    }
}
