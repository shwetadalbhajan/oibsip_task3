import java.util.*;

public class Main{
    public static void main(String[] args) {
        // Initialize user and account
        ATMUser user = new ATMUser("123456", "7890");
        Account account = new Account(1000.0); // Initial balance

        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;

        // Prompt for user id and user pin
        while (!loggedIn) {
            System.out.print("Enter User ID: ");
            String userIdInput = scanner.nextLine();
            System.out.print("Enter User PIN: ");
            String userPinInput = scanner.nextLine();

            if (user.authenticate(userIdInput, userPinInput)) {
                loggedIn = true;
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        }

        // ATM operations
        while (loggedIn) {
            System.out.println("Choose an option:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    account.showTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter the withdrawal amount: ");
                    double withdrawalAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    if (account.withdraw(withdrawalAmount)) {
                        System.out.println("Withdrawal successful.");
                    } else {
                        System.out.println("Insufficient balance.");
                    }
                    break;
                case 3:
                    System.out.print("Enter the deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    account.deposit(depositAmount);
                    System.out.println("Deposit successful.");
                    break;
                case 4:
                    System.out.print("Enter the transfer amount: ");
                    double transferAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter the recipient's account number: ");
                    String recipientAccount = scanner.nextLine();
                    if (account.transfer(transferAmount, recipientAccount)) {
                        System.out.println("Transfer successful.");
                    } else {
                        System.out.println("Transfer failed. Check recipient's account number.");
                    }
                    break;
                case 5:
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Thank you for using the ATM. Goodbye!");
    }

    static class ATMUser {
        private final String userId;
        private final String userPin;

        public ATMUser(String userId, String userPin) {
            this.userId = userId;
            this.userPin = userPin;
        }

        public boolean authenticate(String userIdInput, String userPinInput) {
            return userId.equals(userIdInput) && userPin.equals(userPinInput);
        }
    }

    static class Account {
        private double balance;
        private List<String> transactionHistory;

        public Account(double initialBalance) {
            balance = initialBalance;
            transactionHistory = new ArrayList<>();
        }

        public void showTransactionHistory() {
            if (transactionHistory.isEmpty()) {
                System.out.println("No transaction history.");
            } else {
                System.out.println("Transaction History:");
                for (String transaction : transactionHistory) {
                    System.out.println(transaction);
                }
            }
        }

        public boolean withdraw(double amount) {
            if (amount > 0 && balance >= amount) {
                balance -= amount;
                transactionHistory.add("Withdraw: ₹" + amount);
                return true;
            }
            return false;
        }

        public void deposit(double amount) {
            if (amount > 0) {
                balance += amount;
                transactionHistory.add("Deposit: ₹" + amount);
            }
        }

        public boolean transfer(double amount, String recipientAccount) {
            if (amount > 0 && balance >= amount) {
                balance -= amount;
                transactionHistory.add("Transfer to " + recipientAccount + ": ₹" + amount);
                return true;
            }
            return false;
        }
    }
}
