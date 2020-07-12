import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int currentByte = reader.read();
        while (currentByte != -1) {
           // for (int i = 0; i < currentByte.)
            char character = (char) currentByte;
            System.out.print(character);
            currentByte = reader.read();
        }
        reader.close();
    }
}