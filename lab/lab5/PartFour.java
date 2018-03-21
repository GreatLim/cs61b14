class Superclass4{
    public void printMethod(int i){
        System.out.println(i);
    }
}

class Subclass4 extends Superclass4{
    public void printMethod(int i){
        System.out.println(i+1);
    }
    public static void main(String arg[]){
        Subclass4 subclassvariable = new Subclass4();
        //((Superclass4) subclassvariable).printMethod(4);
        Superclass4 superclassvariable = new Superclass4();
        //((Subclass4) superclassvariable).printMethod(4);
    }
}