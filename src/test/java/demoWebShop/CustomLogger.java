package demoWebShop;

import java.util.ArrayList;
import java.util.List;

public class CustomLogger {
    private static List<String> logMessages = new ArrayList<>();

    public static void log(String message) {
        logMessages.add(message);
        System.out.println(message);
    }

    public static List<String> getLogMessages() {
        return logMessages;
    }
}
