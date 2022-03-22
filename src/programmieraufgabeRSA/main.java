package programmieraufgabeRSA;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

public class main {

    public static void main(String[] args) throws Exception {
        generateKeyPair(); // Task 1
        encryptString("text.txt");   // Task 2
//        decryptString("chiffre.txt");   // Task 3
    }

    public static void generateKeyPair() throws IOException {
        // create two random prime numbers
//        BigInteger p = BigInteger.probablePrime(48, new Random());
//        BigInteger q = BigInteger.probablePrime(48, new Random());

        // values need to be big enough so that n is bigger than the values that need to be en-/decoded
        BigInteger p = BigInteger.valueOf(3);
        BigInteger q = BigInteger.valueOf(11);

        // set a new 2nd prime if the prime numbers are the same
        while (p.equals(q)) {
            q = BigInteger.probablePrime(5, new Random());
        }

        // n = p*q
        BigInteger n = p.multiply(q);

        // phiN = (p-1)*(q-1)
        BigInteger phiN = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // chose a number that isn't in Z*(n)
        BigInteger e = p;

        // d: e*d mod phiN == 1
        BigInteger d = euklid(phiN, e);
        if (d.compareTo(BigInteger.ZERO) < 0) {
            d = d.add(phiN);
        }

        System.out.println("p: " + p + ", q:" + q + ", n: " + n + ", phiN: " + phiN + ", e: " + e + ", d: " + d);

        // write public and private key into text file
        FileReaderWriter.writeFile("sk.txt", n + "," + d);
        FileReaderWriter.writeFile("pk.txt", n + "," + e);
    }

    public static void encryptString(String file) throws Exception {
        // read file as string
        String text = FileReaderWriter.readFileAsString(file);
        System.out.println("Text to encrypt: " + text);

        // convert string to ASCII
        BigInteger[] convertedText = convertToASCII(text);
//        byte[] convertedText = convertToASCII(text);
        String convertedTextAsString = "";
        for (int j = 0; j < convertedText.length; j++){
            convertedTextAsString += convertedText[j] + ", ";
        }
        System.out.println("Text in ASCII: " + convertedTextAsString);

        // read public key
        String publicKey = FileReaderWriter.readFileAsString("pk.txt");
        BigInteger n = new BigInteger(publicKey.substring(0, publicKey.indexOf(",")));
        BigInteger e = new BigInteger(publicKey.substring(publicKey.indexOf(",")+1));
        System.out.println(n);
        System.out.println(e);

        // encrypt each character
        String encryptedText = "";
        for (int i = 0; i < convertedText.length; i++) {
            encryptedText += fastModularExponentiation(convertedText[i], n, e) + ", ";
        }

        // remove last comma from string
        encryptedText = removeCommaTail(encryptedText);
        System.out.println("Encrypted text: " + encryptedText);

        // write encrypted text into text file
        FileReaderWriter.writeFile("chiffre.txt", encryptedText);
    }

    public static void decryptString(String file) throws Exception {
        // read file as string
        String text = FileReaderWriter.readFileAsString(file);
        System.out.println("Text to decrypt: " + text);

        // convert text to integer array
//        int[] decryptedTextAsArray = Arrays.stream(text.split(", ")).mapToInt(Integer::parseInt).toArray();
//        BigInteger[] decryptedTextAsArray = Arrays.stream(text.split(", ")).mapTo(Integer::parseInt).toArray();

        int l = text.length();
        System.out.println("length: "+ l);
        BigInteger[] decryptedTextAsArray = new BigInteger[l];
        for(int i = 0; i < l; ++i) {
            decryptedTextAsArray[i] = BigInteger.valueOf(text.charAt(i)-'0');
        }
        System.out.println(decryptedTextAsArray[1]);

        // read private key
        String privateKey = FileReaderWriter.readFileAsString("sk.txt");
//        int n = Integer.parseInt(privateKey.substring(0, privateKey.indexOf(",")));
//        int d = Integer.parseInt(privateKey.substring(privateKey.indexOf(",")+1));
        BigInteger n = new BigInteger(privateKey.substring(0, privateKey.indexOf(",")));
        BigInteger d = new BigInteger(privateKey.substring(privateKey.indexOf(",")+1));
        System.out.println("n: " + n);
        System.out.println("d: " + d);

        // decrypt each element in the array
        String decryptedTextASCII = "";
        String decryptedTextString = "";
        for (int i = 0; i < decryptedTextAsArray.length; i++) {
//            decryptedTextString += (fastModularExponentiation(decryptedTextAsArray[i], n, d));
//            decryptedTextString += Character.toString((char)fastModularExponentiation(decryptedTextAsArray[i], n, d));
            decryptedTextASCII += fastModularExponentiation(decryptedTextAsArray[i], n, d) + ", ";
        }

        // remove last comma from string
        decryptedTextASCII = removeCommaTail(decryptedTextASCII);

        System.out.println("Decrypted text in ASCII: " + decryptedTextASCII);
        System.out.println("Decrypted text as String: " + decryptedTextString);
    }

    public static BigInteger euklid(BigInteger a, BigInteger b){
        if (b.compareTo(a) > 0) {
            BigInteger t = a; a = b; b = t;
        }
        BigInteger q, r, x0temp, y0temp;
        BigInteger x0 = BigInteger.ONE, y0 = BigInteger.ZERO, x1 = BigInteger.ZERO, y1 = BigInteger.ONE;
        while (b.compareTo(BigInteger.ZERO) != 0) {
            q = (a.divide(b)); r = (a.mod(b));
            a = b; b = r;
            x0temp = x0; y0temp = y0;
            x0 = x1; y0 = y1;
            x1 = x0temp.subtract(q.multiply(x1)); y1 = y0temp.subtract(q.multiply(y1));
        }
        return y0;
    }

    public static BigInteger fastModularExponentiation(BigInteger x, BigInteger n, BigInteger e) {
        String exponentAsBinary = e.toString(2);
//        System.out.println("exponentAsBinary: " + exponentAsBinary);
        int i = exponentAsBinary.length()-1;
        BigInteger h = BigInteger.ONE, k = x;

        while (i >= 0) {
            if (Integer.parseInt(String.valueOf(exponentAsBinary.charAt(i))) == 1) {
                h = (h.multiply(k)).mod(n);
            }
            k = (k.multiply(k)).mod(n);
            i -= 1;
        }

        return h;
    }

    public static BigInteger[] convertToASCII(String text) { //Mada
        int l = text.length();
        System.out.println("length: " + l);
        BigInteger[] data = new BigInteger[l];
        for(int i = 0; i < l; ++i) {
            // todo convert letter into ASCII value --> how?
            System.out.println("text.charAt(i)-0: " + (text.charAt(i)));
//            data[i] = (text.charAt(i));
            data[i] = BigInteger.valueOf( text.charAt(i)-'0' );
//            System.out.println("data: " + data[i]);
        }
        return data;
    }

//    public static byte[] convertToASCII(String text) {
//        return text.getBytes(StandardCharsets.US_ASCII);
//    }

    public static String removeCommaTail(String str) {
        return str.replaceAll(", $", "");
    }

}
