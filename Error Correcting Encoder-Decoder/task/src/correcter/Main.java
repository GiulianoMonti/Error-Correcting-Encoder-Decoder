package correcter;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();

        StringBuilder textWithErrors = new StringBuilder();

        String characters = " ABCDEFGHIJKLMNOQPRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        System.out.println(text);
        for (int i = 0; i < text.length();i++){
            textWithErrors.append(text.charAt(i));
            textWithErrors.append(text.charAt(i));
            textWithErrors.append(text.charAt(i));
        }
        System.out.println(textWithErrors);

        Random random = new Random();
        int index;
        for (int i = 1; i < textWithErrors.length() + 1; i++) {
            if (i % 3 == 0) {
                index = random.nextInt(characters.length());
                textWithErrors.replace(i - 1, i, String.valueOf(characters.charAt(index)));
            }
        }
        System.out.println(textWithErrors);
        System.out.println(text);
    }
}
