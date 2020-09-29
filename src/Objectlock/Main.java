package Objectlock;

class Main {
    public static void main(String[] args) {
        Processor processor = new Processor();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                processor.produce();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                processor.consume();
            }
        });
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
