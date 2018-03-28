/* Board.java*/

package player;

import list.*;
import java.io.*;

/**
 * Board class implements an 8 * 8 game board with Chip for each cell
 **/

public class Board {

    public final static int DIMENSION = 8;

    public Chip[][] grid = new Chip[DIMENSION][DIMENSION];
    


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
    //discuss about validity of Chip 
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

     * @param m Move
     * @param color

     */

    //suppose Move= Add or Step only, not quit
    protected Board setBoard(Move m, int color) {
        Chip c = new Chip(m.x1, m.y1, color);//the amount of chip is wrong ????
        //if chip c is not used, we should change it to "new Chip(m.x1, m.y1, color);"
        if (m.moveKind == Move.STEP) {
            grid[m.x2][m.y2].color = Color.SPACE;
            Chip c2 = new Chip(m.x2, m.y2, color);//why?
        }
        grid[m.x1][m.y1].color = color;
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


    private DList generateChipList(int color)
    {
    		DList ChipList = new DList();
    		for(int i=0;i<8;i++)
    		{
    			for(int j=0;j<8;j++)
    			{
    				if(grid[i][j].color==color)
    				{
    					ChipList.insertBack(grid[i][j]);
    				}
    			}
    		}
    		return ChipList;

    
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


    private boolean isConnected(int color, Move m)
    {
    	//moveKind is supposed to be STEP or ADD
    		if(m.moveKind == Move.STEP) 
    		{
    			grid[m.x2][m.y2].color = Color.SPACE;
    		}
    		grid[m.x1][m.y1].color = color;   		
    		DList l1 = grid[m.x1][m.y1].findPair(this); 
    		// the data type of the item in DListNode is Chip
    		ListNode n =l1.front();
    		while(n.isValidNode())
    		{   			   			
    			try{
    				DList l2 = grid[((Chip)n.item()).x][((Chip)n.item()).y].findPair(this);
    				if(l1.intersection(l2))
    				{
    					if(m.moveKind == Move.STEP) 
		    		    {
		    			grid[m.x2][m.y2].color = color;
		    		    }	
    		    			grid[m.x1][m.y1].color = Color.SPACE;   
    					return true;
    				}
    				n = n.next();
    			}catch(InvalidNodeException e)
    			{
    				System.out.print(e);
    			}
    		}
    		if(m.moveKind == Move.STEP) 
		 {
			grid[m.x2][m.y2].color = color;
		 }	
    		grid[m.x1][m.y1].color = Color.SPACE;   
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

    protected boolean isValidMove(int color, Move m) {
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
        if ((color == Color.WHITE && (m.y1 == 0 || m.y1 == 7)) || (color == Color.BLACK && (m.x1 == 0 || m.x1 == 7))) {
            return false;
        }

        //rule1
        if (grid[m.x1][m.y1].color != Color.SPACE) {
            return false;
        }

        //rule4
        if (isConnected(color, m)) {
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


    public DList generateValidMove(int color) {

        DList l = new DList();
        //add if haveLeftChip()=true
        if (haveLeftChip(color)) {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    Move m = new Move(x, y);
                    if (isValidMove(color, m)) {
                        l.insertFront(m);
                    }
                }
            }
        }
        //step
        //if chip.color==color, x=chip.x, y..;
        
        
        try {
            ListNode n = generateGChipList(color).front();
            while (n.isValidNode()) {
                int x = ((Chip) n.item()).x,
                        y = ((Chip) n.item()).y;
                int i = 1;
                while (i >= -1) {
                    int j = 1;
                    while (j >= -1) {
                        Move m = new Move(x + i, y + j);
                        if (isValidMove(color, m)) {
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

    private boolean haveLeftChip(int color) {
        if (color == Color.BLACK) {
            return Chip.bChipCount < 10;
        } else {
            return Chip.wChipCount < 10;
        }
    }
    
    private DList generateGChipList(int color) {
    		DList l = new DList();
    		return l;
    }
    private boolean isGChip(Chip c,int color) {
    		return false;
    }
    private boolean isTurning(Chip x,Chip y)
    {
    		return false;
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
    		int color = MachinePlayer.checkColor(side);
    		ListNode n = generateGChipList(color).front();
    		try {
    			while(n.isValidNode()) {
        			if(!findPath(n,color)){
        				n = n.next();
        			}else {
        				return true;
        			}        			
        		}
    			return false;
    		}catch(InvalidNodeException e)
    		{ 
    			return false;
    		}
    }
    private boolean findPath(ListNode u,int color)
    {
    		try {
    			ListNode v = ((Chip)u.item()).findPair(this).front();
    			int count = 0;
        		while(v.isValidNode()) {
        			if(((Chip)v.item()).isVisited() && isTurning((Chip)u.item(),(Chip)v.item())){
        				count++;
        				((Chip)v.item()).marker();
        				if(!isGChip((Chip)v.item(),color)) {
        					findPath(v,color);
        				}else{
        					if(count>=6) {
        						return true;
        					}else {
        						v = v.next();
        					}
        				}
        			}
        			else {
        				v = v.next();
        			}      			
        		}
    			return false;
    		}catch(InvalidNodeException e) {
    			return false;
    		}
    		
    }
    private void printBoard()
    {
    		int count=0;
    		System.out.println("-----------------------------------------");
    		for(int i=0;i<8;i++){
    			for(int j=0;j<8;j++) {
    				System.out.print("|"+Color.toString(grid[j][i].color));
    				count++;
    				if(count%8 == 0)
    				{
    					System.out.println("| _"+i);
    					System.out.println("-----------------------------------------");
    				}
    			}
    		}
    		System.out.println("  0_   1_   2_   3_   4_   5_   6_   7_");
    }
    public void testIsValidMove()
    {
    	 	BufferedReader keyBoard =
    	          new BufferedReader(new InputStreamReader(System.in));

   		
        //set a Move        
        int color = Color.WHITE;
        System.out.println("the color of the new movement is: "+Color.toString(color));
        System.out.println("make a new movement");
        System.out.println("Valid commands are: " +
                "add, step, quit");
        System.out.print("-->");
        try {
        		String moveKind = keyBoard.readLine();
        		if(moveKind.equals("quit")){
        			Move m = new Move();
        			System.out.println("the movement is valid: "+isValidMove(color,m)); 	
        		}
            while(!moveKind.equals("quit")) {
            		if(moveKind.equals("add")) {
            			System.out.println("input position in which a chip is being added");
            			System.out.print("input x-coordinates index-->");
            			String x1 = keyBoard.readLine();
            			System.out.print("input y-coordinates index-->");
            			String y1 = keyBoard.readLine();
            			Move m = new Move(Integer.valueOf(x1).intValue(),Integer.valueOf(y1).intValue());
            			System.out.println(m.toString());
            			 //The move is or not valid
            	        System.out.println("the movement is valid: "+isValidMove(color,m));
            		}else if(moveKind.equals("step")) {
            			System.out.println("input position in which a chip is being stepped");
            			System.out.print("input x-coordinates index of new position-->");
            			String x1 = keyBoard.readLine();
            			System.out.print("input y-coordinates index of new position-->");
            			String y1 = keyBoard.readLine();
            			System.out.print("input x-coordinates index of old position-->");
            			String x2 = keyBoard.readLine();
            			System.out.print("input y-coordinates index of old position-->");
            			String y2 = keyBoard.readLine();
            			Move m = new Move(Integer.valueOf(x1).intValue(),Integer.valueOf(y1).intValue(),
            					Integer.valueOf(x2).intValue(),Integer.valueOf(y2).intValue());
            			System.out.println(m.toString());
            			 //The move is or not valid
            	        System.out.println("the movement is valid: "+isValidMove(color,m));          			
            		}else {
          			  System.err.println("Invalid move: "+moveKind );
            		}
            		 System.out.println("-->");
            		 moveKind = keyBoard.readLine();
            }
        }catch(IOException e) {
        		System.out.println(e);
        }
    		
    }
    public void testGenerateValidMove()
    {
    	
    }
    public static void main(String[] args) {
    	//set a Board
		System.out.println("start to set a Board");
		Board b = new Board();
	    b.grid[4][4].color = Color.WHITE;
	    b.grid[5][5].color = Color.BLACK;
	    b.grid[4][2].color = Color.WHITE;
	    b.grid[4][6].color = Color.BLACK;
	    b.grid[6][6].color = Color.WHITE;
	    b.grid[2][2].color = Color.BLACK;
	    /*
	    b.grid[6][4].color = Color.WHITE;
	    
	    
	    b.grid[6][2].color = Color.WHITE;
	    
	    b.grid[2][4].color = Color.WHITE;
	    b.grid[2][6].color = Color.WHITE;
	    */
	    b.printBoard();
    		b.testIsValidMove();
    }
}
