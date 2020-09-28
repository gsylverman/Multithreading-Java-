package PoducerConsumerReentrantLock;

import javax.security.sasl.SaslServer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

class Main {
    public static final String EOF = "EOF";

    public static void main(String[] args) {
        List<String> listX = new ArrayList<>();
        ReentrantLock listReentrantLock = new ReentrantLock();
        MyProducer myProducer = new MyProducer(listX, listReentrantLock);
        MyConsumer myConsumer1 = new MyConsumer(listX, listReentrantLock);
        MyConsumer myConsumer2 = new MyConsumer(listX, listReentrantLock);

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
    private List<String> list;
    private ReentrantLock reentrantLock;

    public MyProducer(List<String> list, ReentrantLock reentrantLock) {
        this.list = list;
        this.reentrantLock = reentrantLock;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4"};
        for (String num : nums) {
            try {
                System.out.println("Adding to list: " + num);
                reentrantLock.lock();
                try {
                    list.add(num);
                } finally {
                    reentrantLock.unlock();
                }

                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        reentrantLock.lock();
        try {
            list.add("EOF");
        } finally {
            reentrantLock.unlock();
        }
    }
}

class MyConsumer implements Runnable {
    private List<String> list;
    private ReentrantLock reentrantLock;


    public MyConsumer(List<String> list, ReentrantLock reentrantLock) {
        this.list = list;
        this.reentrantLock = reentrantLock;
    }

    @Override
    public void run() {
        int counter = 0;
        while (true) {
            if (reentrantLock.tryLock()) {
                try {
                    if (list.isEmpty()) {
                        continue;
                    }
                    System.out.println(counter);
                    if (list.get(0).equals(Main.EOF)) {
                        System.out.println("Exiting" + Thread.currentThread().getName());
                        break;
                    } else {
                        System.out.println("Removed " + list.remove(0) + ", by " + Thread.currentThread().getName());
                    }
                } finally {
                    reentrantLock.unlock();
                }
            } else {
                counter++;
            }
        }
    }
}

