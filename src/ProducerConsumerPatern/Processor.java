package ProducerConsumerPatern;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Processor {
    private static Random random = new Random();
    private static BlockingQueue<Integer> list = new ArrayBlockingQueue<>(10);

    static void produce() {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                list.put(random.nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static void consume() {
        while (true) {
            if (random.nextInt(10) == 0) {
                try {
                    Thread.sleep(100);
                    int randomNr = list.take();
                    System.out.println("Random nr: " + randomNr + ", List size: " + list.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

