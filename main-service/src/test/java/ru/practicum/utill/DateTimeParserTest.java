package ru.practicum.utill;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeParserTest {

    @Test
    @DisplayName("Test parse date-time from string")
    void testParseToLocalDateTime() {
        LocalDateTime parsedDateTime = DateTimeParser.parseToLocalDateTime("2222-11-15 10:20:30");
        assertEquals(LocalDateTime.of(2222, 11, 15, 10, 20, 30), parsedDateTime);
    }
}