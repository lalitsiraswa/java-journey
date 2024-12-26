package thread_wait_sleep_example;

//The `wait()` method is used with an object to release the lock held by the thread and wait until another thread invokes either
// `notify()` or `notifyAll()` on the object. On the hand the `sleep()` method, which is a method of the `Thread` class pauses
// the current threads execution, for a specific period, without releasing the lock.
public class WaitSleepExample {
    public static void main(String[] args) {
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

        t1.start();
        t2.start();
    }
}
