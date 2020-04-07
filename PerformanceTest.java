package dictionary;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.LinkedList;
import java.util.List;

public class PerformanceTest {

    static final int CREATE_N = 16000;

    public static void main(String[] args) throws IOException {

        System.out.println("Test with " + CREATE_N + " entities..");
        testSortedArrayDictionaray();
        testHashDictionary();
        testBinaryTreeDictionary();
    }


    private static void testSortedArrayDictionaray() throws IOException {
        Dictionary<String, String> dict = new SortedArrayDictionary<>();
        testDict(dict);
    }

    private static void testHashDictionary() throws IOException {
        Dictionary<String, String> dict = new HashDictionary<>();
        testDict(dict);
    }

    private static void testBinaryTreeDictionary() throws IOException {
        Dictionary<String, String> dict = new BinaryTreeDictionary<>();
        testDict(dict);
    }

    private static void testDict(Dictionary<String, String> dict) throws IOException {

        // ------------ test creation time ---------------
        long start = System.nanoTime();
        LineNumberReader in;
        in = new LineNumberReader(new FileReader("dtengl.txt"));
        String line;

        int counter = 0;
        while((line = in.readLine()) != null && counter < CREATE_N) {
            String[] wf = line.split(" ");
            dict.insert(wf[0], wf[1]);
            counter++;
        }
        long end = System.nanoTime();
        double elapsed = (double) (end -start) / 1.0e06;
        System.out.print("Elapsed insertion time for " + dict.getClass() + ": ");
        System.out.printf("%.2f", elapsed).print("ms\n");

        // ------------ test searching time ---------------
        in = new LineNumberReader(new FileReader("dtengl.txt"));
        List<String> germanWords = new LinkedList<>();
        while ((line = in.readLine()) != null) {
            String[] wf1 = line.split(" ");
            germanWords.add(wf1[0]);
        }
        start = System.nanoTime();
        for (String s: germanWords) {
            dict.search(s);
        }
        end = System.nanoTime();
        elapsed = (double) (end - start) / 1.0e06;
        System.out.print("Elapsed searching time for " + dict.getClass() + ": ");
        System.out.printf("%.2f", elapsed).print("ms\n");

        // ------------ test failed searching time ---------------
        in = new LineNumberReader(new FileReader("dtengl.txt"));
        List<String> englishWords = new LinkedList<>();
        while ((line = in.readLine()) != null) {
            String[] wf2 = line.split(" ");
            englishWords.add(wf2[1]);
        }
        start = System.nanoTime();
        for (String s: englishWords) {
            dict.search(s);
        }
        end = System.nanoTime();
        elapsed = (double) (end - start) / 1.0e06;
        System.out.print("Elapsed failed searching time for " + dict.getClass() + ": ");
        System.out.printf("%.2f", elapsed).print("ms\n");
        System.out.println();
    }
}
