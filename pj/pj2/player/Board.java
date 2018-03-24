/* Board.java*/

package player;

import list.*;

/**
 * Board class implements an 8 * 8 game board with Chip for each cell
 **/

public class Board {

    public final static int DIMENSION = 8;

    public Chip[][] grid = new Chip[DIMENSION][DIMENSION];

    public Board() {
        for(int i = 0; i < DIMENSION; i ++) {
            for(int j = 0; j < DIMENSION; j ++) {
                grid[i][j] = new Chip(i, j, Color.SPACE);
            }
        }
    }

    /**
     *  set a chip on the game board
     *
     *  @param c a chip that will be set
     **/

    public void setChip(Chip c) {
        grid[c.x][c.y] = c;
    }

    /**
     *  delete a chip on the game board
     *
     *  @param c a chip that will be deleted
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
        return 0;
    }

    /**
     * find the best move
     *
     * @param side is MachinePlayer.COMPUTER or MachinePlayer.OPPONENT
     * @return Best with score
     **/

    public Best chooseMove(boolean side) {
        return null;
    }


    /**
     *  isValidMove() determines whether move "m" of player "side" is a valid move on "this" Game
     *  Board
     *
     *	Unusual conditions:
     *	If side is neither MachinePlayer.COMPUTER nor MachinePlayer.OPPONENT, returns false.
     *	If GameBoard squares contain illegal values, the behavior of this, method is undefined
     *	@param side is MachinePlayer.COMPUTER or MachinePlayer.OPPONENT
     *	@return true if move "m" of player "side" is a valid move in "this" GameBoard; otherwise,
     *	false
     **/

    public boolean isValidMove(boolean side, Move m) {
        return false;
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



    public DList generateValidMove(boolean side) {
        return null;
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
