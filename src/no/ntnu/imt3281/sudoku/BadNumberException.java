package no.ntnu.imt3281.sudoku;

public class BadNumberException extends Exception {
    final int x;
    final int y;

    public BadNumberException (int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String getMessage(){
        return "Number exists in Col "+x+" and Row "+y;
    }

}
