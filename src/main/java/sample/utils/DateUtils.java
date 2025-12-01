package sample.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** 日付ユーティリティ */
public class DateUtils {

    /**
     * 文字列を日付に変換
     * 
     * @param date 日付文字列(yyyy-MM-dd)
     * @return LocalDate
     */
    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * 文字列を日時に変換
     * 
     * @param dateTime 日時文字列(yyyy-MM-dd HH:mm:ss)
     * @return LocalDateTime
     */
    public static LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
