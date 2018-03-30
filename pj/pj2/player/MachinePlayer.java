/* MachinePlayer.java */

package player;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {

    public final static boolean COMPUTER = true;
    public final static boolean OPPONENT = false;

    public static Board board =  new Board();

    public static int color;
    public int searchDepth;
    public static boolean side;


    // Creates a machine player with the given color.  Color is either 0 (black)
    // or 1 (white).  (White has the first move.)
    public MachinePlayer(int color) {
        this.color = color;
    }

    // Creates a machine player with the given color and search depth.  Color is
    // either 0 (black) or 1 (white).  (White has the first move.)
    public MachinePlayer(int color, int searchDepth) {
        this.color = color;
        this.searchDepth = searchDepth;
    }

    // Returns a new move by "this" player.  Internally records the move (updates
    // the internal game board) as a move by "this" player.
    public Move chooseMove() {
        Move move = board.chooseMove(MachinePlayer.COMPUTER, -46, 46, searchDepth, 0).move;
        if(move != null) {
        	 if(move.moveKind != Move.QUIT) {
     			board.setBoard(move,color);
         }   
        }       
        return move;
    }

    // If the Move m is legal, records the move as a move by the opponent
    // (updates the internal game board) and returns true.  If the move is
    // illegal, returns false without modifying the internal state of "this"
    // player.  This method allows your opponents to inform you of their moves.
    public boolean opponentMove(Move m) {
    		if(board.isValidMove(checkColor(MachinePlayer.OPPONENT),m))
    		{
    			if(m.moveKind != Move.QUIT) {
        			board.setBoard(m,checkColor(MachinePlayer.OPPONENT));        			
            } 
    			return true;
    		}else {
    			return false;
    		}
        
    }

    // If the Move m is legal, records the move as a move by "this" player
    // (updates the internal game board) and returns true.  If the move is
    // illegal, returns false without modifying the internal state of "this"
    // player.  This method is used to help set up "Network problems" for your
    // player to solve.
    public boolean forceMove(Move m) {
    		if(board.isValidMove(color,m)) {
    			if(m.moveKind != Move.QUIT) {
        			board.setBoard(m,color);        			
            }return true;
    		}else {
    			return false;
    		}     
    }

    /**
     * check color for each side
     * @param side is MachinePlayer.COMPUTER or MachinePlayer.OPPONENT
     * @return color of the side
     */

    public static int checkColor(boolean side) {
        int c;
        if(side == COMPUTER) {
            c = color;
        } else {
            c = Math.abs(1 - color);
        }
        return c;
    }
    
}
