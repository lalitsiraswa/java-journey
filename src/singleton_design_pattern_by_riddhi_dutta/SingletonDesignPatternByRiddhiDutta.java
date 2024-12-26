package singleton_design_pattern_by_riddhi_dutta;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class TVSet {
    private static volatile TVSet tvSetInstance = null;

    private TVSet() {
        System.out.println("TV Set Instantiated!");
    }

    public static TVSet getTvSetInstance() {
        if (tvSetInstance == null) { // Optimisation
            synchronized (TVSet.class) {
                if (tvSetInstance == null) { // Double Checking
                    tvSetInstance = new TVSet();
                }
            }
        }
        return tvSetInstance;
    }
}

public class SingletonDesignPatternByRiddhiDutta {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " Started!");
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.execute(TVSet::getTvSetInstance);
        executorService.execute(TVSet::getTvSetInstance);
        executorService.execute(TVSet::getTvSetInstance);
        executorService.execute(TVSet::getTvSetInstance);
        executorService.execute(TVSet::getTvSetInstance);
        System.out.println(Thread.currentThread().getName() + " Exiting!");
    }
}
