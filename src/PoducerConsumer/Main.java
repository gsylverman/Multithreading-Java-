package PoducerConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Main {
    public static final String EOF = "EOF";

    public static void main(String[] args) {
        List<String> listX = new ArrayList<>();
        MyProducer myProducer = new MyProducer(listX);
        MyConsumer myConsumer1 = new MyConsumer(listX);
        MyConsumer myConsumer2 = new MyConsumer(listX);

        new Thread(myProducer).start();
        new Thread(myConsumer1).start();
        new Thread(myConsumer2).start();

    }
}

class MyProducer implements Runnable {
    private List<String> list;

    public MyProducer(List<String> list) {
        this.list = list;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4"};
        for (String num : nums) {
            try {
                System.out.println("Adding to list: " + num);
                synchronized (list) {
                    list.add(num);
                }
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (list) {
            list.add("EOF");
        }

    }
}

class MyConsumer implements Runnable {
    private List<String> list;

    public MyConsumer(List<String> list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (list) {
                if (list.isEmpty()) {
                    continue;
                }
                if (list.get(0).equals(Main.EOF)) {
                    System.out.println("Exiting" + Thread.currentThread().getName());
                    break;
                } else {
                    System.out.println("Removed " + list.remove(0) + ", by " + Thread.currentThread().getName());
                }
            }
        }
    }
}

