package thread_pool_example;

// Java program to illustrate ThreadPool
// Task class to be executed (Step 1)

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Task implements Runnable {
    private String taskName;

    public Task(String taskName) {
        this.taskName = taskName;
    }

    // Prints task name and sleeps for 1s:
    // This Whole process is repeated 5 times:
    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                if (i == 0) {
                    // Prints the initialization time for every task:
                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                    System.out.println("Initialization Time For" + " Task Name : " + taskName + " = " + dateFormat.format(date));
                } else {
                    // Prints the execution time for every task:
                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                    System.out.println("Executing Time For Task Name : " + taskName + " = " + dateFormat.format(date));
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ThreadPoolExample {
    // Maximum number of threads in thread pool:
    private static final int MAX_THREAD = 2;

    public static void main(String[] args) {
        // Creates five tasks:
        Runnable task1 = new Task("Task-1");
        Runnable task2 = new Task("Task-2");
        Runnable task3 = new Task("Task-3");
        Runnable task4 = new Task("Task-4");
        Task task5 = new Task("Task-5");
        // Creates a thread pool with MAX_THREAD number of threads as the fixed pool size(Step 2):
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD);
        // Passes the Task objects to the pool to execute (Step 3):
        executorService.execute(task1);
        executorService.execute(task2);
        executorService.execute(task3);
        executorService.execute(task4);
        executorService.execute(task5);
        // Pool shutdown ( Step 4)
        executorService.shutdown();
    }
}
// One of the main advantages of using this approach is when you want to process
// 100 requests at a time, but do not want to create 100 Threads for the same,
// so as to reduce JVM overload. You can use this approach to create a
// ThreadPool of 10 Threads and you can submit 100 requests to this ThreadPool.
// ThreadPool will create maximum of 10 threads to process 10 requests at a
// time. After process completion of any single Thread,
// ThreadPool will internally allocate the 11th request to this Thread
// and will keep on doing the same to all the remaining requests.

// Executor Thread Pool Methods
// newFixedThreadPool(int) -> Creates a fixed size thread pool.
// newCachedThreadPool() -> Creates a thread pool that creates new
// threads as needed, but will reuse previously
// constructed threads when they are available
// newSingleThreadExecutor() -> Creates a single thread.

// In case of a fixed thread pool, if all threads are being currently run by the
// executor then the pending tasks are placed in a queue and are executed when a
// thread becomes idle.