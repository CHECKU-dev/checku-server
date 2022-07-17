package dev.checku.checkuserver.global.util.timeutils;

public class TimeUtils {

    private TimeUtils() {
    }

    public static String toStartHour(String period) {
        return TimeTable.from(period).getStartHour();
    }

    public static String toEndHour(String period) {
        return TimeTable.from(period).getEndHour();
    }

}
