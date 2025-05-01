import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Transaction {
    // Fields
    // ignore warning because I may want to access and edit these fields later on with setters
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
    public LocalDateTime getDateTime(){return LocalDateTime.of(this.date,this.time);}
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
    public String toStringDisplay(){
        return String.format(
                "%s  %-8s | %-20s | %-15s | %10.2f",
                this.date,
                this.time,
                this.description,
                this.vendor,
                this.amount
        );
    }
}
