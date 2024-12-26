package volatile_keyword;

// The volatile keyword guarantees visibility and ordering of variable updates across threads. Any thread reading the variable
// sees the most recent modification made by any other thread when a variable is declared as volatile. It prevents compiler
// optimizations that reorder the code and ensures a consistent view of the shared variable among threads.

public class VolatileKeywordExample {
    private static volatile boolean flag = true;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " Started!");
            int count = 1;
            while (flag) {
                try {
                    System.out.println(Thread.currentThread().getName() + " Count : " + count);
                    count += 1;
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " Exiting!");
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " Started!");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag = false;
            System.out.println("Flag set to false by " + Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getName() + " Exiting!");
        }, "Thread-2");

        t1.start();
        t2.start();
    }
}
