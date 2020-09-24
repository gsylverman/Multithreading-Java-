public class SomeClass implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Another thread wake me up");
            return;
        }
        System.out.println(ThreadColor.ANSI_CYAN + " 5 seconds have passed, Hi from runnable");

    }
}
