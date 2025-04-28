import java.util.Scanner;

public class Menu {
    // Scanner named in
    Scanner in = new Scanner(System.in);

    // Create an instance of Feature class to use its methods
    Features features = new Features();

    // Home Screen Menu ////////////////////////////////////////////////////////////////////////////////////////////////
    public void homeMenu(){
        String userInput = "";
        while(!userInput.equals("X")){
            System.out.print("""
                        -----------------------------------------------------------
                        ------------------------ Main Menu ------------------------
                        -----------------------------------------------------------
                        
                        [D] - Add Deposit
                        [P] - Make Payment (Debit)
                        [L] - Ledger
                        [X] - Exit
                        
                        Please enter one of these letters ([D], [P], [L], or [X])
                        to run the corresponding task:
                        """
            );
            userInput = in.nextLine().trim().toUpperCase();
            switch (userInput){
                case "D" -> features.addDeposit();
                case "P" -> features.makePayment();
                case "L" -> ledgerMenu();
                case "X" -> System.out.println("\nThank you for using this app!");
                default  -> System.out.println("\nCommand not found. Please try again!");

            }// End of switch
        }// End of while loop for Home Menu
        System.out.println("Goodbye!");
    }// End of Home Menu ////////////////////////////////////////////////////////////////////////////////////////

    // Ledger Menu /////////////////////////////////////////////////////////////////////////////////////////////////////

    public void ledgerMenu(){
        String userInput = "";
        while(!userInput.equals("X")){
            System.out.print("""
                        -----------------------------------------------------------
                        ------------------------ Ledger Menu ----------------------
                        -----------------------------------------------------------
                        
                        [A] - Display All Entries
                        [D] - Display ONLY Deposits
                        [P] - Display ONLY Payments
                        [R] - Run Reports + Custom Search
                        [H] - Home Menu
                        
                        Please enter one of these letters ([A], [D], [P], [R], or [X])
                        to run the corresponding task:
                        """
            );
            userInput = in.nextLine().trim().toUpperCase();
            switch (userInput){
                case "A" -> features.displayAll();
                case "D" -> System.out.println("TODO Display Deposits");
                case "P" -> System.out.println("TODO Display Payments");
                case "R" -> System.out.println("TODO Display Screen");
                case "H" -> homeMenu();
                default  -> System.out.println("\nCommand not found. Please try again!");

            }// End of switch
        }// End of while loop for Ledger Menu
    }// End of Ledger Menu ////////////////////////////////////////////////////////////////////////////////////////

//    Methods for Date
//    Plain Text//e.g. Previous Year
//    LocalDate today = LocalDate.now();
//    LocalDate firstDay = today.minusYears(1).with(firstDayOfYear());
//    LocalDate lastDay = today.minusYears(1).with(lastDayOfYear());
//    minusYears method and with method and firstDayOfYear() function are newish
}// End of Ledger class
