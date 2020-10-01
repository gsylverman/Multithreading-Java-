package FutureTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // create Thread by implementing anonymous class
        FutureTask f1 = new FutureTask(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("a");
                return 1;
            }
        });
        Thread t1 = new Thread(f1);
        t1.start();
        System.out.println(f1.get());

        // ----------------------------------------------------------------------------------------
        // create Thread by implementing callable Interface
        Processor processor = new Processor();
        FutureTask futureTask = new FutureTask<>(processor);
        new Thread(futureTask).start();
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class Processor implements Callable {
    @Override
    public Integer call() throws Exception {
        System.out.println("b");
        return 2;
    }
}
