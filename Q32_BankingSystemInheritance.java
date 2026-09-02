import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Account {
    protected String accountNumber;
    protected String holderName;
    protected double balance;

    public Account(String accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);
    public abstract String describe();
}

class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, String holderName, double balance, double interestRate) {
        super(accountNumber, holderName, balance);
        this.interestRate = interestRate;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        balance -= amount;
    }

    @Override
    public String describe() {
        return String.format("Savings Account (%s) - %s (Balance: $%.2f, Interest Rate: %.1f%%)",
                accountNumber, holderName, balance, interestRate);
    }
}

class CheckingAccount extends Account {
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, String holderName, double balance, double overdraftLimit) {
        super(accountNumber, holderName, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        balance -= amount;
    }

    @Override
    public String describe() {
        return String.format("Checking Account (%s) - %s (Balance: $%.2f, Overdraft Limit: $%.2f)",
                accountNumber, holderName, balance, overdraftLimit);
    }
}

public class Q32_BankingSystemInheritance {
    public static void main(String[] args) {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new SavingsAccount("123456789", "Jane Doe", 5000, 1.5));
        accounts.add(new CheckingAccount("987654321", "John Smith", 1000, 1000));

        Scanner sc = new Scanner(System.in);
        System.out.println("The accounts in the system are:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". " + accounts.get(i).describe());
        }

        System.out.print("\nPlease enter the number of the account you wish to perform a transaction on: ");
        int choice = sc.nextInt();
        Account selected = accounts.get(choice - 1);

        System.out.print("Please enter the transaction type (deposit/withdraw): ");
        String type = sc.next();
        System.out.print("Please enter the amount to " + type + ": ");
        double amount = sc.nextDouble();

        if (type.equalsIgnoreCase("deposit")) {
            selected.deposit(amount);
        } else {
            selected.withdraw(amount);
        }

        System.out.printf("Transaction successful. New balance: $%.2f%n", selected.balance);
        sc.close();
    }
}
