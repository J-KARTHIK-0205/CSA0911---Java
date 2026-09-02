import java.util.Arrays;

public class Q80_CompareThreadExecutionTimes {
    public static void main(String[] args) throws InterruptedException {
        int[] arr1 = generateArray(50000);
        int[] arr2 = generateArray(50000);

        Thread t1 = new Thread(() -> {
            long start = System.nanoTime();
            Arrays.sort(arr1);
            long end = System.nanoTime();
            System.out.println("Thread 1 sort time: " + (end - start) / 1_000_000.0 + " ms");
        });

        Thread t2 = new Thread(() -> {
            long start = System.nanoTime();
            Arrays.sort(arr2);
            long end = System.nanoTime();
            System.out.println("Thread 2 sort time: " + (end - start) / 1_000_000.0 + " ms");
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Both threads finished sorting.");
    }

    static int[] generateArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * 100000);
        }
        return arr;
    }
}
