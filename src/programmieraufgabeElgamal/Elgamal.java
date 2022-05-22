package programmieraufgabeElgamal;

import java.math.BigInteger;
import java.util.Random;

public class Elgamal {

    public static void main(String[] args) throws Exception {

        BigInteger n, g, b, h;

        n = convertHexToBigInteger("hexString.txt"); // task 1
        g = new BigInteger("2");
        b = randomBigInteger(n);
        h = g.modPow(b, n);

//        System.out.println("n: " + n);
//        System.out.println("g: " + g);
//        System.out.println("b: " + b);
//        System.out.println("g^b: " + h);
//        System.out.println("Private key: (" + n + ", " + g + ", " + b + ")");
//        System.out.println("Public key: (" + n + ", " + g + ", " + h + ")");

        // task 2: write third part of public and private key into text file
        FileReaderWriter.writeFile("pk.txt", h.toString()); // public
        FileReaderWriter.writeFile("sk.txt", b.toString()); // private

        encryptString("text.txt", "pk.txt", "chiffre.txt", n, g, b); // task 3
        decryptString("chiffre.txt", "sk.txt", "text-d.txt", n); // task 4

    }

    public static void decryptString(String file, String privateKeyFile, String resultFile, BigInteger n) throws Exception {
        // read file as string
        String text = FileReaderWriter.readFileAsString(file);
        System.out.println("Text to decrypt: " + text);

        // split chiffre text into pieces
        String[] array = text.split(";");

        // read private key
        BigInteger b = new BigInteger(FileReaderWriter.readFileAsString(privateKeyFile));

        // actual decryption for each (y1, y2)-pair
        String decryptedTextASCII = "";
        for(String arr : array) {
            arr = arr.replaceAll("[()]", "");

            // read values
            BigInteger y1 = new BigInteger(arr.substring(0, arr.indexOf(",")));
            BigInteger y2 = new BigInteger(arr.substring(arr.indexOf(",")+1));

            // do calculations
            BigInteger part1 = y1.modPow(b, n).modInverse(n);
            BigInteger message = y2.multiply(part1).mod(n);

            decryptedTextASCII += (message + ";");
        }

        // convert ASCII to String
        String decryptedTextString = convertASCIIToString(decryptedTextASCII);

        // remove last comma from string for readability
        decryptedTextASCII = removeSemicolonTail(decryptedTextASCII);

        System.out.println("Decrypted text in ASCII: " + decryptedTextASCII);
        System.out.println("Decrypted text as String: " + decryptedTextString);
        FileReaderWriter.writeFile(resultFile, decryptedTextString);

    }

    public static void encryptString(String file, String publicKeyFile, String resultFile, BigInteger n, BigInteger g, BigInteger b) throws Exception {
        // read file as string
        String text = FileReaderWriter.readFileAsString(file);
        System.out.println("Text to encrypt: " + text);

        // convert string to ASCII
        BigInteger[] convertedText = convertStringToASCII(text);
        String convertedTextAsString = "";
        for (BigInteger integer : convertedText) {
            convertedTextAsString += integer + ",";
        }

        // read public key
        String publicKey = FileReaderWriter.readFileAsString(publicKeyFile);

        // generator a random BigInteger in range
        BigInteger a = randomBigInteger(n);

        // encrypt each character
        String encryptedText = "";
        for (BigInteger bigInteger : convertedText) {
            encryptedText += elgamalAlg(n, g, a, b, bigInteger) + ";";
        }

        // remove last comma from string
        encryptedText = removeSemicolonTail(encryptedText);
        System.out.println("Encrypted text: " + encryptedText);

        // write encrypted text into text file
        FileReaderWriter.writeFile(resultFile, encryptedText);
    }

    public static String elgamalAlg(BigInteger n, BigInteger g, BigInteger a, BigInteger b, BigInteger x) {
        // do calculations: (g^a, (g^b)^a ° x)
        String res = "";
        BigInteger gPowA = g.modPow(a, n);
        BigInteger gPowB = g.modPow(b, n);
        BigInteger gPowBpowA = g.modPow(b.multiply(a), n);
        BigInteger concat = gPowBpowA.multiply(x).mod(n);
        return "(" + gPowA + "," + concat + ")";
    }

    // adapted from java2s.com
    public static BigInteger randomBigInteger(BigInteger n) {
        BigInteger min = BigInteger.TWO;
        BigInteger max = n.subtract(BigInteger.TWO);
        BigInteger bigInt1 = max.subtract(min);
        Random rnd = new Random();
        int maxLength = max.bitLength();
        BigInteger b;
        b = new BigInteger(maxLength, rnd);
        if (b.compareTo(min) < 0) {
            b = b.add(min);
        }
        if (b.compareTo(max) >= 0) {
            b = b.mod(bigInt1).add(min);
        }
        return b;
    }

    public static BigInteger convertHexToBigInteger(String file) throws Exception {
        // read file as string
        String hexString = FileReaderWriter.readFileAsString(file);
        // replace all spaces and line breaks
        hexString = hexString.replace(" ", "").replace("\n", "").replace("\r", "");
        // convert to BigInteger
        return new BigInteger(hexString, 16);
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
            char c = (char)(int)Integer.parseInt(value);
            convertedText += c;
        }
        return convertedText;
    }

    public static String removeSemicolonTail(String str) {
        return str.replaceAll(";$", "");
    }

}
