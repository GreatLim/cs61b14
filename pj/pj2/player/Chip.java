package player;
import list.*;

/**
 *  Chip Class is used to store Chips' information
 **/


public class Chip {

    public int color;
    public int x;
    public int y;
    public static int bChipCount = 0;//count the number of black chips used.
    public static int wChipCount = 0;//count the number of white chips used.


    public Chip(Move m){

    }
    
    public boolean equals(Chip c)
    {
    		if(c.color==color && c.x==x && c.y==y)
    		{
    			return true;
    		}    	 
    		return false;  	 
    }

    public DList findPair(Board board) {
        return null;
    }
}
