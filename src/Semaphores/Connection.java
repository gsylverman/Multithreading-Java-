package Semaphores;

import java.util.concurrent.Semaphore;

public class Connection {
    private static Connection connection = new Connection();
    Semaphore semaphore = new Semaphore(10, true);
    private int connections = 0;

    private Connection() {

    }

    public static Connection getInstance() {
        return connection;
    }

    public void connect() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            doConnect();
        } finally {
            semaphore.release();
        }
    }

    private void doConnect() {
        synchronized (this) {
            connections++;
            System.out.println(connections);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this) {
            connections--;
        }
    }
}
