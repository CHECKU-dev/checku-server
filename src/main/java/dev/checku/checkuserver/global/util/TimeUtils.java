package dev.checku.checkuserver.global.util;

import dev.checku.checkuserver.global.util.TimeTable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeUtils {

    public static String toStartHour(String period) {
        return TimeTable.from(period).getStartHour();
    }

    public static String toEndHour(String period) {
        return TimeTable.from(period).getEndHour();
    }

}
