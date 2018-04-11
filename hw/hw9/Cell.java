public class Cell {
    int x;
    int y;
    int id;

    public Cell(int x, int y,  int vert) {
        this.x = x;
        this.y = y;
        this.id = x * vert + y;
    }

    static public int getX(int id, int vert) {
        return id / vert;
    }

   static  public int getY(int id, int vert) {
        return id % vert;
    }
}
