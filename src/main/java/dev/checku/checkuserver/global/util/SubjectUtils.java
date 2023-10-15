package dev.checku.checkuserver.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubjectUtils {

    public static Integer PAGE_SIZE = 10;

    public static Boolean hasVacancy(String numberOfPeople) {
        String[] nums = numberOfPeople.split("/");

        int currentNumber = Integer.parseInt(nums[0]);
        int limitNumber = Integer.parseInt(nums[1]);

        // 0 / 0 -> 고려해봐야됨
        return currentNumber < limitNumber;
    }
}
