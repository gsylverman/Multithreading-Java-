public class SomeClass implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(ThreadColor.ANSI_CYAN + " 5 seconds have passed, Hi from runnable");

    }
}
