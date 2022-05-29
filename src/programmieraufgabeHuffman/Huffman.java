package programmieraufgabeHuffman;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {

    public static void main(String[] args) throws Exception {

        // task 1: read file as string
        String text = programmieraufgabeHuffman.FileReaderWriter.readFileAsString("text-test.txt");
        System.out.println("Text in file: " + text);

        // task 2: create table for each ASCII code and the occurrence
//        createTable(text);

        String encodedText = createEncoding(text);
        System.out.println(encodedText);

//        int[] count = new int[256];
//        int len = text.length();

        // Initialize count array index
//        for (int i = 0; i < len; i++)
//            count[text.charAt(i)]++;

        // Create an array of given String size
//        char[] chars = new char[text.length()];
//        for (int i = 0; i < len; i++) {
//            chars[i] = text.charAt(i);
//            int occ = 0;
//            for (int j = 0; j <= i; j++) {
//                if (text.charAt(i) == chars[j]) {
//                    occ++;
//                }
//            }
//            if (occ == 1) {
//                System.out.println("Occ of " + text.charAt(i) + " = " + count[text.charAt(i)]);
//            }
//        }
    }

    public static String createEncoding(String text) {
        String encodedText = "";
        // create map for ASCII character and occurrence
        Map<Character, Integer> occ = new HashMap<>();
        for (int i=0; i<text.length(); i++) {
            char currChar = text.charAt(i);
            // check and save the occurrence of each ASCII character
            if (!occ.containsKey(text.charAt(i))) { // add initial value of 0 for all chars
                occ.put(currChar, 0);
            }
            occ.put(currChar, occ.get(currChar)+1); // add count
        }
        System.out.println("Hashmap of occurrence: " + occ);

        // create priority queue to later process each leaf in the tree from lowest to highest occurrence
        PriorityQueue<Leaf> queue = new PriorityQueue<Leaf>();

        // create a 'tree leaf' for each character
        for (Character character : occ.keySet()) {
            Leaf leaf = new Leaf(character, occ.get(character));
            queue.add(leaf);
        }
        System.out.println("Current queue: " + queue);

        // todo: find the two characters with the lowest occurrences and add their occurrences together

        // todo: make a parent leaf for those two characters, remove them from the queue

        // todo: find the next character with the lowest occurrence and add it, then remove it from the queue

        // todo: create codes according to the 'tree'
        // (start with 1, add 0s until length of Bitstring is a multiple of 8)
        // create byte Array
        // save byte Array (output.dat)

        return encodedText;
    }

    public static void createTable(String text) {
        int[][] table = new int[128][2];
        for (int i=0; i<table.length; i++) {
            table[i][0]= i;
            table[i][1]= countOccurrence(i, text);
        }

        // print whole table
        for (int i=0; i<table.length; i++) {
            System.out.println(table[i][0] + ": " + table[i][1]);
        }
        // todo: remove if not needed
        System.out.println(Arrays.deepToString(table).replace("], ", "]\n"));

    }

    public static int countOccurrence(int asciiCode, String text) {
        int len = text.length();
        int occ = 0;

        for (int i = 0; i < len; i++) {
            if (text.charAt(i) == asciiCode) {
                occ++;
            }
        }
        return occ;
    }

}
