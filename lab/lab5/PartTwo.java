interface Printable {
    //public void printMethod();
    //public int printMethod();
    public void printMethod(int i);
}

class Superclass{
    public void printMethod(int i){
    }
}

class Subclass extends Superclass implements Printable {
}