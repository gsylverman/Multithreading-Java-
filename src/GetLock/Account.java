package GetLock;

public class Account {
    private int amount = 10000;

    public static void transfer(Account account1, Account account2, int sum) {
        account1.withdraw(sum);
        account2.add(sum);
    }

    public void add(int sum) {
        amount += sum;
    }

    public int getAmount() {
        return amount;
    }

    public void withdraw(int sum) {
        amount -= sum;
    }
}
