package Volatile;

import java.util.Scanner;

class Processor extends Thread {
   // volatile keyword guarantees that all threads see the shared state
    private volatile boolean running = true;
    private int nr = 0;

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nr++;
            System.out.println(nr);
        }
    }

    public void shutDown() {
        running = false;
    }
}

public class Main {
    public static void main(String[] args) {
        Processor processor = new Processor();
        processor.start();
        System.out.println("please a key to stop");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        processor.shutDown();
    }
}
