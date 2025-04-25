import java.util.Scanner;

public class Ledger {

    //Scanner named in
    Scanner in = new Scanner(System.in);
    String userInput = in.nextLine();

    // Home Screen Menu
    public void homeScreen(){
        System.out.printf("""
                        -----------------------------------------------------------
                        ------------------------ Main Menu ------------------------
                        -----------------------------------------------------------
                        
                        Please enter one of the following letter (D, P, L, or X)
                        to run the corresponding task:
                        
                        D - Add Deposit
                        P - Make Payment (Debit)
                        L - Ledger
                        X - Exit
                        
                        """
        );
        while(userInput != "X"){
            switch (userInput){
                case "D":
                    System.out.println("\nTODO Add Deposit");
                    break;
                case "P":
                    System.out.println("\nTODO Make Payment (Debit)");
                    break;
                case "L":
                    System.out.println("\nTODO Ledger Screen");
                    break;
                default:
                    System.out.println("\nCommand not found. Please try again!");

            }// End of switch
        }// End of while loop for Home Screen Menu
        System.out.println("\nThank you for using this app! Goodbye!");
    }// End of Home Screen Menu
}// End of Ledger class
