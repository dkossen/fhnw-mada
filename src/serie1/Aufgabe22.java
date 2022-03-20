package serie1;

public class Aufgabe22 {

    public static void main(String[] args) {

        int n = 41;
        int e = 20;

        int d = euklid(n, e);
        System.out.println(d);

        if (ggt1(d, e)) {
            System.out.println("d =" + d);
        } else {
            System.out.println("There is no possible d");
        }

    }

    public static boolean ggt1(int a, int b) {
        int r;
        while ( b != 0 ) {
            r = a % b;
            a = b;
            b = r;
        }
        if (a == 1) return true;
        else return false;
    }

    // alle Zahlen teilerfremd zu n
    public static int euklid(int a, int b){
        int a_original = a;
        if ( b > a) {
            int t = a; a = b; b = t;
        }
        int q, r;
        int x0 = 1, y0 = 0, x1 = 0, y1 = 1;
        while (b != 0) {
            q = (a / b); r = (a % b);
            a = b; b = r;
            x0 = x1; y0 = y1;
            x1 = x0 - q*x1; y1 = y0 - q*y1;
//            System.out.println("x1 = " + x1 + " | y1 = " + y1);
            // System.out.println("a = " + a + " b = " + b + " x0 = " + x0 + " y0 = " + y0 + " x1 = " + x1 + " y1 = " + y1 + " q = " + q + " r = " + r);
        }
        System.out.println(y0 % a_original);
        return y0 % a_original;
    }


}
