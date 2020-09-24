import javax.naming.InterruptedNamingException;

public class Threads {
    public static void main(String[] args) {
        System.out.println(ThreadColor.ANSI_BLUE + "hello from main thread");
        Thread anotherThread = new AnotherThread();
        anotherThread.start();
        new Thread() {
            @Override
            public void run() {
                System.out.println(ThreadColor.ANSI_RED + "Hello from the second thread");
            }
        }.start();

        System.out.println(ThreadColor.ANSI_BLUE + "Hello again from the main thread");
//        Thread someClassThread = new Thread(new SomeClass());
        Thread someClassThread = new Thread(() -> {
            System.out.println("Override");
            try {
                anotherThread.join(); //wait for another tread to finish before starting
                System.out.println("another thread terminated so I am running again");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        someClassThread.start();

        Thread someNewThred = new Thread(new SomeClass());
    }
}
