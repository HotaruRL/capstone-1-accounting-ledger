import java.util.Scanner;

public class Ledger {
    // Scanner named in
    Scanner in = new Scanner(System.in);

    // Create an instance of Feature class to use its methods
    Features features = new Features();

    // Home Screen Menu
    public void homeScreen(){
        String userInput = "";
        while(!userInput.equals("X")){
            System.out.print("""
                        -----------------------------------------------------------
                        ------------------------ Main Menu ------------------------
                        -----------------------------------------------------------
                        
                        D - Add Deposit
                        P - Make Payment (Debit)
                        L - Ledger
                        X - Exit
                        
                        Please enter one of these letters (D, P, L, or X)
                        to run the corresponding task:
                        """
            );
            userInput = in.nextLine().toUpperCase();
            switch (userInput){
                case "D":
                    features.addDeposit();
                    break;
                case "P":
                    features.makePayment();
                    break;
                case "L":
                    System.out.println("\nTODO Ledger Screen");
                    break;
                case "X":
                    System.out.println("\nThank you for using this app!");
                    break;
                default:
                    System.out.println("\nCommand not found. Please try again!");

            }// End of switch
        }// End of while loop for Home Screen Menu
        System.out.println("Goodbye!");
    }// End of Home Screen Menu
}// End of Ledger class
