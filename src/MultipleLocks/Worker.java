package MultipleLocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Worker {
    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();
    private Object lock1 = new Object();
    private Object lock2 = new Object();
    private Random random = new Random();

    public void main() {
        long start = System.currentTimeMillis();
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });
        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });
        thread3.start();
        thread4.start();
        try {
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("List1 length: " + list1.size() + ", List2 length: " + list2.size());
        System.out.println("Duration: " + (end - start));
    }

    private void stage1() {
        synchronized (lock1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list1.add(random.nextInt(100));
        }
    }

    private void stage2() {
        synchronized (lock2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list2.add(random.nextInt(100));
        }
    }

    private void process() {
        for (int i = 0; i < 1000; i++) {
            stage1();
            stage2();
        }
    }
}
