/* Board.java*/

package player;

import list.*;

/**
 * Board class implements an 8 * 8 game board with Chip for each cell
 **/

public class Board {

    public final static int DIMENSION = 8;

    public Chip[][] grid = new Chip[DIMENSION][DIMENSION];
    public DList ChipList = new DList();//assume the game board will make change but not construct a new one every time it changes


    public Board() {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                grid[i][j] = new Chip(i, j, Color.SPACE);
            }
        }
    }

    /**
     * set a chip on the game board
     *
     * @param c a chip that will be set
     **/

    public void setChip(Chip c) {
        grid[c.x][c.y] = c;
    }

    /**
     * delete a chip on the game board
     *
     * @param c a chip that will be deleted
     **/

    public void delChip(Chip c) {
        grid[c.x][c.y].color = Color.SPACE;
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
        } else {
            return PairNum1 - PairNum2;
        }
    }

    public int getPairNum(boolean side) {
        int c = MachinePlayer.checkColor(side);
        int sum = 0;
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                if (grid[i][j].color == c) {
                    sum += grid[i][j].findPair(this).length();
                }
            }
        }
        return sum / 2;
    }


    /**
     * set the Board for
     *
     * @param m    Move
     * @param side
     * @return
     */

    //suppose Move= Add or Step only, not quit
    protected Board setBoard(Move m, int side) {
        Chip c = new Chip(m.x1, m.y1, side);
        if (m.moveKind == Move.STEP) {
            grid[m.x2][m.y2].color = Color.SPACE;
            Chip c2 = new Chip(m.x2, m.y2, side);
            try {
                ListNode n = ChipList.front();
                while (n.isValidNode()) {
                    if (c2.equals((Chip) n.item())) {
                        n.remove();
                    }
                    n = n.next();
                }

            } catch (InvalidNodeException e) {
                System.out.println(e);
            }

        }
        grid[m.x1][m.y1].color = side;
        ChipList.insertBack(c);
        return this;
    }

    // discuss ChipList ???

    protected void restoreBoard(Move m, int color) {
        // m1 is a move that reverses m
        if (m.moveKind == Move.STEP) {
            Move m1 = new Move(m.x2, m.y2, m.x1, m.y1);
            setBoard(m1, color);
        } else {
            grid[m.x1][m.y1].color = Color.SPACE;
        }
    }

    /**
     * find the best move
     *
     * @param side        is MachinePlayer.COMPUTER or MachinePlayer.OPPONENT
     * @param alpha       is the score that MachinePlayer.COMPUTER knows it can achieve(it should be initialized with -46 )
     * @param beta        is the score that MachinePlayer.OPPONENT knows it can achieve(it should be initialized with 46 )
     * @param searchDepth is depth that this recursion can achieve
     * @param mark        is used to record searchDepth (it should be initialized with 0)
     * @return Best objection that stores best move
     */

    public Best chooseMove(boolean side, int alpha, int beta, int searchDepth, int mark) {
        Best myBest = new Best();
        Best reply;
        int color = MachinePlayer.checkColor(side);
        DList l; //  stores each move

        if (mark == searchDepth || hasValidNetwork(side)) {
            myBest.score = evaluate(side);
            myBest.move = null;
        }

        if(side == MachinePlayer.COMUPTER) {
            myBest.score = alpha;
        } else {
            myBest.score = beta;
        }

        l = this.generateValidMove(color);
        ListNode n = l.front();
        while (n.isValidNode()) {
            try {
                Move m = (Move) n.item();
                setBoard(m, color);
                mark += 1;
                reply = chooseMove(!side, alpha, beta, searchDepth, mark);
                restoreBoard((Move) n.item(), color);
                if (side == MachinePlayer.COMUPTER && reply.score > myBest.score) {
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


    /**
     * Be used in isValidMove() Test if a player have more than two chips in a connected group, whether
     * connected orthogonally or diagonally.
     *
     * @param side
     * @param m
     * @return true if move "m" of player "side" makes three chips in a connected group in "this" GameBoard; otherwise,
     * false
     */

    private boolean isConnected(int side, Move m) {
        //moveKind is supposed to be STEP or ADD
        if (m.moveKind == Move.STEP) {
            grid[m.x2][m.y2].color = -1;
        }
        grid[m.x1][m.y1].color = side;
        DList l1 = grid[m.x1][m.y1].findPair(this);//the chip itself should not be return by findPair()
        // the data type of the item in DListNode is Chip
        ListNode n = l1.front();
        while (n.isValidNode()) {
            try {
                DList l2 = grid[((Chip) n.item()).x][((Chip) n.item()).y].findPair(this);
                if (l1.intersection(l2)) {
                    if (m.moveKind == Move.STEP) {
                        grid[m.x2][m.y2].color = side;
                    }
                    grid[m.x1][m.y1].color = -1;
                    return true;
                }
                n = n.next();
            } catch (InvalidNodeException e) {
                System.out.print(e);
            }
        }
        if (m.moveKind == Move.STEP) {
            grid[m.x2][m.y2].color = side;
        }
        grid[m.x1][m.y1].color = -1;
        return false;
    }


    /**
     * isValidMove() determines whether move "m" of player "side" is a valid move on "this" Game
     * Board
     * <p>
     * Unusual conditions:
     * If side is neither MachinePlayer.COMPUTER nor MachinePlayer.OPPONENT, returns false.
     * If GameBoard squares contain illegal values, the behavior of this, method is undefined
     *
     * @param side is MachinePlayer.COMPUTER or MachinePlayer.OPPONENT
     * @return true if move "m" of player "side" is a valid move in "this" GameBoard; otherwise,
     * false
     **/

    protected boolean isValidMove(int side, Move m) {
        // is the moveKind is not Valid the method is undefined
        //Quit is always valid;
        if (m.moveKind == Move.QUIT) {
            return true;
        }
        //rule3
        if ((m.x1 == 0 && m.y1 == 0) || (m.x1 == 0 && m.y1 == 7) || (m.x1 == 7 && m.y1 == 0) || (m.x1 == 7 && m.y1 == 7)) {
            return false;
        }

        //rule2
        if ((side == 1 && (m.y1 == 0 || m.y1 == 7)) || (side == 0 && (m.x1 == 0 || m.x1 == 7))) {
            return false;
        }

        //rule1
        if (grid[m.x1][m.y1].color != -1) {
            return false;
        }

        //rule4
        if (isConnected(side, m)) {
            return false;
        } else {
            return true;
        }

    }


    /**
     * generateValidMove() generates a list of all valid moves of player "side" on "this" Game Board
     * <p>
     * Unusual conditions:
     * If side is neither MachinePlayer.COMPUTER nor MachinePlayer.OPPONENT, returns false.
     * If GameBoard squares contain illegal values, the behavior of this, method is undefined
     *
     * @param side is MachinePlayer.COMPUTER or MachinePlayer.OPPONENT
     * @return valid moves are returned in the "item" of the return DList
     **/


    public DList generateValidMove(int side) {

        DList l = new DList();
        //add if haveLeftChip()=true
        if (haveLeftChip(side)) {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    Move m = new Move(x, y);
                    if (isValidMove(side, m)) {
                        l.insertFront(m);
                    }
                }
            }
        }
        //step
        //if chip.color==side, x=chip.x, y..;


        try {
            ListNode n = ChipList.front();
            while (n.isValidNode()) {
                int x = ((Chip) n.item()).x,
                        y = ((Chip) n.item()).y;
                int i = 1;
                while (i >= -1) {
                    int j = 1;
                    while (j >= -1) {
                        Move m = new Move(x + i, y + j);
                        if (isValidMove(side, m)) {
                            l.insertFront(m);
                        }
                        j = j - 2;
                    }
                    i = i - 2;
                }

                n = n.next();
            }

        } catch (InvalidNodeException e) {
            System.out.println(e);
        }

        return l;
    }

    private boolean haveLeftChip(int side) {
        if (side == 0) {
            return Chip.bChipCount < 10;
        } else {
            return Chip.wChipCount < 10;
        }
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
        return false;
    }
}
