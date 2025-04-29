import java.io.*;
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
    public void addTransaction(boolean creditOrDebit){
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
            // Format the time to remove nano
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            // Create an object of transaction that has all the fields
            // Use LocalDate.now() to add Date of adding transaction in ISO format;
            // .toString() to convert it from Date to String
            // Use LocalTime.now to add Time of adding transaction;
            // .format(timeFormatter) to apply format above to remove nano
            Transaction transaction = new Transaction(
                    LocalDate.now().toString(), LocalTime.now().format(timeFormatter), "", "", 0);

            // Credit - Add Deposits
            if(creditOrDebit) {
                System.out.print("""
                        -----------------------------------------------------------
                        ----------------------- Add Deposit -----------------------
                        -----------------------------------------------------------
                        """
                );
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
            }

            // Dedit - Add Payments
            if(!creditOrDebit) {
                System.out.print("""
                        -----------------------------------------------------------
                        ----------------------- Make Payment ----------------------
                        -----------------------------------------------------------
                        """
                );
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
            }
                in.nextLine();
                choice = in.nextLine().toUpperCase().trim();
        } while (!choice.equals("X"));

        try {
            // Close BufferedWriter
            bufWriter.close();
        } catch (IOException e) {
            System.out.println("\nBufferedWriter could not be closed. Please try again!");
        }
    } // End of addTransaction function ////////////////////////////////////////////////////////////////////////////////////



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


    //Display ONLY Function //////////////////////////////////////////////////////////////////////////////////////////
    public void displayOnly(boolean depositOrPayment) {
        // depositOrPayment: true - display only deposits; false - display only payments
        // Create BufferedReader named bufReader and use FileReader to read the file in ""
        BufferedReader bufReader;
        try {
            bufReader = new BufferedReader(new FileReader("transactions.csv"));
            String input;
            int line = 0;
            // Display 1st header line as it is
            System.out.println(bufReader.readLine());

            // Parse and display only transactions > 0 (deposits)
            if(depositOrPayment) {
                while ((input = bufReader.readLine()) != null) {
                    if (line++ == 0) {
                        continue; //skip parsing 1st header line
                    }
                    String[] text = input.split("\\|");
                    if (Double.parseDouble(text[4]) > 0) {
                        System.out.println(input);
                    }
                }
            }else{
                while ((input = bufReader.readLine()) != null) {
                    if (line++ == 0) {
                        continue; //skip parsing 1st header line
                    }
                    String[] text = input.split("\\|");
                    if (Double.parseDouble(text[4]) < 0) {
                        System.out.println(input);
                    }
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
    } // End of displayOnly function //////////////////////////////////////////////////////////////////////////////////



    // REPORTS FUNCTIONS

    //Reports by Date Function //////////////////////////////////////////////////////////////////////////////////////////
    public void reportsByDate(int criteria){
        // Create BufferedReader named bufReader and use FileReader to read the file in ""
        BufferedReader bufReader;
        LocalDate today = LocalDate.now();
        try {
            bufReader = new BufferedReader(new FileReader("transactions.csv"));
            String input;
            int line = 0;
            // Display 1st header line as it is
            System.out.println(bufReader.readLine());
            while ((input = bufReader.readLine()) != null) {
                if (line++ == 0) {
                    continue;
                }
                String[] text = input.split("\\|");
                switch (criteria) {
                    case 1 -> {long todayForCalc = today.toEpochDay();
                        long firstDayOfMonth = today.withDayOfMonth(1).toEpochDay();
                        long inputDateForCalc = LocalDate.parse(text[0]).toEpochDay();
                        if (firstDayOfMonth >= inputDateForCalc && inputDateForCalc > todayForCalc) {
                            continue;
                        }System.out.println(input);
                    }
                    case 2 -> System.out.println("case 2");
                    default -> System.out.println("default");
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

    }
}



