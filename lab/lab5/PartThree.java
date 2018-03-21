interface Printable3 {
    //public void printMethod();
    //public int printMethod();
    public void printMethod(int i);
    public static final String str = "hello";
}

class Superclass3{
    public static final String str = "hellos";
    public void printMethod(int i){
    }
}

class Subclass3 extends Superclass3 implements Printable3 {
    public static void main(String arg[]){
        System.out.println(Superclass3.str + Printable3.str);
    }
}
