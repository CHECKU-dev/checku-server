package dev.checku.checkuserver.global.util;

import dev.checku.checkuserver.domain.temp.PortalSubjectResponse;
import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class PortalResponseParser {

    private static final Pattern TIME_PERIOD_PATTERN = Pattern.compile("\\d{2}-\\d{2}");

    public static String convertPeriodsToHours(String timeAndPlace) {
        if (timeAndPlace == null) {
            return null;
        }

        final StringBuilder newTimeAndPlace = new StringBuilder();
        final Matcher matcher = TIME_PERIOD_PATTERN.matcher(timeAndPlace);
        while (matcher.find()) {
            String[] periodArr = matcher.group().split("-");
            String replacement = TimeUtils.toStartHour(periodArr[0]) + "-" + TimeUtils.toEndHour(periodArr[1]);
            matcher.appendReplacement(newTimeAndPlace, replacement);
        }
        matcher.appendTail(newTimeAndPlace);

        return newTimeAndPlace.toString().replaceAll("\\([^()]+\\)", "").trim();
    }

    public static String extractEmptySeat(PortalSubjectResponse.SubjectDetail subjectDetail) {
        String[] people = subjectDetail.getNumberOfPeople().split("/");
        String current = people[0];
        String total = people[1];
        return String.valueOf(Integer.parseInt(total) - Integer.parseInt(current));
    }
}
