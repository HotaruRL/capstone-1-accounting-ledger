import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Features {
    // File path to read and write
    String filePath = "transactions.csv";

    // Scanner named in
    Scanner in = new Scanner(System.in);
    // Create BufferedReader named bufReader
    BufferedReader bufReader;
    // Buffered Writer name bufWriter
    BufferedWriter bufWriter;

    // Constructor
    Features(){}


    // REUSABLE METHODS
    // Create a new entry
    public Transaction createEntry(boolean creditOrDebit) {
        // Add description from user input
        System.out.println("\nPlease enter the description: ");
        String description = in.nextLine().trim();
        // Add vendor from user input
        System.out.println("\nPlease enter the vendor: ");
        String vendor = in.nextLine().trim();
        // Add amount from user input
        System.out.println("\nPlease enter the amount: ");
        double amount;
        if (creditOrDebit) {
            amount = in.nextDouble();
        }else {
            amount = -in.nextDouble();
        }
        return new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);
    }

    // Reader
    public ArrayList<Transaction> readEntries() {
        ArrayList<Transaction> entries = new ArrayList<>();
        try {
            bufReader = new BufferedReader(new FileReader(filePath));
            String input;
            // Display 1st header line as it is
            System.out.println(bufReader.readLine());
            // Go through each line, parse, create Transaction object, and add to entries ArrayList
            while ((input = bufReader.readLine()) != null) {
                String[] text = input.split("\\|"); // split entry into parts
                // Convert parts into appropriate data type
                LocalDate datePart = LocalDate.parse(text[0]);
                LocalTime timePart = LocalTime.parse(text[1]);
                double amountPart = Double.parseDouble(text[4]);
                entries.add(new Transaction(datePart, timePart, text[2], text[3],amountPart));
            }
            bufReader.close();
        } catch (IOException e) {
            System.out.println("\nFile could not be read.\nPlease check the filename and try again!");
        }
        return entries;
    }

    // Writer
    public void writeToFile(Transaction t){
        try {
            bufWriter = new BufferedWriter(new FileWriter(filePath, true));
            bufWriter.write(t.toString());
            bufWriter.flush();
            bufWriter.close();
        } catch (IOException e) {
            System.out.println("\nFile not found.\nPlease double check the file Path and try again!");
        }
    }



    // HOME SCREEN FUNCTIONS
    // addTransaction Function//////////////////////////////////////////////////////////////////////////////////////////
    public void addTransaction(boolean creditOrDebit){
        String choice;
        do {
            System.out.print("""
                        -----------------------------------------------------------
                        -------------------- Add Transactions ---------------------
                        -----------------------------------------------------------
                        """
            );
            // Credit - Add Deposits
            if(creditOrDebit) {
                writeToFile(createEntry(true));
            }
            // Debit - Add Payments
            if(!creditOrDebit) {
                writeToFile(createEntry(false));
            }
            System.out.print("""
                        Do you want to add another transaction?
                        
                        [Any Key] - Add Another Transaction
                        [X] - Exit to Main Menu
                        
                        Please enter either [Any Key] or [X]:
                        """
            );
            in.nextLine();
            choice = in.nextLine().trim();
        } while (!choice.equalsIgnoreCase("X"));
    } // End of addTransaction Function ////////////////////////////////////////////////////////////////////////////////



    // LEDGER FUNCTIONS
    // displayAll Function /////////////////////////////////////////////////////////////////////////////////////////////
    public void displayAll() {
        for(Transaction e : readEntries()){
            System.out.println(e.toString());
        }
    } // End of displayAll function //////////////////////////////////////////////////////////////////////////////////


    // displayOnly Function //////////////////////////////////////////////////////////////////////////////////////////
    public void displayOnly(boolean depositOrPayment) {
        // depositOrPayment: true - display only deposits; false - display only payments
        for (Transaction e : readEntries()) {
            if (depositOrPayment && e.getAmount() > 0) {
                System.out.println(e);
            }
            if (!depositOrPayment && e.getAmount() < 0){
                System.out.println(e);
            }
        }
    } // End of displayOnly function //////////////////////////////////////////////////////////////////////////////////



    // REPORTS FUNCTIONS

    // reportByDate Function ///////////////////////////////////////////////////////////////////////////////////////////
    public void reportByDate(int criteria){
        LocalDate today = LocalDate.now();
        int previousMonth = today.getMonthValue() - 1;
        int previousYear = today.getYear() - 1;
        for (Transaction e : readEntries()) {
            switch (criteria) {
                // Month to Date
                case 1 -> {
                    long todayInEpoch = today.toEpochDay();
                    long firstDayOfMonth = today.withDayOfMonth(1).toEpochDay();
                    long inputDateForCalc = e.getDate().toEpochDay();
                    if (inputDateForCalc < firstDayOfMonth || inputDateForCalc >= todayInEpoch) {
                        continue;
                    }
                    System.out.println(e);
                }
                // Previous Month
                case 2 -> {
                    if (e.getDate().getMonthValue() == previousMonth && e.getDate().getYear() == today.getYear()){
                        System.out.println(e);
                    }
                }
                // Year to Date
                case 3 -> {
                    if (e.getDate().getYear() == today.getYear()){
                        System.out.println(e);
                    }
                }
                case 4 ->{
                    if (e.getDate().getYear() == previousYear){
                        System.out.println(e);
                    }
                }
                default -> {return;}
            }
        }
    }// End of reportByDate function ///////////////////////////////////////////////////////////////////////////////////


    // reportByCriteria Function ///////////////////////////////////////////////////////////////////////////////////////
    public void reportByCriteria(String criteria){
        HashMap<String, Transaction> lookUpTable = new HashMap<>();


        for (Transaction e : readEntries()){

        }
    }
}



