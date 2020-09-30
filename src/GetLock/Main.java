package GetLock;

class Main {
    public static void main(String[] args) {
        Process process = new Process();
        Thread thread1 = new Thread(() -> process.thread1());
        Thread thread2 = new Thread(() -> process.thread2());
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        process.finished();
    }
}
