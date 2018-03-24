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

    //suppose Move= Add or Step only, not quit
    	    protected Board setBoard(Move m,int score,int side)
    	    {
    	    		Chip c = new Chip(m.x1,m.y1,side);
    	    	    if(m.moveKind == Move.STEP) 
    			{
    				grid[m.x2][m.y2].color = -1;
    				Chip c2 = new Chip(m.x2,m.y2, side);			
    				try {
    					ListNode n = ChipList.front();
    					while(n.isValidNode())
    					{
    						if(c2.equals((Chip)n.item()))
    								{n.remove();}
    						n = n.next();
    					}
    					
    				}catch(InvalidNodeException e)  {
    					System.out.println(e);
    				}
    				
    			}
    			grid[m.x1][m.y1].color = side;
    			ChipList.insertBack(c);
    	    		return this;
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
    
    //side have the same definition of color; side = 0 for b; side = 1 for w
    protected boolean isValidMove(int side, Move m) 
    {
    		// is the moveKind is not Valid the method is undefined
    		//Quit is always valid;
    		if(m.moveKind == Move.QUIT) 
		{
			return true;
		}
    		//rule3
    		if((m.x1==0 && m.y1==0)||(m.x1==0 && m.y1==7)||(m.x1==7 && m.y1==0)||(m.x1==7 && m.y1==7))
    		{
    			return false;
    		}
    		
    		//rule2
    		if((side==1 && (m.y1==0 || m.y1==7))||(side==0 && (m.x1==0 || m.x1==7)))
    		{
    			return false;
    		}
    		
    		//rule1
    		if(grid[m.x1][m.y1].color!=-1)
    		{
    			return false;
    		}
    		
    		//rule4
    		if(isConnected(side,m)){
    			return false;
    		}else {
    			return true;
    		}
    		
    }
    
    /**
     *  Be used in isValidMove() Test if a player have more than two chips in a connected group, whether
      connected orthogonally or diagonally.
     * @param side
     * @param m
     * @return true if move "m" of player "side" makes three chips in a connected group in "this" GameBoard; otherwise,
     * false
     */
    //forget to move back
    private boolean isConnected(int side, Move m)
    {
    	//moveKind is supposed to be STEP or ADD
    		if(m.moveKind == Move.STEP) 
    		{
    			grid[m.x2][m.y2].color = -1;
    		}
    		grid[m.x1][m.y1].color = side;   		
    		DList l1 = grid[m.x1][m.y1].findPair(this);//the chip itself should not be return by findPair() 
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
		    			grid[m.x2][m.y2].color = side;
		    		    }	
    		    			grid[m.x1][m.y1].color = -1;   
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
			grid[m.x2][m.y2].color = side;
		 }	
    		grid[m.x1][m.y1].color = -1;   
    		return false;
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

    protected boolean isValidMove(int side, Move m) 
    {
    		// is the moveKind is not Valid the method is undefined
    		//Quit is always valid;
    		if(m.moveKind == Move.QUIT) 
		{
			return true;
		}
    		//rule3
    		if((m.x1==0 && m.y1==0)||(m.x1==0 && m.y1==7)||(m.x1==7 && m.y1==0)||(m.x1==7 && m.y1==7))
    		{
    			return false;
    		}
    		
    		//rule2
    		if((side==1 && (m.y1==0 || m.y1==7))||(side==0 && (m.x1==0 || m.x1==7)))
    		{
    			return false;
    		}
    		
    		//rule1
    		if(grid[m.x1][m.y1].color!=-1)
    		{
    			return false;
    		}
    		
    		//rule4
    		if(isConnected(side,m)){
    			return false;
    		}else {
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


   public DList generateValidMove(int side){
        
    		DList l = new DList();
    		//add if haveLeftChip()=true
    		if(haveLeftChip(side))
    		{
    			for(int x=0;x<8;x++)
    	        {
    	        		for(int y=0;y<8;y++)
    	        		{
    	        			Move m = new Move(x,y);
    	        			if(isValidMove(side,m))
    	        			{
    	        				l.insertFront(m);
    	        			}
    	        		}
    	        }
    		}  		
    		//step
    		//if chip.color==side, x=chip.x, y..;
    		
    		
    		try {
				ListNode n = ChipList.front();
				while(n.isValidNode())
				{
					int x = ((Chip)n.item()).x,
						y = ((Chip)n.item()).y;
					int i=1;
			    		while(i>=-1)
			    		{
			    			int j=1;
			    			while(j>=-1)
			    			{
			    				Move m = new Move(x+i,y+j);
			    				if(isValidMove(side,m))
			        			{
			        				l.insertFront(m);
			        			}
			    				j=j-2;
			    			}
			    			i=i-2;
			    		}
					
					n = n.next();
				}
				
			}catch(InvalidNodeException e)  {
				System.out.println(e);
			}
    		
    		return l;
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
