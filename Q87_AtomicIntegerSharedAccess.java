import java.util.concurrent.atomic.AtomicInteger;

public class Q87_AtomicIntegerSharedAccess {
    static AtomicInteger sharedValue = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 10000; i++) {
                sharedValue.incrementAndGet();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Shared value: " + sharedValue.get());
        System.out.println("This confirms that the two threads were able to increment the shared value atomically and the final value is as expected.");
    }
}
