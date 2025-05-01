import java.time.LocalDate;
import java.util.Scanner;

public class Menu {
    // Scanner named in
    Scanner in = new Scanner(System.in);

    // Create an instance of Feature class to use its methods
    Features features = new Features();

    // Reports Menu /////////////////////////////////////////////////////////////////////////////////////////////////////

    public void reportsMenu(){
        int userInput;
        while(true){
            System.out.print("""
                        -----------------------------------------------------------
                        ------------------------ Reports Menu ---------------------
                        -----------------------------------------------------------
                        
                        [1] - Month To Date
                        [2] - Previous Month
                        [3] - Year To Date
                        [4] - Previous Year
                        [5] - Custom Search by Criteria
                        [0] - Go Back to Ledger Menu
                        
                        Please enter one of the numbers ([1], [2], [3], [4], [5],or [0])
                        to run the corresponding task:
                        """
            );
            userInput = in.nextInt();
            String _ = in.nextLine();
            switch (userInput){
                case 1 -> features.reportByDate(1);
                case 2 -> features.reportByDate(2);
                case 3 -> features.reportByDate(3);
                case 4 -> features.reportByDate(4);
                case 5 -> features.reportByCriteria();
                case 0 -> {return;}
                default  -> System.out.println("\nCommand not found. Please try again!");

            }// End of switch
        }// End of while loop for Reports Menu
    }// End of Reports Menu ////////////////////////////////////////////////////////////////////////////////////////////


    // Ledger Menu /////////////////////////////////////////////////////////////////////////////////////////////////////

    public void ledgerMenu(){
        String userInput = "";
        while(!userInput.equalsIgnoreCase("H")){
            System.out.print("""
                        -----------------------------------------------------------
                        ------------------------ Ledger Menu ----------------------
                        -----------------------------------------------------------
                        
                        [A] - Display All Entries
                        [D] - Display ONLY Deposits
                        [P] - Display ONLY Payments
                        [R] - Run Reports + Custom Search
                        [H] - Go Back to Home Menu
                        
                        Please enter one of these letters ([A], [D], [P], [R], or [H])
                        to run the corresponding task:
                        """
            );
            userInput = in.nextLine().trim().toUpperCase();
            switch (userInput){
                case "A" -> features.displayAll();
                case "D" -> features.displayOnly(true);
                case "P" -> features.displayOnly(false);
                case "R" -> reportsMenu();
                case "H" -> {return;}
                default  -> System.out.println("\nCommand not found. Please try again!");

            }// End of switch
        }// End of while loop for Ledger Menu
    }// End of Ledger Menu /////////////////////////////////////////////////////////////////////////////////////////////


    // Home Screen Menu ////////////////////////////////////////////////////////////////////////////////////////////////
    public void homeMenu(){
        String userInput = "";
        while(!userInput.equalsIgnoreCase("X")){
            System.out.print("""
                        -----------------------------------------------------------
                        ------------------------ Main Menu ------------------------
                        -----------------------------------------------------------
                        
                        [D] - Add Deposit (Credit)
                        [P] - Make Payment (Debit)
                        [L] - Show Ledger Menu
                        [X] - Exit
                        
                        Please enter one of these letters ([D], [P], [L], or [X])
                        to run the corresponding task:
                        """
            );
            userInput = in.nextLine().trim().toUpperCase();
            switch (userInput){
                case "D" -> features.addTransaction(true);
                case "P" -> features.addTransaction(false);
                case "L" -> ledgerMenu();
                case "X" -> System.out.println("\nThank you for using this app!");
                default  -> System.out.println("\nCommand not found. Please try again!");

            }// End of switch
        }// End of while loop for Home Menu
        System.out.println("Goodbye!");
    }// End of Home Menu ///////////////////////////////////////////////////////////////////////////////////////////////
}// End of Menu class
