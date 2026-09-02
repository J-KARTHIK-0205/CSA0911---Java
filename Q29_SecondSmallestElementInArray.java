import java.util.Scanner;

public class Q29_SecondSmallestElementInArray {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of elements: ");
        int n = sc.nextInt();
        int[] arr = new int[n];
        System.out.println("Enter " + n + " integers:");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
        for (int val : arr) {
            if (val < first) {
                second = first;
                first = val;
            } else if (val < second && val != first) {
                second = val;
            }
        }
        System.out.println("Second smallest element: " + second);
        sc.close();
    }
}
