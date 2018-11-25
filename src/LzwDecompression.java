import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class LzwDecompression {
    private List<Integer> inputIndexes = new ArrayList<>();
    private HashMap<Integer, String> dictionary;
    private List<String> results;
    private int dictionarySize = 256;

    public void decompress() {
        init();
        lookTheIndexes();
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
            dictionary.put(i, "" + (char) i);
    }

    private void lookTheIndexes() {
        int previous;
        int current = inputIndexes.get(0);
        results.add(dictionary.get(current));
        for (int i = 1; i < inputIndexes.size(); i++) {
            previous = current;
            current = inputIndexes.get(i);
            if (dictionary.containsKey(current)) {
                String text = dictionary.get(current);
                results.add(text);
                dictionary.put(dictionarySize++, dictionary.get(previous) + text.charAt(0));
            } else {
                String text = dictionary.get(previous) + dictionary.get(previous).charAt(0);
                results.add(text);
                dictionary.put(dictionarySize++, text);
            }
        }
    }

    private void printResults() {
        for (int i = 0; i < results.size(); i++) {
            System.out.print(results.get(i));
        }
    }

    public void readFromFile(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner in = new Scanner(file);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            StringTokenizer token = new StringTokenizer(line, " ");
            while (token.hasMoreTokens())
                inputIndexes.add(Integer.parseInt(token.nextToken()));
        }
    }


    public void saveToFile() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter("resultt.txt");
        for (int i = 0; i < results.size(); i++)
            pw.print(results.get(i));
        pw.close();
    }

    public static void main(String[] args) {
        LzwDecompression lzw = new LzwDecompression();
        try {
            lzw.readFromFile(FileFrame.chooseFileAndGetPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        lzw.decompress();
    }
}
