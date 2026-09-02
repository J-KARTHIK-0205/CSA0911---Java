import java.util.LinkedList;
import java.util.Queue;

public class Q81_ProducerConsumerQueue {
    static final int CAPACITY = 5;
    static Queue<Integer> queue = new LinkedList<>();
    static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                synchronized (lock) {
                    while (queue.size() == CAPACITY) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    queue.add(i);
                    System.out.println("Produced: " + i);
                    lock.notifyAll();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                synchronized (lock) {
                    while (queue.isEmpty()) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    int value = queue.poll();
                    System.out.println("Consumed: " + value);
                    lock.notifyAll();
                }
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}
