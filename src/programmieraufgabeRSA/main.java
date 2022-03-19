package programmieraufgabeRSA;

import java.math.BigInteger;
import java.util.Random;

public class main {

    public static void main(String[] args) {

//        BigInteger p = BigInteger.probablePrime(5, new Random());
//        BigInteger q = BigInteger.probablePrime(5, new Random());

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

        BigInteger e = p;

        // number e != factor of n:
        // loop through all possible numbers (e-phiN) to get a number that isn't a factor of n
//        for (e = BigInteger.TWO; e.compareTo(phiN) < 0; e = e.add(BigInteger.ONE)) {
//            if (ggT(e, phiN).equals(1)) {
//                break;
//            }
//        }

        // d: e*d mod phiN == 1
        //Euklid: a = phiN, b = e
        BigInteger d = euklid(phiN, e);
        if (d < 0) {
            d += phiN;
        }

        System.out.println("p: " + p);
        System.out.println("q: " + q);
        System.out.println("n: " + n);
        System.out.println("phiN: " + phiN);
        System.out.println("e: " + e);
        System.out.println("d: " + d);


    }

    public static int euklid(int a, int b){
        if (b > a) {
            int t = a; a = b; b = t;
        }
        int q, r;
        int x0 = 1, y0 = 0, x1 = 0, y1 = 1, x0temp = 0, y0temp = 0;
        while (b != 0) {
            q = (a / b); r = (a % b);
            a = b; b = r;
            x0temp = x0; y0temp = y0;
            x0 = x1; y0 = y1;
            x1 = x0temp - q*x1; y1 = y0temp - q*y1;
//             System.out.println("x1 = " + x1 + " | y1 = " + y1);
//             System.out.println("a = " + a + " b = " + b + " x0 = " + x0 + " y0 = " + y0 + " x1 = " + x1 + " y1 = " + y1 + " q = " + q + " r = " + r);
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


}
