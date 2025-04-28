import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Features {
    Features(){
    }
    //Scanner named in
    Scanner in = new Scanner(System.in);

    //Add Deposit Function//////////////////////////////////////////////////////////////////////////////////////////////
    public void addDeposit(){
        String choice;
        // Create BufferWriter named bufWriter and use FileWriter to write to file in ""
        BufferedWriter bufWriter;
        try {
            bufWriter = new BufferedWriter(new FileWriter("transactions.csv", true));
        } catch (IOException e) {
            System.out.println("\nFile could not be read.\nPlease check the filename and try again!");
            return;
        }
        do {
            System.out.print("""
                    -----------------------------------------------------------
                    ----------------------- Add Deposit -----------------------
                    -----------------------------------------------------------
                    """
            );
            // Format the time to remove nano
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            // Create an object of transaction that has all the fields
            // Use LocalDate.now() to add Date of adding transaction in ISO format;
            // .toString() to convert it from Date to String
            // Use LocalTime.now to add Time of adding transaction;
            // .format(timeFormatter) to apply format above to remove nano
            Transaction transaction = new Transaction(
                    LocalDate.now().toString(), LocalTime.now().format(timeFormatter), "", "", 0);
            // Add description from user input
            System.out.println("\nPlease enter the deposit description: ");
            transaction.setDescription(in.nextLine().trim());
            // Add vendor from user input
            System.out.println("\nPlease enter the vendor: ");
            transaction.setVendor(in.nextLine().trim());
            // Add amount from user input
            System.out.println("\nPlease enter the amount: ");
            transaction.setAmount(in.nextDouble());
            // Use bufWriter to write to file
            try {
                bufWriter.write(transaction.toString());
                // Flush to force writing whatever it has
                bufWriter.flush();
            } catch (IOException e) {
                System.out.println("\nFile could not be read.\nPlease check the filename and try again!");
            }

            System.out.print("""
                    Do you want to add another deposit?
                    
                    [Any Key] - Add Another Deposit
                    [X] - Exit to Main Menu
                    
                    Please enter either [Any Key] or [X]:
                    """
            );
            in.nextLine();
            choice = in.nextLine().toUpperCase().trim();
        } while (!choice.equals("X"));
        try {
            // Close BufferedWriter
            bufWriter.close();
        } catch (IOException e) {
            System.out.println("\nFile could not be read.\nPlease check the filename and try again!");
        }
    } // Add of addDeposit function ////////////////////////////////////////////////////////////////////////////////////

    //Make Payment Function//////////////////////////////////////////////////////////////////////////////////////////////
    public void makePayment(){
        String choice;
        // Create BufferWriter named bufWriter and use FileWriter to write to file in ""
        BufferedWriter bufWriter;
        try {
            bufWriter = new BufferedWriter(new FileWriter("transactions.csv", true));
        } catch (IOException e) {
            System.out.println("\nFile could not be read.\nPlease check the filename and try again!");
            return;
        }
        do {
            System.out.print("""
                            -----------------------------------------------------------
                            ----------------------- Make Payment ----------------------
                            -----------------------------------------------------------
                            """
            );
            // Format the time to remove nano
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            // Create an object of transaction that has all the fields
            // Use LocalDate.now() to add Date of adding transaction in ISO format;
            // .toString() to convert it from Date to String
            // Use LocalTime.now to add Time of adding transaction;
            // .format(timeFormatter) to apply format above to remove nano
            Transaction transaction = new Transaction(
                    LocalDate.now().toString(), LocalTime.now().format(timeFormatter), "", "", 0);
            // Add description from user input
            System.out.println("\nPlease enter the payment description: ");
            transaction.setDescription(in.nextLine().trim());
            // Add vendor from user input
            System.out.println("\nPlease enter the vendor: ");
            transaction.setVendor(in.nextLine().trim());
            // Add amount from user input and apply negative sign
            System.out.println("\nPlease enter the amount: ");
            transaction.setAmount(-in.nextDouble());
            // Use bufWriter to write to file
            try {
                bufWriter.write(transaction.toString());
                // Flush to force writing whatever it has
                bufWriter.flush();
            } catch (IOException e) {
                System.out.println("\nFile could not be read.\nPlease check the filename and try again!");
            }

            System.out.print("""
                            Do you want to add another payment?

                            [Any Key] - Add Another Payment
                            [X] - Exit to Main Menu
                            
                            Please enter either [Any Key] or [X]:
                            """
            );
            in.nextLine();
            choice = in.nextLine().toUpperCase().trim();
        } while (!choice.equals("X"));
        try {
            // Close BufferedWriter
            bufWriter.close();
        } catch (IOException e) {
            System.out.println("\nFile could not be read.\nPlease check the filename and try again!");
        }
    } // Add of makePayment function ////////////////////////////////////////////////////////////////////////////////////


}



