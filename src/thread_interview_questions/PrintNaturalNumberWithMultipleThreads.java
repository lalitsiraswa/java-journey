package thread_interview_questions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class NumberPrinter {
    private int currentNumber = 1;
    private final int MAX_NUMBER = 10;

    public synchronized void printNumbers() {
        while (currentNumber <= MAX_NUMBER) {
            System.out.println(Thread.currentThread().getName() + " : " + currentNumber);
            currentNumber += 1;
            // Notify the other thread that it can take its turn:
            notifyAll();
            try {
                // Wait if we haven't reached the maximum number, allowing the other thread to proceed:
                if (currentNumber <= MAX_NUMBER) {
                    wait();
                }
            } catch (InterruptedException e) {
                // Reset interrupted status:
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class PrintNaturalNumberWithMultipleThreads {
    public static void main(String[] args) {
        NumberPrinter numberPrinter = new NumberPrinter();
        // Create 5 threads that will share the same NumberPrinter instance:
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.execute(numberPrinter::printNumbers);
        executorService.execute(numberPrinter::printNumbers);
        executorService.execute(numberPrinter::printNumbers);
        executorService.execute(numberPrinter::printNumbers);
        executorService.execute(numberPrinter::printNumbers);
    }
}
