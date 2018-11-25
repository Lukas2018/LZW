import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class LzwCompression {
    String inputText = " ";
    private HashMap<String, Integer> dictionary;
    private List<Integer> results;
    private int dictionarySize = 256;

    public void compress() {
        init();
        lookTheWord();
        printResults();
        try {
            saveToFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        dictionary = new HashMap<>();
        results = new ArrayList<>();
        for (int i = 0; i < dictionarySize; i++)
            dictionary.put("" + (char) i, i);
    }

    private void lookTheWord() {
        String word = "";
        for (char c : inputText.toCharArray()) {
            String wordCode = word + c;
            if (dictionary.containsKey(wordCode))
                word = wordCode;
            else {
                results.add(dictionary.get(word));
                dictionary.put(wordCode, dictionarySize++);
                word = "" + c;
            }
        }
        if (!isStringEmpty(word))
            results.add(dictionary.get(word));

    }

    private boolean isStringEmpty(String text) {
        return text.equals("");
    }

    private void printResults() {
        for (int i = 0; i < results.size(); i++) {
            System.out.print("[" + results.get(i) + "] ");
        }
        System.out.println();
    }

    public void readFromFile(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner in = new Scanner(file);
        this.inputText = in.useDelimiter("\\A").next();
        in.close();
    }

    public void saveToFile() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter("result.txt");
        for (int i = 0; i < results.size(); i++)
            pw.print(results.get(i) + " ");
        pw.close();
    }

    public static void main(String[] args) {
        LzwCompression lzw = new LzwCompression();
        try {
            lzw.readFromFile(FileFrame.chooseFileAndGetPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        lzw.compress();
    }
}
