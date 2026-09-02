public class Q82_DeadlockScenario {
    static final Object lock1 = new Object();
    static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 1: Holding lock 1...");
                try { Thread.sleep(100); } catch (InterruptedException e) { }
                System.out.println("Thread 1: Waiting for lock 2...");
                synchronized (lock2) {
                    System.out.println("Thread 1: Holding lock 1 and 2.");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Thread 2: Holding lock 2...");
                try { Thread.sleep(100); } catch (InterruptedException e) { }
                System.out.println("Thread 2: Waiting for lock 1...");
                synchronized (lock1) {
                    System.out.println("Thread 2: Holding lock 2 and 1.");
                }
            }
        });

        // NOTE: This intentionally demonstrates a deadlock.
        // t1 holds lock1 and waits for lock2, t2 holds lock2 and waits for lock1.
        t1.start();
        t2.start();
    }
}
