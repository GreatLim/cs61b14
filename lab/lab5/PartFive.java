class human {
    int age;
    String sex;
    int height;

    public human(){
    }

    public human(int age, String sex, int height){
        this.age = age;
        this.sex = sex;
        this.height = height;
    }

    public int getAge(){
        return age;
    }
    public String getSex(){
        return sex;
    }
    public int getHeight(){
        return height;
    }
    public void speak(){
        System.out.println("I'm a human");
    }
}

class man extends human{
    final String sex = "male";
    public man(int age, int height){
        this.age = age;
        this.height = height;
    }

    public String getSex(){
        return sex;
    }

    public void speak(){
        //super.speak();
        System.out.println("I'm a man");
    }
    public void special(){
        System.out.println("I can not be pregnant");
    }
}

class woman extends human{
    public woman(int age, int height){
        super(age, "female", height);
    }
    public void speak(){
        System.out.println("I'm a woman");
    }
    public void special(){
        System.out.println("I can be pregnant");
    }
}

class doctor{
    public static void test(human n){
        System.out.println("My age is " + n.getAge());
        System.out.println("My sex is " + n.getSex());
        System.out.println("My height is " + n.getHeight()+"cm");
        System.out.print("I can say that ");
        n.speak();
    }
    public static void main(String args[]){
        test(new man(1,2));
    }
}

public class PartFive {
    private PartFive(){
        System.out.println("PartFive");
        System.out.println("========");

    }
    public static void main(String args[]){
        //doctor doctor = new doctor();
        human man = new man(18, 175);
        woman woman = new woman(21, 160);
        human[] human = new human[2];
        Object[] object = new human[2];
        human[0] = man;
        human[1] = woman;
        object[0] = man;
        object[1] = woman;
        //object[0].speak(); can't compile
        PartFive PartFive = new PartFive();
        System.out.println("*** test speak() ***\n");
        System.out.print("man.speak(): ");
        man.speak();
        System.out.print("woman.speak(): ");
        woman.speak();
        System.out.print("human[0].speak(): ");
        human[0].speak();
        System.out.print("human[1].speak(): ");
        human[1].speak();
        System.out.print("((human) object[0]).speak(): ");
        ((human) object[0]).speak();
        //object[0].speak(); can't compile

        System.out.println("\n\n*** test getClass() ***\n");
        System.out.println("human[0]'s class " + human[0].getClass());
        System.out.println("human[1]'s class " + human[1].getClass());
        System.out.println("object[0]'s class " + object[0].getClass());
        System.out.println("object[1]'s class " + object[1].getClass());
        System.out.println("man's class " + man.getClass());
        System.out.println("woman's class " + woman.getClass());
        System.out.println("\n\n*** test instanceof() ***\n");
        System.out.println("human[0] instanceof human: " + (human[0] instanceof human));
        System.out.println("human[0] instanceof man: " + (human[0] instanceof man));
        System.out.println("man instanceof human: " + (man instanceof human));
        System.out.println("man instanceof human: " + (man instanceof human));
        System.out.println("object[0] instanceof human: " + (object[0] instanceof human));
        System.out.println("object[0] instanceof man: " + (object[0] instanceof man));
        System.out.println("object[0] instanceof woman: " + (object[0] instanceof woman));

        System.out.println("\n\n*** test test() ***\n");
        System.out.println("test(human[0]): " );
        doctor.test(human[0]);
        //man.special(); can't find special, since we use human as reference instance.
    }
}