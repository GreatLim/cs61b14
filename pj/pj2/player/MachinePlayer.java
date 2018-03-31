/* MachinePlayer.java */

package player;

import list.DList;
import list.InvalidNodeException;
import list.ListNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * An implementation of an automatic Network player.  Keeps track of moves
 * made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {

    public final static boolean COMPUTER = true;
    public final static boolean OPPONENT = false;

    public static Board board = new Board();

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
        Move move = findBest(MachinePlayer.COMPUTER, -46, 46, 2, 0).move;
        if (move != null) {
            if (move.moveKind != Move.QUIT) {
                board.setBoard(move, color);
                board.printBoard();
            }
        }
        return move;
    }

    /**
     *  find the best move
     *  @param side is MachinePlayer.COMPUTER or MachinePlayer.OPPONENT
     *  @param alpha is the score that MachinePlayer.COMPUTER knows it can achieve(it should be initialized with -46 )
     *  @param beta is the score that MachinePlayer.OPPONENT knows it can achieve(it should be initialized with 46 )
     *  @param searchDepth is depth that this recursion can achieve
     *  @param mark is used to record searchDepth (it should be initialized with 0)
     *  @return Best objection that stores best move
     **/

    public Best findBest(boolean side, int alpha, int beta, int searchDepth, int mark) {
        Board b = board;
        Best myBest = new Best();
        Best reply;
        int color = checkColor(side);
        DList l; //  stores each move
        //board.printBoard();
        l = board.generateValidMove(color);
        //System.out.println(l.toString());

        if (mark == searchDepth || board.hasValidNetwork(side) || l == null) {
            myBest.score = board.evaluate(side);
            myBest.move = null;
            return myBest;
        }

        if (side == MachinePlayer.COMPUTER) {
            myBest.score = alpha;
        } else {
            myBest.score = beta;
        }

        ListNode n = l.front();
        while (n.isValidNode()) {
            try {
                Move m = (Move) n.item();
                board.setBoard(m, color);
                mark ++;
                reply = findBest(!side, alpha, beta, searchDepth, mark);
                board.restoreBoard((Move) n.item(), color);
                mark --;
                if (side == MachinePlayer.COMPUTER && reply.score > myBest.score) {
                    myBest.move = m;
                    myBest.score = reply.score;
                    alpha = reply.score;
                } else if (side == MachinePlayer.OPPONENT && reply.score < myBest.score) {
                    myBest.move = m;
                    myBest.score = reply.score;
                    beta = reply.score;
                }
                if (alpha >= beta) {
                    return myBest;
                }
                n = n.next();
            } catch (InvalidNodeException e) {
                System.out.print(e);
            }
        }
        return myBest;
    }


    // If the Move m is legal, records the move as a move by the opponent
    // (updates the internal game board) and returns true.  If the move is
    // illegal, returns false without modifying the internal state of "this"
    // player.  This method allows your opponents to inform you of their moves.
    public boolean opponentMove(Move m) {
        if (board.isValidMove(checkColor(MachinePlayer.OPPONENT), m)) {
            if (m.moveKind != Move.QUIT) {
                board.setBoard(m, checkColor(MachinePlayer.OPPONENT));
                board.printBoard();
            }
            return true;
        } else {
            return false;
        }

    }

    // If the Move m is legal, records the move as a move by "this" player
    // (updates the internal game board) and returns true.  If the move is
    // illegal, returns false without modifying the internal state of "this"
    // player.  This method is used to help set up "Network problems" for your
    // player to solve.
    public boolean forceMove(Move m) {
        if (board.isValidMove(color, m)) {
            if (m.moveKind != Move.QUIT) {
                board.setBoard(m, color);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * check color for each side
     *
     * @param side is MachinePlayer.COMPUTER or MachinePlayer.OPPONENT
     * @return color of the side
     */

    public static int checkColor(boolean side) {
        int c;
        if (side == COMPUTER) {
            c = color;
        } else {
            c = Math.abs(1 - color);
        }
        return c;
    }

    public static void testFindBest() {
        BufferedReader keyBoard = new BufferedReader(new InputStreamReader(System.in));
        MachinePlayer p = new MachinePlayer(Color.WHITE,2);
        Board b = p.board;
        while(!b.hasValidNetwork(p.COMPUTER) || !b.hasValidNetwork(p.OPPONENT)) {
            System.out.println("Computer move: ");
            System.out.println(p.chooseMove() + "\n");
            try {
                System.out.println("Your move: ");
                System.out.print("input x-coordinates index: ");
                String x1 = keyBoard.readLine();
                System.out.print("input y-coordinates index: ");
                String y1 = keyBoard.readLine();
                Move m = new Move(Integer.valueOf(x1).intValue(), Integer.valueOf(y1).intValue());
                p.opponentMove(m);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args){
        System.out.println("\n------ test testFindBest() ------");
        testFindBest();
    }
}
