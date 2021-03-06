public class AnotherThread extends Thread {
    @Override
    public void run() {
        System.out.println(ThreadColor.ANSI_GREEN + "Hello from: " + currentThread().getName());
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            System.out.println("Another thread wake me up" + currentThread().getName());
            return;
        }
        System.out.println("6 sec have passed, I am awake: " + currentThread().getName());

    }
}
