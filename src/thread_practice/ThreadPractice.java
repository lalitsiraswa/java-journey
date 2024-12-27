package thread_practice;

class ImplementsRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Implements Runnable!");
    }
}

class ExtendsThread extends Thread {
    @Override
    public void run() {
        System.out.println("Extends Runnable!");
    }
}

public class ThreadPractice {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new ImplementsRunnable());
        thread1.start();
        ExtendsThread thread2 = new ExtendsThread();
        thread2.start();
    }
}
