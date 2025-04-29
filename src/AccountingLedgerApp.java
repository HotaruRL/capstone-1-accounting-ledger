import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 ﻿
 Damous
 damous962

 Software engineer ♥️
 import java.io.*;
 import java.time.LocalDate;
 import java.time.LocalTime;
 import java.util.*;

 /**
 * Accounting Ledger Application
 * Capstone 1 Project
 * Tracks deposits and payments, saves to transactions.csv, displays reports.
 */
public class AccountingLedgerApp {
    // A Transaction represents a deposit or a payment
    static class Transaction {
        private LocalDate date;
        private LocalTime time;
        private String description;
        private String vendor;
        private double amount;

        public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
            this.date = date;
            this.time = time;
            this.description = description;
            this.vendor = vendor;
            this.amount = amount;
        }

        // Create Transaction object from a line in CSV
        public static Transaction fromCSV(String line) {
            String[] parts = line.split("\\|");
            LocalDate date = LocalDate.parse(parts[0]);
            LocalTime time = LocalTime.parse(parts[1]);
            String description = parts[2];
            String vendor = parts[3];
            double amount = Double.parseDouble(parts[4]);
            return new Transaction(date, time, description, vendor, amount);
        }

        // Convert Transaction to CSV line
        public String toCSV() {
            return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
        }

        public LocalDate getDate() { return date; }
        public double getAmount() { return amount; }
        public String getVendor() { return vendor; }
        public String getDescription() { return description; }

        @Override
        public String toString() {
            return date + " " + time + " | " + description + " | " + vendor + " | $" + String.format("%.2f", amount);
        }
    }

    // Ledger class manages all transactions
    static class Ledger {
        private List<Transaction> transactions;
        private final String filePath = "transactions.csv";

        public Ledger() {
            transactions = new ArrayList<>();
            loadTransactions();
        }

        // Load existing transactions from CSV
        private void loadTransactions() {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while((line = reader.readLine()) != null) {
                    transactions.add(Transaction.fromCSV(line));
                }
            } catch (IOException e) {
                System.out.println("No existing transactions found. Starting fresh.");
            }
        }

        // Add new transaction and save it
        public void addTransaction(Transaction transaction) {
            transactions.add(transaction);
            saveTransaction(transaction);
        }

        // Save single transaction to CSV
        private void saveTransaction(Transaction transaction) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(transaction.toCSV());
                writer.newLine();
            } catch (IOException e) {
                System.out.println("Error saving transaction.");
            }
        }

        // Show all transactions (newest first)
        public void displayAll() {
            List<Transaction> reversed = new ArrayList<>(transactions);
            Collections.reverse(reversed);
            for (Transaction t : reversed) {
                System.out.println(t);
            }
        }

        // Show only deposits (positive amounts)
        public void displayDeposits() {
            List<Transaction> reversed = new ArrayList<>(transactions);
            Collections.reverse(reversed);
            for (Transaction t : reversed) {
                if (t.getAmount() > 0) {
                    System.out.println(t);
                }
            }
        }

        // Show only payments (negative amounts)
        public void displayPayments() {
            List<Transaction> reversed = new ArrayList<>(transactions);
            Collections.reverse(reversed);
            for (Transaction t : reversed) {
                if (t.getAmount() < 0) {
                    System.out.println(t);
                }
            }
        }

        // Show this month's transactions
        public void monthToDate() {
            LocalDate now = LocalDate.now();
            for (Transaction t : transactions) {
                if (t.getDate().getMonth() == now.getMonth() && t.getDate().getYear() == now.getYear()) {
                    System.out.println(t);
                }
            }
        }

        // Show last month's transactions
        public void previousMonth() {
            LocalDate now = LocalDate.now().minusMonths(1);
            for (Transaction t : transactions) {
                if (t.getDate().getMonth() == now.getMonth() && t.getDate().getYear() == now.getYear()) {
                    System.out.println(t);
                }
            }
        }

        // Show all transactions this year
        public void yearToDate() {
            int year = LocalDate.now().getYear();
            for (Transaction t : transactions) {
                if (t.getDate().getYear() == year) {
                    System.out.println(t);
                }
            }
        }

        // Show all transactions from last year
        public void previousYear() {
            int year = LocalDate.now().minusYears(1).getYear();
            for (Transaction t : transactions) {
                if (t.getDate().getYear() == year) {
                    System.out.println(t);
                }
            }
        }

        // Find transactions by vendor name
        public void searchByVendor(String vendorName) {
            for (Transaction t : transactions) {
                if (t.getVendor().equalsIgnoreCase(vendorName)) {
                    System.out.println(t);
                }
            }
        }
    }

    // Main program starts here
    private static Ledger ledger = new Ledger();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showHomeScreen();
            String choice = scanner.nextLine().trim().toUpperCase();
            switch (choice) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    ledgerScreen();
                    break;
                case "X":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Show main menu
    private static void showHomeScreen() {
        System.out.println("\n--- Home Screen ---");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment (Debit)");
        System.out.println("L) Ledger");
        System.out.println("X) Exit");
        System.out.print("Choose an option: ");
    }

    // Add a deposit transaction
    private static void addDeposit() {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        Transaction deposit = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);
        ledger.addTransaction(deposit);
        System.out.println("Deposit added!");
    }

    // Add a payment (debit) transaction
    private static void makePayment() {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        Transaction payment = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, -Math.abs(amount));
        ledger.addTransaction(payment);
        System.out.println("Payment added!");
    }

    // Go to ledger screen
    private static void ledgerScreen() {
        boolean viewing = true;
        while (viewing) {
            System.out.println("\n--- Ledger Screen ---");
            System.out.println("A) All Entries");
            System.out.println("D) Deposits Only");
            System.out.println("P) Payments Only");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim().toUpperCase();
            switch (choice) {
                case "A":
                    ledger.displayAll();
                    break;
                case "D":
                    ledger.displayDeposits();
                    break;
                case "P":
                    ledger.displayPayments();
                    break;
                case "R":
                    reportScreen();
                    break;
                case "H":
                    viewing = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Go to report screen
    private static void reportScreen() {
        boolean reporting = true;
        while (reporting) {
            System.out.println("\n--- Reports Screen ---");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    ledger.monthToDate();
                    break;
                case "2":
                    ledger.previousMonth();
                    break;
                case "3":
                    ledger.yearToDate();
                    break;
                case "4":
                    ledger.previousYear();
                    break;
                case "5":
                    System.out.print("Enter vendor name: ");
                    String vendor = scanner.nextLine();
                    ledger.searchByVendor(vendor);
                    break;
                case "0":
                    reporting = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
