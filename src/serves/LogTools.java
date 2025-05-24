package serves;

import source.SingletonServerConfig;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogTools {
    private static LocalDateTime LOCAL_DATE_TIME;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static String DATE_TIME;

    public static void statusLog(String message) {
        System.out.println("СТАТУС: " + message);
        LOCAL_DATE_TIME = LocalDateTime.now();
        DATE_TIME = LOCAL_DATE_TIME.format(FORMATTER);
        try (FileWriter fileWriter = new FileWriter(SingletonServerConfig.SERVER_CONFIG.getLogPath(), true)) {
            fileWriter.write(DATE_TIME + " СТАТУС: " + message + "\n");
        } catch (IOException e) {
            exceptionLog("Невозможно произвести запись в файл.");
        }
    }

    public static void exceptionLog(String message) {
        System.out.println("\u001B[31m" + " ОШИБКА! " + message + "\u001B[0m");
        LOCAL_DATE_TIME = LocalDateTime.now();
        DATE_TIME = LOCAL_DATE_TIME.format(FORMATTER);
        try (FileWriter fileWriter = new FileWriter(SingletonServerConfig.SERVER_CONFIG.getLogPath(), true)) {
            fileWriter.write(DATE_TIME + " СОШИБКА ! " + message + "\n");
        } catch (IOException e) {
            exceptionLog("Невозможно произвести запись в файл.");
        }
    }

}
