package dev.checku.checkuserver.global.util;


public class SubjectUtil {

    private SubjectUtil() {
    }

    public static Integer PAGE_SIZE = 10;

    public static Boolean isVacancy(String numberOfPeople) {
        String[] nums = numberOfPeople.split("/");

        int currentNumber = Integer.parseInt(nums[0]);
        int limitNumber = Integer.parseInt(nums[1]);

        // 0 / 0 -> 고려해봐야됨
        return currentNumber < limitNumber;
    }


}
