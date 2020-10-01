package Future;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.*;

class SomeCls {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();

        // if no need to return we can use a wildcard (<?> and <Void> for implemented method from Interface)
        Future<?> future = executor.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Random random = new Random();
                int duration = random.nextInt(4000);
                Thread.sleep(4000);
                if (duration > 2000) {
                    throw new IOException("Thread is sleeping to long..");
                }
                return null;
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
