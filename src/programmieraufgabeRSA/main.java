package programmieraufgabeRSA;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

public class main {

    public static void main(String[] args) throws IOException {

        generateKeyPair();

    }

    public static void generateKeyPair() throws IOException {
        // create two random prime numbers
//        BigInteger p = BigInteger.probablePrime(2048, new Random());
//        BigInteger q = BigInteger.probablePrime(2048, new Random());

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
        writeFile("sk.txt", "(" + n + "," + d + ")");
        writeFile("pk.txt", "(" + n + "," + e + ")");
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

    public static BigInteger ggT(BigInteger a, BigInteger b) {
        if (a.equals(BigInteger.ZERO)){
            return b;
        } else {
            return ggT(b.mod(a), a);
        }
    }

    public static void writeFile(String fileName, String str)
        throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/programmieraufgabeRSA/" + fileName));
        writer.write(str);

        writer.close();
    }


}
