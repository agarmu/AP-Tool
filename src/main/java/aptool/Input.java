package aptool;

import java.util.Scanner;
public class Input {
    public static String input(String prompt) {
    	
        Scanner input = new Scanner(System.in);
    	
        System.out.print(prompt);
        String value = input.next();
        input.close();
        return value;
    }
}
