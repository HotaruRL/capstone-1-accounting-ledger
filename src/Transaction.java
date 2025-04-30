import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    // Fields
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    //Constructor
    Transaction(
            LocalDate date,
            LocalTime time,
            String description,
            String vendor,
            double amount
    ){
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    // Methods
    public LocalDate getDate(){return this.date;}
    public String getDescription(){return this.description;}
    public String getVendor(){return this.vendor;}
    public double getAmount(){return this.amount;}

    public String toString(){
        return String.format(
                "\n%s|%s|%s|%s|%.2f",
                this.date,
                this.time,
                this.description,
                this.vendor,
                this.amount
        );
    }
}
