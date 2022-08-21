package dev.checku.checkuserver.global.util.timeutils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TimeTable {

    T00("00", "08:00", "09:00"),
    T01("01", "09:00", "09:30"),
    T02("02", "09:30", "10:00"),
    T03("03", "10:00", "10:30"),
    T04("04", "10:30", "11:00"),
    T05("05", "11:00", "11:30"),
    T06("06", "11:30", "12:00"),
    T07("07", "12:00", "12:30"),
    T08("08", "12:30", "13:00"),
    T09("09", "13:00", "13:30"),
    T10("10", "13:30", "14:00"),
    T11("11", "14:00", "14:30"),
    T12("12", "14:30", "15:00"),
    T13("13", "15:00", "15:30"),
    T14("14", "15:30", "16:00"),
    T15("15", "16:00", "16:30"),
    T16("16", "16:30", "17:00"),
    T17("17", "17:00", "17:30"),
    T18("18", "17:30", "18:00"),
    T19("19", "18:00", "18:30"),
    T20("20", "18:30", "19:00"),
    T21("21", "19:00", "19:30"),
    T22("22", "19:30", "20:00"),
    T23("23", "20:00", "20:30");

    private final String period;
    private final String startHour;
    private final String endHour;

    public static TimeTable from(String period) {
        return Arrays.stream(TimeTable.values())
                .filter(timeTable -> timeTable.getPeriod().equals(period))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
