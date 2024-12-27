package callable_and_runnable;

// There are two ways of creating threads – one by extending the Thread class and other by creating a thread with a Runnable.
// However, one feature lacking in  Runnable is that we cannot make a thread return result when it terminates, i.e. when run() completes.
// For supporting this feature, the Callable interface is present in Java.

// A Runnable is a type of interface that includes a run method without a return value and no ability to throw a checked exception.
// On the other hand a Callable is an interface, with a call method that can return a value and may throw a checked exception.

// Note that a thread can’t be created with a Callable, it can only be created with a Runnable.
// Another difference is that the call() method can throw an exception whereas run() cannot.

// To create the thread, a Runnable is required. To obtain the result, a Future is required.

// The Java library has the concrete type FutureTask, which implements Runnable and Future, combining both functionality conveniently.
// A FutureTask can be created by providing its constructor with a Callable. Then the FutureTask object is provided to the constructor
// of Thread to create the Thread object. Thus, indirectly, the thread is created with a Callable. For further emphasis, note that
// there is no way to create the thread directly with a Callable.

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


// Java program to illustrate Callable and FutureTask for random number generation:
class CallableExample implements Callable {
    @Override
    public Object call() throws Exception {
        // Create random number generator:
        Random generator = new Random();
        Integer randomNumber = generator.nextInt(5);
        // To simulate a heavy computation,
        // we delay the thread for some random time:
        Thread.sleep(randomNumber * 1000);
        return randomNumber;
    }
}

public class CallableAndRunnable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // FutureTask is a concrete class that implements both Runnable and Future
        FutureTask[] randomNumberTasks = new FutureTask[5];
        for (int i = 0; i < 5; i++) {
            Callable callable = new CallableExample();
            // Create the FutureTask with Callable
            randomNumberTasks[i] = new FutureTask(callable);
            // As it implements Runnable, create Thread with FutureTask
            Thread thread = new Thread(randomNumberTasks[i]);
            thread.start();
        }
        for (int i = 0; i < 5; i++) {
            // As it implements Future, we can call get()
            System.out.println(randomNumberTasks[i].get());
            // This method blocks till the result is obtained
            // The get method can throw checked exceptions
            // like when it is interrupted. This is the reason
            // for adding the throws clause to main
        }
    }
}
