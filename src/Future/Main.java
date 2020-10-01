package Future;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();

        Future<Integer> future = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Random random = new Random();
                int duration = random.nextInt(4000);
                Thread.sleep(4000);
                if (duration > 2000) {
                    throw new IOException("Thread is sleeping to long..");
                }
                return duration;
            }
        });
        executor.shutdown();
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            IOException ex = (IOException) e.getCause();
            System.out.println(ex.getMessage());
        }
    }
}
