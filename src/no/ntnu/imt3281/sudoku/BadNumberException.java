package no.ntnu.imt3281.sudoku;

/**
 * Title:   BadNumberException
 * Desc:    Custom exception class
 */
public class BadNumberException extends Exception {
    final int x;
    final int y;

    /**
     * Title:       BadNumberException
     * DesC:        Constructor
     * @param x     x nr
     * @param y     y nr
     */
    public BadNumberException (int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String getMessage(){
        return "Number exists in Col "+x+" and Row "+y;
    }

}
