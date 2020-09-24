public class SomeClass implements Runnable {

    @Override
    public void run() {
        System.out.println(ThreadColor.ANSI_CYAN + "hi from runnable");
    }
}
