import java.io.*;

public class Nuke2 {
    public static void main(String[] arg) throws Exception {
        BufferedReader keyboard;
        String line;

        keyboard = new BufferedReader(new InputStreamReader(System.in));
        line = keyboard.readLine();
        for (int i = 0; i < line.length(); i++){
            if(i != 1){
                System.out.print(line.charAt(i));
            }
        }
    }
}
