package Objectlock;

import java.util.LinkedList;

public class Processor {
    private final int LIMIT = 10;
    private LinkedList<Integer> list = new LinkedList<>();
    private Object lock = new Object();

    public void produce() {
        int nr = 0;
        while (true) {
            synchronized (lock) {
                list.add(nr++);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (list.size() == LIMIT) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        }
    }

    public void consume() {
        while (true) {
            synchronized (lock) {
                while (list.size() == 0) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int value = list.removeFirst();
                System.out.println(value + " list size " + list.size());
                lock.notify();
            }
        }
    }
}

