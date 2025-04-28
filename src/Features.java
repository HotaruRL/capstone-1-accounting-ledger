import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Features {
    Features(){
    }
    //Scanner named in
    Scanner in = new Scanner(System.in);

    //Add Deposit Function//////////////////////////////////////////////////////////////////////////////////////////////
    public void addDeposit(){
        String choice;
        // Create BufferedWriter named bufWriter and use FileWriter to write to file in ""
        BufferedWriter bufWriter;
        try {
            bufWriter = new BufferedWriter(new FileWriter("transactions.csv", true));
        } catch (IOException e) {
            System.out.println("\nFile not found.\nPlease check the filename and try again!");
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
                System.out.println("\nFile could not be written on.\nPlease check the filename and try again!");
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
            System.out.println("\nBufferedWriter could not be closed. Please try again!");
        }
    } // End of addDeposit function ////////////////////////////////////////////////////////////////////////////////////

    //Make Payment Function/////////////////////////////////////////////////////////////////////////////////////////////
    public void makePayment(){
        String choice;
        // Create BufferedWriter named bufWriter and use FileWriter to write to file in ""
        BufferedWriter bufWriter;
        try {
            bufWriter = new BufferedWriter(new FileWriter("transactions.csv", true));
        } catch (IOException e) {
            System.out.println("\nFile not found.\nPlease check the filename and try again!");
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
                System.out.println("\nFile could not be written on.\nPlease check the filename and try again!");
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
            System.out.println("\nBufferedWriter could not be closed. Please try again!");
        }
    } // End of makePayment function //////////////////////////////////////////////////////////////////////////////////



    // LEDGER FUNCTIONS

    //Display ALL Function //////////////////////////////////////////////////////////////////////////////////////////
    public void displayAll() {
        // Create BufferedReader named bufReader and use FileReader to read the file in ""
        BufferedReader bufReader;
        try {
            bufReader = new BufferedReader(new FileReader("transactions.csv"));
            String input;
            while((input = bufReader.readLine()) != null){
                System.out.println(input);
            }
        } catch (IOException e) {
            System.out.println("\nFile could not be read.\nPlease check the filename and try again!");
            return;
        }
        try {
            // Close BufferedReader
            bufReader.close();
        } catch (IOException e) {
            System.out.println("\nBufferedReader could not be closed. Please try again!");
        }
    } // End of displayAll function //////////////////////////////////////////////////////////////////////////////////


    //Display ONLY Deposits Function //////////////////////////////////////////////////////////////////////////////////////////
    public void displayDeposit() {
        // Create BufferedReader named bufReader and use FileReader to read the file in ""
        BufferedReader bufReader;
        try {
            bufReader = new BufferedReader(new FileReader("transactions.csv"));
            String input;
            int line = 0;
            // Display 1st header line as it is
            System.out.println(input = bufReader.readLine());

            // Parse and display only transactions > 0 (deposits)
            while((input = bufReader.readLine()) != null){
                if (line++ == 0){
                    continue; //skip parsing 1st header line
                }
                String[] text = input.split("\\|");
                if(Double.parseDouble(text[4]) > 0) {
                    System.out.println(input);
                }

            }
        } catch (IOException e) {
            System.out.println("\nFile could not be read.\nPlease check the filename and try again!");
            return;
        }
        try {
            // Close BufferedReader
            bufReader.close();
        } catch (IOException e) {
            System.out.println("\nBufferedReader could not be closed. Please try again!");
        }
    } // End of displayDeposit function //////////////////////////////////////////////////////////////////////////////////

}



