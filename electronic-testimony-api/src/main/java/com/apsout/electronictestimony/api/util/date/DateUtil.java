package com.apsout.electronictestimony.api.util.date;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    private DateUtil() {
        throw new IllegalStateException("Date utility class");
    }

    public static String build(Date date, String format) {
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return ldt.format(formatter);
    }

    public static String build(Timestamp date, String format) {
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return ldt.format(formatter);
    }

    public static String build(LocalDate date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }

    public static String today(String format) {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDate.format(formatter);
    }

    public static String moment(String format) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    public static long now() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("America/Los_Angeles"));
        return zonedDateTime.toInstant().toEpochMilli();
    }

    public static Timestamp cast(Date date) {
        return new Timestamp(date.getTime());
    }

    public static LocalDateTime cast2(Date date) {
        return new Timestamp(date.getTime()).toLocalDateTime();
    }

    public static boolean hasIntervalIn(Timestamp before, Timestamp after, Duration duration) {
        LocalDateTime ldtBefore = LocalDateTime.ofInstant(before.toInstant(), ZoneId.systemDefault());
        LocalDateTime ldtAfter = LocalDateTime.ofInstant(after.toInstant(), ZoneId.systemDefault());
        return ldtAfter.minus(duration).isBefore(ldtBefore);
    }
}
