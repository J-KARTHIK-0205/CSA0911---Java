public class Q43_MethodOverloadingVarargs {
    static int sum(int... numbers) {
        int total = 0;
        for (int n : numbers) {
            total += n;
        }
        return total;
    }

    static double sum(double... numbers) {
        double total = 0;
        for (double n : numbers) {
            total += n;
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println("Sum of ints: " + sum(1, 2, 3, 4));
        System.out.println("Sum of doubles: " + sum(1.5, 2.5, 3.0));
    }
}
