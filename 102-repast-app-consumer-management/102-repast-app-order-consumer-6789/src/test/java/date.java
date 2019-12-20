import java.sql.Timestamp;
import java.util.Date;

public class date {
    public static void main(String[] args) {
        Date date = new Date();
        long time = date.getTime();
        Timestamp timestamp1 = new Timestamp(time);
        System.out.println(timestamp1);
        Long time1 = time - 600000;
        Timestamp timestamp = new Timestamp(time1);
        System.out.println(timestamp);

    }
}
