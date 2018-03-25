package player;
import list.*;

/**
 *  Chip Class records Chips' information
 **/


public class Chip {

    public int color; // the value of color are stored in Color Class.
    public int x;
    public int y;
    public static int bChipCount = 0;//count the number of black chips used.
    public static int wChipCount = 0;//count the number of white chips used.



    
    public boolean equals(Chip c) {
        return c.color == color && c.x == x && c.y == y;

    }

    public Chip(int x, int y, int color){
        this.x = x;
        this.y = y;
        this.color = color;
	    		if(color == 0)
    		{
    			bChipCount++;
    		}
    		if(color == 1)
    		{
    			wChipCount++;
    		}
    }


    /**
     *  find the chips (of the same color) that form connections with a chip
     *  @param b is an Game board
     *  @return DList that stores chips that connect with a "this" chip.
     **/
    public DList findPair(Board b) {
        DList result = new DList();
        if(color == Color.SPACE) {
            return null;
        } else {
            for(int i = x + 1; i < 8; i++) {
                int temp = b.grid[i][y].color;
                if(temp == color) {
                    result.insertBack(new Chip(i, y , temp));
                    break;
                } else if(temp == Color.SPACE) {
                    continue;
                } else {
                    break;
                }
            }

            for(int i = x - 1; i >= 0; i--) {
                int temp = b.grid[i][y].color;
                if(temp == color) {
                    result.insertBack(new Chip(i, y , temp));
                    break;
                } else if(temp == Color.SPACE) {
                    continue;
                } else {
                    break;
                }
            }

            for(int j = y + 1; j < 8; j++) {
                int temp = b.grid[x][j].color;
                if(temp == color) {
                    result.insertBack(new Chip(x, j , temp));
                    break;
                } else if(temp == Color.SPACE) {
                    continue;
                } else {
                    break;
                }
            }

            for(int j = y - 1; j >= 0; j--) {
                int temp = b.grid[x][j].color;
                if(temp == color) {
                    result.insertBack(new Chip(x, j , temp));
                    break;
                } else if(temp == Color.SPACE) {
                    continue;
                } else {
                    break;
                }
            }

            for(int i = x + 1, j = y + 1; i < 8 && j < 8; i++, j++) {
                int temp = b.grid[i][j].color;
                if(temp == color) {
                    result.insertBack(new Chip(i, j, temp));
                    break;
                } else if(temp == Color.SPACE) {
                    continue;
                } else {
                    break;
                }
            }

            for(int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
                int temp = b.grid[i][j].color;
                if(temp == color) {
                    result.insertBack(new Chip(i, j, temp));
                    break;
                } else if(temp == Color.SPACE) {
                    continue;
                } else {
                    break;
                }
            }

            for(int i = x + 1, j = y - 1; i < 8 && j >= 0; i++, j--) {
                int temp = b.grid[i][j].color;
                if(temp == color) {
                    result.insertBack(new Chip(i, j, temp));
                    break;
                } else if(temp == Color.SPACE) {
                    continue;
                } else {
                    break;
                }
            }

            for(int i = x - 1, j = y + 1; i >= 0 && j < 8; i--, j++) {
                int temp = b.grid[i][j].color;
                if(temp == color) {
                    result.insertBack(new Chip(i, j, temp));
                    break;
                } else if(temp == Color.SPACE) {
                    continue;
                } else {
                    break;
                }
            }
        }
        return result;
    }

    public String toString(){
        String s;
        s = "( " + x + " , " + y + " )";
        return s;
    }
    public static void main(String[] args) {
        Board b = new Board();
        b.grid[4][4].color = Color.WHITE;
        b.grid[5][5].color = Color.BLACK;
        b.grid[4][2].color = Color.WHITE;
        b.grid[4][6].color = Color.WHITE;
        b.grid[2][2].color = Color.WHITE;
        b.grid[6][6].color = Color.WHITE;
        b.grid[6][2].color = Color.WHITE;
        b.grid[6][4].color = Color.WHITE;
        b.grid[2][4].color = Color.WHITE;
        b.grid[2][6].color = Color.WHITE;
        DList l = b.grid[4][4].findPair(b);
        System.out.println(l.length());
    }

}
