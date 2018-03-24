package player;
import list.*;

/**
 *  Chip Class records Chips' information
 **/


public class Chip {

    public int color; // the value of color are stored in Color Class.
    public int x;
    public int y;


    public Chip(int x, int y, int color){
        this.color = color;
        this.x = x;
        this.y = y;
    }


    /**
     *  find the chips (of the same color) that form connections with a chip
     *  @param b is an Game board
     *  @return DList that stores chips that connect with a "this" chip.
     **/
    public DList findPair(Board b) {
        return null;
    }
}
