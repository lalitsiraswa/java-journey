package thread_interview_questions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class NumberPrinter {
    private int currentNumber = 1;
    private final int MAX_NUMBER = 10;

    public synchronized void printNumbers() {
        while (currentNumber <= MAX_NUMBER) {
            System.out.println(Thread.currentThread().getName() + " : " + currentNumber);
            currentNumber++;

            notifyAll(); // Notify waiting threads

            if (currentNumber <= MAX_NUMBER) {
                try {
                    wait(); // Make the current thread wait
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

public class PrintNaturalNumberWithMultipleThreads {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " Started!");
        NumberPrinter printer = new NumberPrinter();
        ExecutorService service = Executors.newFixedThreadPool(5);
        // Launch 5 worker threads
        for (int i = 0; i < 5; i++) {
            service.execute(printer::printNumbers);
        }
        service.shutdown(); // Properly shut down the thread pool
        while (!service.isTerminated()) {
            // Wait for all threads to finish
        }
        System.out.println(Thread.currentThread().getName() + " Exiting!");
    }
}
