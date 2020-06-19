public class Combinatorics {
    public static long combination(int n, int r) {
        // C(n,r) = C(n,r-1) * (n - r + 1) / r
        long prev = 1;
        for (int i = 1; i <= r; i++) {
            prev = prev * (n - i + 1) / i;
        }
        return prev;
    }

    public static long permutation(int n, int r) {
        // P(n,r) = P(n,r-1) * (n - r + 1)
        long prev = 1;
        for (int i = 1; i <= r; i++) {
            prev = prev * (n - i + 1);
        }
        return prev;
    }
}

class CombinationRunner {
    public static void main(String[] args) {
        System.out.println(Combinatorics.combination(5,2));
        System.out.println(Combinatorics.permutation(5,3));
    }
}
