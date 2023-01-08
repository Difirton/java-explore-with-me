package ru.practicum.utill;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {
    private static final String LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private DateTimeParser() {
    }

    public static LocalDateTime parseToLocalDateTime(String source) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN);
        return LocalDateTime.parse(source, formatter);
    }
}
