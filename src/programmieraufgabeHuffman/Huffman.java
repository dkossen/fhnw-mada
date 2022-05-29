package programmieraufgabeHuffman;

import java.math.BigInteger;
import java.util.Arrays;

public class Huffman {

    public static void main(String[] args) throws Exception {

        // task 1: read file as string
        String text = programmieraufgabeHuffman.FileReaderWriter.readFileAsString("text-test.txt");
        System.out.println("Text in file: " + text);

        // task 2: create table for each ASCII code and the occurrence
        createTable(text);

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


    public static BigInteger[] convertStringToASCII(String text) {
        int l = text.length();
        BigInteger[] data = new BigInteger[l];
        for(int i = 0; i < l; ++i) {
            char character = text.charAt(i);
            data[i] = new BigInteger(String.valueOf((int)character));
        }
        return data;
    }

    public static String convertASCIIToString(String text) {
        String[] array = text.split(";");
        String convertedText = "";
        for(String value:array) {
            char c = (char) Integer.parseInt(value);
            convertedText += c;
        }
        return convertedText;
    }

    public static String removeSemicolonTail(String str) {
        return str.replaceAll(";$", "");
    }

}
