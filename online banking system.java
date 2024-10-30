import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class BankAccount {
    private String accountNumber;
    private String ownerName;
    private double balance;
    private ArrayList<String> history;

    public BankAccount(String accountNumber, String ownerName) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = 0.0;
        this.history = new ArrayList<>();
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            history.add("Deposited: $" + amount);
            System.out.println("Deposited: $" + amount);
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            history.add("Withdrew: $" + amount);
            System.out.println("Withdrew: $" + amount);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void transfer(BankAccount toAccount, double amount) {
        if (amount > 0 && amount <= balance) {
            this.withdraw(amount);
            toAccount.deposit(amount);
            history.add("Transferred: $" + amount + " to " + toAccount.accountNumber);
            System.out.println("Transferred: $" + amount + " to " + toAccount.ownerName);
        }
    }

    public void showHistory() {
        System.out.println("Transaction History:");
        for (String record : history) System.out.println(record);
    }

    public void updateName(String newName) {
        ownerName = newName;
        System.out.println("Owner name updated to " + newName);
    }
}

public class Main {
    private static final HashMap<String, BankAccount> accounts = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
    while (true) {
        System.out.println("\n1. Create Account 2. Deposit 3. Withdraw 4. Transfer 5. History 6. Update Name 7. Exit");
        System.out.print("Enter your choice: ");
        
        if (!scanner.hasNextInt()) {  // Check if the input is not an integer
            System.out.println("Invalid input. Please enter a number between 1 and 7.");
            scanner.next();  // Clear the invalid input
            continue;
        }

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                createAccount();
                break;
            case 2:
                performAction("deposit");
                break;
            case 3:
                performAction("withdraw");
                break;
            case 4:
                transferFunds();
                break;
            case 5:
                performAction("history");
                break;
            case 6:
                performAction("updateName");
                break;
            case 7:
                System.out.println("Goodbye!");
                return;
            default:
                System.out.println("Invalid choice. Please select a number between 1 and 7.");
                break;
        }
    }
}


    private static void createAccount() {
        System.out.print("Account Number: ");
        String accNum = scanner.next();
        System.out.print("Owner's Name: ");
        String name = scanner.next();
        accounts.put(accNum, new BankAccount(accNum, name));
        System.out.println("Account created.");
    }

    private static void performAction(String action) {
        BankAccount account = getAccount();
        if (account != null) {
            switch (action) {
                case "deposit":
                    account.deposit(amountInput("Deposit amount: "));
                    break;
                case "withdraw":
                    account.withdraw(amountInput("Withdraw amount: "));
                    break;
                case "history":
                    account.showHistory();
                    break;
                case "updateName":
                    System.out.print("New Owner's Name: ");
                    account.updateName(scanner.next());
                    break;
            }
        }
    }

    private static void transferFunds() {
        System.out.print("From Account Number: ");
        BankAccount fromAcc = getAccount();
        System.out.print("To Account Number: ");
        BankAccount toAcc = getAccount();
        if (fromAcc != null && toAcc != null) {
            fromAcc.transfer(toAcc, amountInput("Transfer amount: "));
        }
    }

    private static BankAccount getAccount() {
        System.out.print("Account Number: ");
        return accounts.get(scanner.next());
    }

    private static double amountInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextDouble();
    }
}
