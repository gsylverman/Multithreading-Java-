package Synchronized;

public class Main {
    private int nr = 0;

    public static void main(String[] args) {
        Main main = new Main();
        main.increment();
    }

    //  synchronized keyword guarantees that only one thread will be executing the synchronized block statements at a time, and thus prevent multiple threads from corrupting the shared data inside the block.
    private synchronized void changeNr() {
        nr++;
    }

    private void increment() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    changeNr();
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    changeNr();
                }
            }
        });
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(nr);
    }
}
