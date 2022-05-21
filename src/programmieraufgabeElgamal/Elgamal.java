package programmieraufgabeElgamal;

import java.math.BigInteger;
import java.util.Random;

public class Elgamal {

    public static void main(String[] args) throws Exception {

        BigInteger n, g, b, h;

        n = convertHexToBigInteger("hexStringSmall.txt"); // task 1
        g = new BigInteger("2");
        b = randomBigInteger(n);
        h = g.modPow(b, n);

        System.out.println("n: " + n);
        System.out.println("g: " + g);
        System.out.println("b: " + b);
        System.out.println("g^b: " + h);
        System.out.println("Private key: (" + n + ", " + g + ", " + b + ")");
        System.out.println("Public key: (" + n + ", " + g + ", " + h + ")");

        // task 2: write third part of public and private key into text file
        FileReaderWriter.writeFile("pk.txt", h.toString()); // public
        FileReaderWriter.writeFile("sk.txt", b.toString()); // private

        encryptString("text.txt", "pk.txt", "chiffre.txt", n, g, b); // task 3
        decryptString("chiffre.txt", "sk.txt", "text-d.txt");


//        generateKeyPair(2048); // Task 1
//        encryptString("text.txt", "pk.txt", "chiffre.txt"); // Task 2
//        decryptString("chiffre.txt", "sk.txt", "text-d.txt"); // Task 3
//        decryptString("chiffre_task4.txt", "sk_task4.txt", "text-d_task4.txt"); // Task 4
    }

    public static void decryptString(String file, String privateKeyFile, String resultFile) throws Exception {
        // read file as string
        String text = FileReaderWriter.readFileAsString(file);
        System.out.println("Text to decrypt: " + text);

        int l = text.length();
        BigInteger[] decryptedTextAsArray = new BigInteger[l];
        String[] array = text.split(";");
        System.out.println(array[0]);
        int index = 0;
        for(String value:array) {
            array[index] = array[index].replaceAll("[()]", "");
            index++;
        }
//        BigInteger y1 = new BigInteger(array[0].substring(0, array[0].indexOf(",")));
//        BigInteger y2 = new BigInteger(array[0].substring(array[0].indexOf(",")+1));
//        System.out.println("y1: " + y1 + " y2: " + y2);

        // read private key
        String b = FileReaderWriter.readFileAsString(privateKeyFile);

        // decrypt each element in the array
        String decryptedText = "";
        //todo
//        for (int i : array) {
//            BigInteger y1 = new BigInteger(array[0].substring(0, array[0].indexOf(",")));
//            BigInteger y2 = new BigInteger(array[0].substring(array[0].indexOf(",")+1));
//            System.out.println("y1: " + y1 + " y2: " + y2);
//            decryptedText += (y1.toString() + y2.toString());
//            System.out.println(decryptedText);
//        }


        String decryptedTextASCII = "";
        for (BigInteger bigInteger : decryptedTextAsArray) {
            if (bigInteger != null) {
                decryptedTextASCII += ";";
            }
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
        BigInteger[] convertedText = convertToASCII(text);
        String convertedTextAsString = "";
        for (BigInteger integer : convertedText) {
            convertedTextAsString += integer + ",";
        }
        System.out.println("Text in ASCII: " + convertedTextAsString);

        // read public key
        String publicKey = FileReaderWriter.readFileAsString(publicKeyFile);
//        publicKey = publicKey.replace("(", "").replace(")", "");
//        BigInteger n = new BigInteger(publicKey.substring(0, publicKey.indexOf(",")));
//        BigInteger e = new BigInteger(publicKey.substring(publicKey.indexOf(",")+1));

        BigInteger a = randomBigInteger(n);
        System.out.println("a: " + a);

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
        // (g^a, (g^b)^a ° x)
        String res = "";
        BigInteger gPowA = g.modPow(a, n);
        BigInteger gPowB = g.modPow(b, n);
        BigInteger gPowBpowA = g.modPow(b.multiply(a), n);
        System.out.println("(g^b)^a: " + gPowBpowA);
        BigInteger concat = gPowBpowA.multiply(x).mod(n);
        System.out.println("concat: " + concat);
        return "(" + gPowA + "," + concat + ")";
    }

    // adapted from java2s.com
    public static BigInteger randomBigInteger(BigInteger n) {
        // 1 < b < n-1:
        BigInteger min = BigInteger.TWO;
        BigInteger max = n.subtract(BigInteger.TWO);
        BigInteger bigInt1 = max.subtract(min);
        Random rnd = new Random();
        int maxIntLenght = max.bitLength();
        BigInteger b;
        b = new BigInteger(maxIntLenght, rnd);
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
        hexString = hexString.replace(" ", "").replace("\n", "").replace("\r", "");
        // convert to BigInteger
        return new BigInteger(hexString, 16);
    }


//    public static void generateKeyPair(int bitLength) throws IOException {
//        // create two random prime numbers
//        BigInteger p = BigInteger.probablePrime(bitLength, new Random());
//        BigInteger q = BigInteger.probablePrime(bitLength, new Random());
//
//        // values need to be big enough so that n is bigger than the values that need to be en-/decoded
////        BigInteger p = BigInteger.valueOf(17);
////        BigInteger q = BigInteger.valueOf(19);
//
//        // set a new 2nd prime if the prime numbers are the same
//        while (p.equals(q)) {
//            q = BigInteger.probablePrime(5, new Random());
//        }
//
//        // n = p*q
//        BigInteger n = p.multiply(q);
//
//        // phiN = (p-1)*(q-1)
//        BigInteger phiN = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
//
//        // chose a number that isn't in Z*(n)
//        BigInteger e = p;
//
//        // d: e*d mod phiN == 1
//        BigInteger d = euklid(phiN, e);
//        if (d.compareTo(BigInteger.ZERO) < 0) {
//            d = d.add(phiN);
//        }
//
//        System.out.println("p: " + p + ", q:" + q + ", n: " + n + ", phiN: " + phiN + ", e: " + e + ", d: " + d);
//
//        // write public and private key into text file
//        FileReaderWriter.writeFile("sk.txt", n + "," + d);
//        FileReaderWriter.writeFile("pk.txt", n + "," + e);
//    }

//    public static BigInteger euklid(BigInteger a, BigInteger b){
//        if (b.compareTo(a) > 0) {
//            BigInteger t = a; a = b; b = t;
//        }
//        BigInteger q, r, x0temp, y0temp;
//        BigInteger x0 = BigInteger.ONE, y0 = BigInteger.ZERO, x1 = BigInteger.ZERO, y1 = BigInteger.ONE;
//        while (b.compareTo(BigInteger.ZERO) != 0) {
//            q = (a.divide(b)); r = (a.mod(b));
//            a = b; b = r;
//            x0temp = x0; y0temp = y0;
//            x0 = x1; y0 = y1;
//            x1 = x0temp.subtract(q.multiply(x1)); y1 = y0temp.subtract(q.multiply(y1));
//        }
//        return y0;
//    }

//    public static BigInteger fastModularExponentiation(BigInteger x, BigInteger n, BigInteger exponent) {
//        String exponentAsBinary = exponent.toString(2);
//        int i = exponentAsBinary.length()-1;
//        BigInteger h = BigInteger.ONE, k = x;
//
//        while (i >= 0) {
//            if (Integer.parseInt(String.valueOf(exponentAsBinary.charAt(i))) == 1) {
//                h = (h.multiply(k)).mod(n);
//            }
//            k = (k.multiply(k)).mod(n);
//            i -= 1;
//        }
//
//        return h;
//    }

    public static BigInteger[] convertToASCII(String text) {
        int l = text.length();
        BigInteger[] data = new BigInteger[l];
        for(int i = 0; i < l; ++i) {
            char character = text.charAt(i);
            data[i] = new BigInteger(String.valueOf((int)character));
        }
        return data;
    }

    public static String convertASCIIToString(String text) {
        String[] array = text.split(",");
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
