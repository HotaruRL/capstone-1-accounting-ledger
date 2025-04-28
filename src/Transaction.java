public class Transaction {
    private String date;
    private String time;
    private String description;
    private String vendor;
    private double amount;

    //Constructor
    Transaction(
            String date,
            String time,
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

    // Getters
    public String getDate(){
        return this.date;
    }
    public String getTime(){
        return this.time;
    }
    public String getDescription(){
        return this.description;
    }
    public String getVendor(){
        return this.vendor;
    }
    public double getAmount(){
        return this.amount;
    }

    // Setters

    public void setDate(String date) {
        this.date = date;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

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
