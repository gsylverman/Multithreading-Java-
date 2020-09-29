package TestProducerConsumer;

public class Processor {
    private boolean isData = false;

    public synchronized void produce() {

        while (true) {
            if (isData) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                System.out.println(Thread.currentThread().getName() + " Producing data");
                Thread.sleep(2000);
                System.out.println("Data Produced");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            notify();
            isData = true;
        }
    }

    public synchronized void consume() {

        while (true) {

            if (!isData) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " Consuming data");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Data is consumed");

            isData = false;
            notify();
        }
    }
}
