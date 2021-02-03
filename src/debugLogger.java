import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class debugLogger {
    public enum colour {
        red,
        yellow,
        green,
        stock
    }

    public static void log(String msg, colour colour) {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss");
        String d = dt.format(dtf);

        switch (colour) {
            case red:
                System.out.println("[" + d + "]" + "\u001B[31m" + "\t[FATAL]: " + msg + "\u001B[0m");
                break;
            case yellow:
                System.out.println("[" + d + "]" + "\u001B[33m" + "\t[WARNING]: " + msg + "\u001B[0m");
                break;
            case green:
                System.out.println("[" + d + "]" + "\u001B[32m" + "\t[INFO]: " + msg + "\u001B[0m");
                break;
            case stock:
                System.out.println("[" + d + "]" + "\t: " + msg + "\u001B[0m");
                break;
            default:
                System.out.println("error: the debugger broke!");
                break;
        }
    }
}
