package no.ntnu.imt3281.sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;


//TODO: Internationalisering
//TODO: JAVADOC

public class Sudoku extends Application {

    protected int[][] board = new int[9][9];
    protected int[][] originalBoard = new int[9][9];

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sudoku.fxml"));
        primaryStage.setTitle("Sudoku");
        primaryStage.setScene(new Scene(root));
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
    }

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
        board[y][x] = value;
    }


    /**
     * Title: ReadSudokuFromFile
     * TODO: redo function :)
     * @throws IOException
     */
    protected void readSudokuFromFile() throws IOException {
        File file = new File("src/no/ntnu/imt3281/sudoku/sudokus/sudoku1.json");
        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytes);
        fis.close();

        String[] valueStr = new String(bytes).trim().split("[^0-9]+");

        int[][] board = new int[9][9];

        int count = 1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = Integer.parseInt(valueStr[count++]);
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                originalBoard[i][j] = 0; //resets board
                originalBoard[i][j] = board[i][j]; //copies the new board
            }
        }

        this.board = board;
    }

    /**
     * Title: ResetOriginalBoard
     * Desc: Resets OriginalBoard to be equal Board before it gets tampered with :)
     */
    protected void resetOriginalBoard(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                originalBoard[i][j] = 0; //resets board
                originalBoard[i][j] = board[i][j]; //copies the new board
            }
        }
    }

    /**
     * Title: IsLegal
     * Desc: Asserts that a number is a legal number in a given sudoku tile,
     *       will check that same number is not occuring on the same row, column
     *       or 'box'
     * @param select_col
     * @param select_row
     * @param nr
     * @return
     */
    protected boolean isLegal(int select_col, int select_row, int nr){
        boolean legal = true;

        if(originalBoard[select_col][select_row] != 0){ //you can't edit a original number
            return false;
        }

        if(nr < 1){return false;} //number must be > 0

        //Check row
        try{
            IterateRow iterateRow = new IterateRow(select_row);
            while(iterateRow.hasNext()){
                if((int)iterateRow.next() == nr){
                    legal = false;
                    throw new BadNumberException(iterateRow.pos-1, select_row);
                }
            }
        }catch (BadNumberException e){
            System.out.println("Exception: "+e.getMessage());
        }

        //Check Column
        try{
            IterateCol iterateCol = new IterateCol(select_col);
            while(iterateCol.hasNext()){
                if((int)iterateCol.next() == nr){
                    legal = false;
                    throw new BadNumberException(select_col, iterateCol.pos-1);
                }
            }
        }catch (BadNumberException e){
            System.out.println("Exception: "+e.getMessage());
        }

        //Check box
        try{
            IterateBox iterateBox = new IterateBox(select_col, select_row);
            while(iterateBox.hasNext()){
                if((int)iterateBox.next() == nr){
                    legal = false;
                    throw new BadNumberException(iterateBox.col+iterateBox.posy, iterateBox.row+iterateBox.posx);
                }
            }
        }catch (BadNumberException e){
            System.out.println("Exception: "+e.getMessage());
        }
        return legal;
    }

    /**
     * Title: Mirror
     * Desc: the function mirrors the board, e.g top-left number becomes top-right
     */
    protected void mirror() {
        for (int i = 0; i < 9/2; i++) {
            for (int j = 0; j < 9; j++) {
                //int temp = this.board[i][j];
                //this.board[i][j] = this.board[8-i][j];
                //this.board[8-i][j] = temp;

                int temp = getNumber(i, j);
                setNumber(i, j, getNumber(8-i, j));
                setNumber(8-i, j, temp);
            }
        }
        resetOriginalBoard();
    }

    /**
     * Title: Flip
     * Desc: the function flips the board, e.g top-left becomes bottom-left
     */
    protected void flip() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9/2; j++) {
                //int temp = this.board[i][j];
                //this.board[i][j] = this.board[i][8-j];
                //this.board[i][8-j] = temp;

                int temp = getNumber(i, j);
                setNumber(i, j, getNumber(i, 8-j));
                setNumber(i, 8-j, temp);
            }
        }
        resetOriginalBoard();
    }

    /**
     * Title: FlipBlueLine
     * Desc: the function flips the board over the 'blue line'
     *       (line from top-left to bottom-right corner)
     *       e.g bottom-left number becomes top-right number
     */
    protected void flipBlueLine() {
        for (int i = 0; i < 9; i++) {
            for (int j = i; j < 9; j++) {
                //int temp = this.board[i][j];
                //this.board[i][j] = this.board[j][i];
                //this.board[j][i] = temp;

                int temp = getNumber(i, j);
                setNumber(i, j, getNumber(j, i));
                setNumber(j, i, temp);
            }
        }
        resetOriginalBoard();
    }

    /**
     * Title: FlipRedLine
     * Desc: the function flips the board over the 'red line'
     *       (line from bottom-left to top-right corner)
     *       e.g top-left number becomes bottom-right number
     */
    protected void flipRedLine() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9 - i; j++) {
                //int temp = this.board[i][j];
                //this.board[i][j] = this.board[8-j][8-i];
                //this.board[8-j][8-i] = temp;

                int temp = getNumber(i, j);
                setNumber(i, j, getNumber(8 - j, 8 - i));
                setNumber(8 - j, 8 - i, temp);
            }
        }
        resetOriginalBoard();
    }

    /**
     * Title: ChangeNumbers
     * Desc: the function will first create
     */
    protected void changeNumbers(){

        boolean[] numbers = new boolean[9]; //array keeps track of which numbers have been randomly assigned

        Arrays.fill(numbers, Boolean.TRUE);

        for (int k = 0; k < 9; k++) {       //for every number (1-9)

            int newNumber = 0;

            boolean lookingForNumber = true;
            while(lookingForNumber){               //get a random number that hasn't been taken before
                int temp = (int)Math.floor(Math.random()*9);    //get random number
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
     * Class IterateRow
     * Desc: custom iterator for row
     */
    public class IterateRow implements Iterator{
        int row;
        int pos;

        public IterateRow(int row){
            this.row = row;
            this.pos = 0;
        }

        @Override
        public boolean hasNext() {
            if(this.pos < 9){
                return true;
            }else{
                return false;
            }
        }

        @Override
        public Object next() {
            return board[pos++][row];
        }
    }

    /**
     * Class IterateCol
     * Desc: custom iterator for col
     */
    public class IterateCol implements Iterator{
        int col;
        int pos;

        public IterateCol(int col){
            this.col = col;
            this.pos = 0;
        }

        @Override
        public boolean hasNext() {
            if(this.pos < 9){
                return true;
            }else{
                return false;
            }
        }

        @Override
        public Object next() {
            return board[col][pos++];
        }
    }

    /**
     * Class IterateBox
     * Desc: custom iterator for box
     */
    public class IterateBox implements Iterator{
        int row,col;
        int posx, posy;
        int pos;

        public IterateBox(int col, int row){
            this.col = col/3*3;
            this.row = row/3*3;
            this.posx = 0;
            this.posy = 0;
            this.pos = 0;
        }

        @Override
        public boolean hasNext() {
            if(pos < 9){
                return true;
            }else{
                return false;
            }
        }

        @Override
        public Object next() {
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
            }
            pos++;
            return board[col+posy][row+posx];

        }
    }
}
