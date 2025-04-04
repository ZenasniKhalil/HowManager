package com.JESIKOM.HowManager.service;
import java.time.LocalDate;
import java.time.temporal.IsoFields;


public class LocalDateService {

    static public int getWeekOfDate(LocalDate date) {
        return date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
    }

    static public int getWeekBasedYearOfDate(LocalDate date) {
        return date.get(IsoFields.WEEK_BASED_YEAR);
    }
}
