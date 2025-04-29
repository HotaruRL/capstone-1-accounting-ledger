import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        LocalDate firstDayofMonth = today.withDayOfMonth(1);
        System.out.println(firstDayofMonth);
        System.out.println(today.toEpochDay());
        System.out.println(firstDayofMonth.toEpochDay());
    }
}
