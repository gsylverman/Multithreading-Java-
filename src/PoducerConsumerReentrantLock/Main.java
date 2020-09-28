package PoducerConsumerReentrantLock;

import java.util.Random;
import java.util.concurrent.*;

class Main {
    public static final String EOF = "EOF";

    public static void main(String[] args) {
        ArrayBlockingQueue<String> listX = new ArrayBlockingQueue<>(6);
        MyProducer myProducer = new MyProducer(listX);
        MyConsumer myConsumer1 = new MyConsumer(listX);
        MyConsumer myConsumer2 = new MyConsumer(listX);

        ExecutorService executor = Executors.newFixedThreadPool(4);
//        executor.submit(myProducer);
//        executor.submit(myConsumer1);
//        executor.submit(myConsumer2);

        executor.execute(myProducer);
        executor.execute(myConsumer1);
        executor.execute(myConsumer2);
        Future<String> future = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("Hi from callable");
                return "callable result";
            }
        });
        executor.shutdown();

        long startTime = System.currentTimeMillis();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Finisehd in: " + (endTime - startTime) / 1000 + " seconds");
//        new Thread(myProducer).start();
//        new Thread(myConsumer1).start();
//        new Thread(myConsumer2).start();
    }
}

class MyProducer implements Runnable {
    private ArrayBlockingQueue<String> list;

    public MyProducer(ArrayBlockingQueue<String> list) {
        this.list = list;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4"};
        for (String num : nums) {
            try {
                System.out.println("Adding to list: " + num);
                list.put(num);
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            list.put("EOF");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyConsumer implements Runnable {
    private ArrayBlockingQueue<String> list;

    public MyConsumer(ArrayBlockingQueue<String> list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (list) {
                if (list.isEmpty()) {
                    continue;
                }
                if (list.peek().equals(Main.EOF)) {
                    System.out.println("Exiting" + Thread.currentThread().getName());
                    break;
                } else {
                    try {
                        System.out.println("Removed " + list.take() + ", by " + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

