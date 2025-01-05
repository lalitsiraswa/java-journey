package catch_exception_thrown_by_another_thread;

// This can be done using Thread.UncaughtExceptionHandler.

public class ThreadExceptionHandler {
    public static void main(String[] args) {
        // create our uncaught exception handler:
        Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("Uncaught exception : " + e);
            }
        };

        // create another thread:
        Thread otherThread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " Sleeping ...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " Throwing exception ...");
            throw new RuntimeException();
        }, "Other-Thread");
        // set our uncaught exception handler as the one to be used when the new thread
        // throws an uncaught exception:
        otherThread.setUncaughtExceptionHandler(handler);

        // start the other thread - our uncaught exception handler will be invoked when
        // the other thread throws an uncaught exception:
        otherThread.start();
    }
}
