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
    
    public Board board = new Board();


    public int color;
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
        Move move = findBest(MachinePlayer.COMPUTER, -46, 46, 6, 0).move;
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
        

        if (mark == searchDepth || hasValidNetwork(side) || l == null) {
            myBest.score = evaluate(COMPUTER);

            myBest.move = null;
            return myBest;
        }

        if (side == COMPUTER) {
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
                if (side == COMPUTER && reply.score > myBest.score) {
                    myBest.move = m;
                    myBest.score = reply.score;
                    alpha = reply.score;
                } else if (side == OPPONENT && reply.score < myBest.score) {
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

    public int checkColor(boolean side) {
        int c;
        if (side == COMPUTER) {
            c = color;
        } else {
            c = Math.abs(1 - color);
        }
        return c;
    }
    
    /**
     * evaluate the game board and give a score
     *
     * @param side is MachinePlayer.COMPUTER or MachinePlayer.OPPONENT
     * @return score for the game board
     */
    public int evaluate(boolean side) {
        // pair number of this side
        int PairNum1 = getPairNum(side);
        // pair number of opponent side
        int PairNum2 = getPairNum(!side);
        if (this.hasValidNetwork(side)) {
            return 46;
        } else if(this.hasValidNetwork(!side)) {
            return -46;
        } else {
            return PairNum1 - PairNum2;
        }
    }
    
    public int getPairNum(boolean side) {
        int c = checkColor(side);
        int sum = 0;
        for (int i = 0; i <  board.DIMENSION; i++) {
            for (int j = 0; j < board.DIMENSION; j++) {
                if (board.grid[i][j].color == c) {
                    sum += board.grid[i][j].findPair(board).length();
                }
            }
        }
        return sum / 2;
    }
    
    /**
     * hasValidNetwork() determines whether "this" GameBoard has a valid network
     * for player "side".  (Does not check whether the opponent has a network.)
     * A full description of what constitutes a valid network appears in the
     * project "readme" file.
     * <p>
     * Unusual conditions:
     * If side is neither MachinePlayer.COMPUTER nor MachinePlayer.OPPONENT,
     * returns false.
     * If GameBoard squares contain illegal values, the behavior of this
     * method is undefined (i.e., don't expect any reasonable behavior).
     *
     * @param side is MachinePlayer.COMPUTER or MachinePlayer.OPPONENT
     * @return true if player "side" has a winning network in "this" GameBoard;
     * false otherwise.
     **/
    public boolean hasValidNetwork(boolean side) {
        int color = checkColor(side);
        if (board.start(color) == null) {
            return false;
        }
        ListNode u = board.start(color).front();
        try {
            while (u.isValidNode()) {
                ListNode v = ((Chip) u.item()).findPair(board).front();
                while (v.isValidNode()) {
                    boolean[][] key = new boolean[8][8];
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            key[i][j] = false;
                        }
                    }
                    ((Chip) u.item()).marker(key);
                    ((Chip) v.item()).marker(key);
                    if (!board.findPath(u, v, color, key, 2)) {
                        v = v.next();
                    } else {
                        return true;
                    }
                }
                u = u.next();
            }
            return false;
        } catch (InvalidNodeException e) {
            return false;
        }
    }


    public void testFindBest() {
        BufferedReader keyBoard = new BufferedReader(new InputStreamReader(System.in));
        MachinePlayer p = new MachinePlayer(Color.WHITE,3);
        Board b = p.board;
        while(!hasValidNetwork(p.COMPUTER) || !hasValidNetwork(p.OPPONENT)) {
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
    
    public void testHasValidNetwork() {
        color = Color.WHITE;
        System.out.println("MachinePlayer is WHITE");
        System.out.println("there is a valid network for MachinePlayer: " + hasValidNetwork(MachinePlayer.COMPUTER));
        System.out.println("there is a valid network for OpponentPlayer: " + hasValidNetwork(MachinePlayer.OPPONENT));
    }

    public static void main(String[] args){
    		MachinePlayer p = new MachinePlayer(Color.WHITE);
        System.out.println("\n------ test testFindBest() ------");
        p.testFindBest();
    }
}
