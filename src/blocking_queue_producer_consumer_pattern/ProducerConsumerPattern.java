package blocking_queue_producer_consumer_pattern;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// The `wait()` method is used with an object to release the lock held by the thread and wait until another thread invokes either
// `notify()` or `notifyAll()` on the object. On the hand the `sleep()` method, which is a method of the `Thread` class pauses
// the current threads execution, for a specific period, without releasing the lock.

class BlockingQueue {
    private Queue<Integer> queue;
    private int capacity;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
        queue = new LinkedList<>();
    }

    public boolean add(int item) {
        synchronized (queue) {
            while (queue.size() == capacity) {
                try {
                    System.out.println(Thread.currentThread().getName() + " waiting");
                    queue.wait();
                    System.out.println(Thread.currentThread().getName() + " resumed");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.add(item);
            queue.notifyAll();
            return true;
        }
    }

    public int remove() {
        synchronized (queue) {
            while (queue.isEmpty()) {
                try {
                    System.out.println(Thread.currentThread().getName() + " waiting");
                    queue.wait();
                    System.out.println(Thread.currentThread().getName() + " resumed");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int element = queue.poll();
            queue.notifyAll();
            return element;
        }
    }
}

public class ProducerConsumerPattern {
    public static void main(String[] args) {
        BlockingQueue blockingQueue = new BlockingQueue(5);
        // Producer thread to add items
        Runnable producer = () -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    System.out.println(Thread.currentThread().getName() + " adding : " + i);
                    blockingQueue.add(i);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // Consumer thread to remove items
        Runnable consumer = () -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    int element = blockingQueue.remove();
                    System.out.println(Thread.currentThread().getName() + " removed : " + element);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Create multiple producer and consumer threads
        Thread producer1 = new Thread(producer, "Producer-1");
        Thread producer2 = new Thread(producer, "Producer-2");
        Thread producer3 = new Thread(producer, "Producer-3");
        Thread consumer1 = new Thread(consumer, "Consumer-1");
        Thread consumer2 = new Thread(consumer, "Consumer-2");
        Thread consumer3 = new Thread(consumer, "Consumer-3");

        // Start threads
        producer1.start();
        producer2.start();
        producer3.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
    }
}
