import java.util.LinkedList;
import java.util.Queue;

class SharedBuffer {
    private Queue<Integer> buffer = new LinkedList<>();
    private final int capacity = 5;

    public synchronized void produce(int value) throws InterruptedException {
        while (buffer.size() == capacity) {
            wait();
        }
        buffer.add(value);
        System.out.println("Produced: " + value);
        notify();
    }

    public synchronized void consume() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait();
        }
        int value = buffer.poll();
        System.out.println("Consumed: " + value);
        notify();
    }
}

public class Q83_WaitNotifyProducerConsumer {
    public static void main(String[] args) throws InterruptedException {
        SharedBuffer sharedBuffer = new SharedBuffer();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    sharedBuffer.produce(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    sharedBuffer.consume();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}
