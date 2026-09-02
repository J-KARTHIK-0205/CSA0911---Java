public class Q47_BankAccountClassConstructor {
    public static void main(String[] args) {
        BankAccount account = new BankAccount("ACC001", 1000);
        account.deposit(500);
        account.withdraw(200);
        System.out.println("Final balance: " + account.getBalance());
    }
}

class BankAccount {
    private String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited " + amount + ". New balance: " + balance);
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient funds.");
            return;
        }
        balance -= amount;
        System.out.println("Withdrew " + amount + ". New balance: " + balance);
    }

    public double getBalance() {
        return balance;
    }
}
