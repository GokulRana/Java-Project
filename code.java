import java.util.*;

class Account {
    private String accountNumber;
    private String customerName;
    private String accountType;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(String accountNumber, String customerName, String accountType) {
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.accountType = accountType;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getcustomerName() {
        return customerName;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
            return true;
        } else {
            System.out.println("Insufficient balance.");
            return false;
        }
    }
}

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

public class BankManagementApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Account> accounts = new ArrayList<>();

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Display All Users");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    registerAccount(accounts, scanner);
                    break;

                case 2:
                    login(accounts, scanner);
                    break;

                case 3:
                    DisplayAllusers(accounts);
                    break;

                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
    }

    public static void registerAccount(List<Account> accounts, Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        if (isAccountNumberTaken(accounts, accountNumber)) {
            System.out.println("Account number already exists. Please choose a different account number.");
            return;
        }
        System.out.print("Enter customername: ");
        String customerName = scanner.nextLine();
        System.out.print("Enter account type: ");
        String accountType = scanner.nextLine();
        Account newAccount = new Account(accountNumber, customerName, accountType);
        accounts.add(newAccount);
        System.out.println("Registration successful.");
    }

    public static void login(List<Account> accounts, Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        Account loggedInAccount = findAccountByAccountNumber(accounts, accountNumber);
        if (loggedInAccount != null) {
            System.out.println("Login successful. Welcome, " + loggedInAccount.getcustomerName());
            handleAccountOptions(loggedInAccount, scanner);
        } else {
            System.out.println("Account not found. Please enter a valid account number.");
        }
    }

    public static boolean isAccountNumberTaken(List<Account> accounts, String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return true;
            }
        }
        return false;
    }

    public static Account findAccountByAccountNumber(List<Account> accounts, String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public static void DisplayAllusers(List<Account> accounts) {
        System.out.println("List of all registered users:");
        for (Account account : accounts) {
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("customerName: " + account.getcustomerName());
            System.out.println("Account Type: " + account.getAccountType());
            System.out.println("Balance: $" + account.getBalance());
            System.out.println();
        }
    }

    public static void handleAccountOptions(Account account, Scanner scanner) {
        while (true) {
            System.out.println("1. View Account Details");
            System.out.println("2. Check Transaction History");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Account Type: " + account.getAccountType());
                    System.out.println("Balance: $" + account.getBalance());
                    break;

                case 2:
                    List<Transaction> transactions = account.getTransactionHistory();
                    System.out.println("Transaction History:");
                    for (Transaction transaction : transactions) {
                        System.out.println(transaction.getType() + " - $" + transaction.getAmount());
                    }
                    break;

                case 3:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    System.out.println("Deposit successful.");
                    break;

                case 4:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = scanner.nextDouble();
                    account.withdraw(withdrawalAmount);
                    break;

                case 5:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
    }
}
