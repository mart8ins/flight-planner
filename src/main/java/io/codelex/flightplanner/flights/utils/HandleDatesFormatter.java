package io.codelex.flightplanner.flights.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HandleDatesFormatter {

    private static String dateTimePattern = "yyyy-MM-dd HH:mm";
    private static String datePattern = "yyyy-MM-dd";

    public static LocalDateTime formatStringToDateTime(String dateTime) {
        DateTimeFormatter departureTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);
        return LocalDateTime.parse(dateTime, departureTimeFormatter);
    }

    public static LocalDate formatStringToDate(String date) {
        DateTimeFormatter departureTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        return LocalDate.parse(date, departureTimeFormatter);
    }

    public static String formatLocalDateTimeToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
        return dateTime.format(formatter);
    }
}
