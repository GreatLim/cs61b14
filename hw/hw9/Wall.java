public class Wall {
    final static boolean H = true;
    final static boolean V = false;
    public int x;
    public int y;
    public boolean direction;
    public Wall(int x, int y, boolean direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    public String toString() {
        String str ="( ";
        str += x + " ";
        str += y + " ";
        if(direction ) {
            str += "H )";
        } else {
            str += "V )";
        }
        return str;
    }
}
