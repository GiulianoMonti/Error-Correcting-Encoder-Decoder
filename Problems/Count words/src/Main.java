import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String text = reader.readLine().trim();
        List<String> words = new ArrayList<>(Arrays.asList(text.split(" ")));
        words.removeAll(Arrays.asList(" ", "", null));
        System.out.println(words.size());
        reader.close();
    }
}