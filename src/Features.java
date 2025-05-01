import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

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


    // ----------------------------------------------- UTILITIES METHODS -------------------------------------------- //
    // Create a new transaction entry from user input
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
    } // End of createEntry method

    // Read csv file and create an ArrayList of all transactions in reversed chronological order
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
                LocalTime timePart = LocalTime.parse(text[1]).truncatedTo(ChronoUnit.SECONDS);
                double amountPart = Double.parseDouble(text[4]);
                entries.add(new Transaction(datePart, timePart, text[2], text[3],amountPart));
            }
            bufReader.close();
        } catch (IOException e) {
            System.out.println("\nFile could not be read.\nPlease check the filename and try again!");
        }
        entries.sort(Comparator.comparing(Transaction::getDateTime));
        return new ArrayList<> (entries.reversed());
    } // End of readEntries method

    // Write to csv file
    public void writeToFile(Transaction t){
        try {
            bufWriter = new BufferedWriter(new FileWriter(filePath, true));
            bufWriter.write(t.toString());
            bufWriter.flush();
            bufWriter.close();
        } catch (IOException e) {
            System.out.println("\nFile not found.\nPlease double check the file Path and try again!");
        }
    } // End of writeToFile method
    // --------------------------------------------- UTILITIES METHODS ENDS ----------------------------------------- //

    // --------------------------------------------- HOME SCREEN FUNCTIONS ---------------------------------------------
    // Add transaction entry to cvs file; Auto add negative sign if enter from Make Payment option
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
    } // End of addTransaction Function
    // ------------------------------------------ HOME SCREEN FUNCTIONS ENDS ---------------------------------------- //

    // ----------------------------------------------- LEDGER FUNCTIONS ------------------------------------------------
    // Display ALL transaction entries
    public void displayAll() {
        for(Transaction e : readEntries()){
            System.out.println(e.toString());
        }
    } // End of displayAll function

    // Display either Deposit ONLY or Payment ONLY depends on which option chose in Ledger Menu
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
    } // End of displayOnly function
    // -------------------------------------------- LEDGER FUNCTIONS ENDS ----------------------------------------------

    // -------------------------------------------- REPORTS FUNCTIONS --------------------------------------------------
    // Display entries that match Date criteria chosen in Reports Menu
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
    }// End of reportByDate function
    // ----------------------------------------- REPORTS FUNCTIONS ENDS ------------------------------------------------

    // ------------------------------------------------- CUSTOM SEARCH -------------------------------------------------
    // A set of methods to compare entries' values vs. active criteria's values
    // Data types are converted here for comparisons
    public boolean isValidStartDate(String criteriaValue, LocalDate entryValue){
        return entryValue.toEpochDay() >= LocalDate.parse(criteriaValue).toEpochDay();
    }
    public boolean isValidEndDate(String criteriaValue, LocalDate entryValue){
        return entryValue.toEpochDay() <= LocalDate.parse(criteriaValue).toEpochDay();
    }
    public boolean isValidStringExact(String criteriaValue, String entryValue){
        return entryValue.equalsIgnoreCase(criteriaValue);
    }
    public boolean isValidStringContains(String criteriaValue, String entryValue){
        return entryValue.contains(criteriaValue);
    }
    public boolean isValidAmount(String criteriaValue, double entryValue){
        return Double.parseDouble(criteriaValue) == entryValue;
    }

    // Method to make sure entry meets all active criteria (Default: true; if one criterion doesn't match, return false)
    // Pass in only original data
    public boolean meetAllCriteria(String key, String criteriaValue, Transaction entry){
        if (("Start Date").equalsIgnoreCase(key)){
            if (!isValidStartDate(criteriaValue, entry.getDate())) {
                return false;
            }
        }
        if (("End Date").equalsIgnoreCase(key)) {
            if (!isValidEndDate(criteriaValue, entry.getDate())) {
                return false;
            }
        }
        if (("Description").equalsIgnoreCase(key)) {
            if (!isValidStringExact(criteriaValue, entry.getDescription())) {
                return false;
            }
        }
        if (("Vendor").equalsIgnoreCase(key)) {
            if (!isValidStringExact(criteriaValue, entry.getVendor())) {
                return false;
            }
        }
        if (("Amount").equalsIgnoreCase(key)) {
            if (!isValidAmount(criteriaValue, entry.getAmount())) {
                return false;
            }
        }
        return true;
    } // End of meetAllCriteria method


    // Display entries that match one or multiple criteria's values entered by user
    public void reportByCriteria(){
        // HashMap for Search Criteria and their values
        HashMap<String, String> criteria = new HashMap<>();
        System.out.print("""
                    -----------------------------------------------------------
                    ----------------------- Custom Search ---------------------
                    -----------------------------------------------------------
                    
                    Search transactions by one or more of the following criteria
                    
                    o Start Date
                    o End Date
                    o Description
                    o Vendor
                    o Amount
                    
                    Please enter values for those criteria as they are prompted.
                    If you want to skip a criteria, just hit [Enter] key.
                    """
        );
        System.out.print("\nPlease enter Start Date using this format YYYY-MM-DD: ");
        criteria.put("Start Date", in.nextLine().trim()) ;
        System.out.print("\nPlease enter End Date using this format YYYY-MM-DD: ");
        criteria.put("End Date", in.nextLine().trim()) ;
        System.out.print("\nPlease enter Description: ");
        criteria.put("Description", in.nextLine().trim()) ;
        System.out.print("\nPlease enter Vendor: ");
        criteria.put("Vendor", in.nextLine().trim()) ;
        System.out.print("\nPlease enter Amount: ");
        criteria.put("Amount", in.nextLine().trim()) ;

        // Loop through each transaction to check
        for (Transaction e : readEntries()) {
            boolean match = true; // assume all transactions match search criteria before filtering
            // Check if there are blank criteria to skip
            for (String key : criteria.keySet()) { // loop through each criterion in HashMap
                String criteriaValue = criteria.get(key); // get value from each criterion
                if (!criteriaValue.isEmpty()) {
                    match = meetAllCriteria(key, criteriaValue, e);
                    if (!match){
                        break;
                    }
                }
            }
            if (match){
                System.out.println(e);
            }
        }
    }// End of reportByCriteria function
    // ---------------------------------------------- CUSTOM SEARCH ENDS -----------------------------------------------
}



