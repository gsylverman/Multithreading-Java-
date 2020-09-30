package GetLock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Process {
    private Account account1 = new Account();
    private Account account2 = new Account();
    private Random random = new Random();
    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    private void getLock(Lock lock1, Lock lock2) {
        while (true) {
            boolean gotlocked1 = false;
            boolean gotlocked2 = false;
            try {
                gotlocked1 = lock1.tryLock();
                gotlocked2 = lock2.tryLock();
            } finally {
                if (gotlocked1 && gotlocked2) {
                    return;
                }
                if (gotlocked1) {
                    lock1.unlock();
                }
                if (gotlocked2) {
                    lock2.unlock();
                }
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void thread1() {
        for (int i = 0; i < 10000; i++) {
            getLock(lock1, lock2);
            try {
                Account.transfer(account1, account2, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void thread2() {
        for (int i = 0; i < 10000; i++) {
            getLock(lock1, lock2);
            try {
                Account.transfer(account2, account1, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void finished() {
        System.out.println("Account 1 balance: " + account1.getAmount());
        System.out.println("Account 2 balance: " + account2.getAmount());
        System.out.println("Total balance: " + (account1.getAmount() + account2.getAmount()));
    }
}
