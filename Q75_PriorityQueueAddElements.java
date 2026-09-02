import java.util.PriorityQueue;

public class Q75_PriorityQueueAddElements {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(50);
        pq.add(10);
        pq.add(40);
        pq.add(20);

        System.out.println("Polling elements in priority order:");
        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
    }
}
