package serves;

public class LogTools {

    public static void statusLog(String message) {
        System.out.println("СТАТУС: " + message);
    }

    public static void exceptionLog(String message) {
        System.out.println("ОШИБКА! " + message);
    }
}
