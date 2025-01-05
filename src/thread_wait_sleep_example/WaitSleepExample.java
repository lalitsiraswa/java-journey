package thread_wait_sleep_example;

// The `wait()` method is used with an object to release the lock held by the thread and wait until another thread invokes either
// `notify()` or `notifyAll()` on the object. On the hand the `sleep()` method, which is a method of the `Thread` class pauses
// the current threads execution, for a specific period, without releasing the lock.

// sleep() is a blocking operation that keeps a hold on the monitor / lock of the shared object for the specified number of milliseconds.
public class WaitSleepExample {
    public static void main(String[] args) {
        // Shared lock object for synchronization
        final Object lock = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println(Thread.currentThread().getName() + " waiting");
                    lock.wait();
                    System.out.println(Thread.currentThread().getName() + " resumed");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println(Thread.currentThread().getName() + " sleeping");
                    Thread.sleep(4000);
                    System.out.println(Thread.currentThread().getName() + " awake and notifying");
                    lock.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-2");

        Thread t3 = new Thread(() -> {
            synchronized (lock) {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " Executes");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-3");

//        t1.start();
//        t2.start();
//        t3.start();
//        ------------------------
        // Thread t4 acquires the lock and holds it for 4 seconds
        Thread t4 = new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " has started and acquired the lock.");
                try {
                    // Simulating work by sleeping for 4 seconds
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " has finished its task and is releasing the lock.");
            }
        }, "Thread-4");

        // Thread t5 waits for the lock held by t4 to be released
        Thread t5 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " has started and is trying to acquire the lock.");
            synchronized (lock) {
                try {
                    // Simulating work by sleeping for 200ms after acquiring the lock
                    Thread.sleep(200);
                    System.out.println(Thread.currentThread().getName() + " has acquired the lock and is executing.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " has finished its task and is releasing the lock.");
        }, "Thread-5");

        // Starting both threads
        t4.start();
        t5.start();
    }
}
