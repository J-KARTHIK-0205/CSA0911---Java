public class Q86_SynchronizationSharedResource {
    static int sharedResource = 0;

    static synchronized void increment() {
        sharedResource++;
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 10000; i++) {
                increment();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Final shared resource value: " + sharedResource);
    }
}
