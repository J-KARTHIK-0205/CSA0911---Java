public class Q85_JoinMethodWaitForThread {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Working... step " + i);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        worker.start();
        worker.join(); // main thread waits for worker to finish
        System.out.println("Worker thread finished. Main thread proceeding.");
    }
}
