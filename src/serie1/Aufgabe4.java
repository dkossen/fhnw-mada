package serie1;

public class Aufgabe4 {

    // Sieb des Eratostenes
    // alle Primzahlen bis 1'000'000

    public static void main(String[] args) {
        sieb(1000000);
    }

    public static void sieb(int number){

        boolean prim[] = new boolean[number+1];
        int count = 0;

        // set all to true
        for (int i = 2; i <= number; i++) {
            prim[i] = true;
        }

        // go through all numbers up to the max i*i one (2*2 = 4 <= number?)
        for (int i = 2; i*i <= number; i++) {
            if (prim[i]) {
                // durch alle Faktoren gehen (2*2, 2*3, 2*3, ...) und als Primzahl 'wegstreichen'
                for (int j = 2; i*j <= number; j++) {
                    prim[i*j] = false;
                }
            }
        }

        // durch alle Nummern gehen und sout wenn Primzahl = true
        for (int i = 2; i <= number; i++) {
            if (prim[i]) {
                count++;
                System.out.println(i);
            }
        }

        System.out.println("Anzahl Primzahlen bis " + number + ": " + count);

    }


}
