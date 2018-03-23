/* Board.java*/

package player;
import list.*;

/**
 *  Board class implements an 8 * 8 game board with Chip for each cell
 **/

public class Board {
    public final static int DIMENSION = 8;
    public static Chip[][] grid = new Chip[DIMENSION][DIMENSION];
    public int score;


    /**
     *  set each board with a value for evaluation
     **/
    public void setScore(){
    }


    /**
     *  generateValidMove() generates a list of all valid moves of player "side" on "this" Game Board
     *
     *	Unusual conditions:
     *	If side is neither MachinePlayer.COMPUTER nor MachinePlayer.OPPONENT, returns false.
     *	If GameBoard squares contain illegal values, the behavior of this, method is undefined
     *	@param side is MachinePlayer.COMPUTER or MachinePlayer.OPPONENT
     *	@return valid moves are returned in the "item" of the return DList
     **/

    public DList generateValidMove(boolean side){
        return null;
    }

    /**
     *  hasValidNetwork() determines whether "this" GameBoard has a valid network
     *  for player "side".  (Does not check whether the opponent has a network.)
     *  A full description of what constitutes a valid network appears in the
     *  project "readme" file.
     *
     *  Unusual conditions:
     *    If side is neither MachinePlayer.COMPUTER nor MachinePlayer.OPPONENT,
     *          returns false.
     *    If GameBoard squares contain illegal values, the behavior of this
     *          method is undefined (i.e., don't expect any reasonable behavior).
     *
     *  @param side is MachinePlayer.COMPUTER or MachinePlayer.OPPONENT
     *  @return true if player "side" has a winning network in "this" GameBoard;
     *          false otherwise.
     **/

    public boolean hasValidNetwork(boolean side) {
        return false;
    }
}
