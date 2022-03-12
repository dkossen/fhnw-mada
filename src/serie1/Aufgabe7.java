package serie1;

import java.math.BigInteger;
import java.util.Random;

public class Aufgabe7 {

    /* Schreiben Sie ein Java-Programm, welches zwei 1024-Bit Primzahlen zufällig erzeugt,
    das Produkt dieser Zahlen berechnet und alle drei Zahlen ausgibt. */

    public static void main(String[] args) {

        BigInteger prime1 = BigInteger.probablePrime(1024, new Random());
        BigInteger prime2 = BigInteger.probablePrime(1024, new Random());
        BigInteger primeProduct = prime1.multiply(prime2);

        System.out.println(prime1);
        System.out.println(prime2);
        System.out.println(primeProduct);

    }

}
