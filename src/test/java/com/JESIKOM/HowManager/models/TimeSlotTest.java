package com.JESIKOM.HowManager.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeSlotTest {

    TimeSlot currentTs;
    LocalDateTime beforeTime=LocalDateTime.of(2020, 1, 1, 0, 0);
    LocalDateTime afterTime = LocalDateTime.of(2020, 1, 1, 3, 0);
    LocalDateTime withinTime = LocalDateTime.of(2020, 1, 1, 1, 30);
    LocalDate beforeDate = LocalDate.of(2019, 12, 31);
    LocalDate afterDate = LocalDate.of(2020, 1, 2);
    LocalDate sameDate = LocalDate.of(2020, 1, 1);
    @BeforeEach
            void setUp() {
        currentTs = new TimeSlot(LocalDateTime.of(2020, 1, 1, 1, 0), LocalDateTime.of(2020, 1, 1, 2, 0));
    }

    @Test
    void mergeTest(){
        TimeSlot nextTs = new TimeSlot(withinTime, afterTime);
        TimeSlot ts=TimeSlot.merge(currentTs,nextTs);
        assertEquals(currentTs.startDateTime,ts.startDateTime);
        assertEquals(nextTs.endDateTime,ts.endDateTime);
    }

    @Test
    void isBeforeLocalDateTimeTest() {
        assertFalse(currentTs.isBefore(withinTime));
        assertFalse(currentTs.isBefore(beforeTime));
        assertFalse(currentTs.isBefore(currentTs.startDateTime));
        assertTrue(currentTs.isBefore(afterTime));

    }

    @Test
    void isBeforeLocalDateTest() {
        assertFalse(currentTs.isBefore(sameDate));
        assertFalse(currentTs.isBefore(beforeDate));
        assertTrue(currentTs.isBefore(afterDate));
    }

    @Test
    void isAfterLocalDateTimeTest() {
        assertFalse(currentTs.isAfter(withinTime));
        assertTrue(currentTs.isAfter(beforeTime));
        assertFalse(currentTs.isAfter(currentTs.startDateTime));
        assertFalse(currentTs.isAfter(afterTime));
    }

    @Test
    void isAfterLocalDateTest() {
        assertFalse(currentTs.isAfter(sameDate));
        assertFalse(currentTs.isAfter(afterDate));
        assertTrue(currentTs.isAfter(beforeDate));
    }

    @Test
    void isWithinLocalDateTimeTest() {
        assertTrue(currentTs.isWithin(currentTs.startDateTime));
        assertTrue(currentTs.isWithin(currentTs.endDateTime));
        assertTrue(currentTs.isWithin(withinTime));
    }

    @Test
    void getWeekNumber() {
        int weeknumber= currentTs.getWeekNumber();
        assertEquals(1,weeknumber);
        TimeSlot ts1= new TimeSlot(LocalDateTime.of(2019,12,23,0,0),afterTime);
        assertEquals(52,ts1.getWeekNumber());

    }

    @Test
    void overlapsWith() {
        TimeSlot beforeAfter = new TimeSlot(beforeTime, afterTime);
        TimeSlot beforeBefore = new TimeSlot(beforeTime, beforeTime.plusMinutes(10));
        TimeSlot afterAfter = new TimeSlot(afterTime, afterTime.plusMinutes(10));
        TimeSlot beforeWithin = new TimeSlot(beforeTime,withinTime);
        TimeSlot withinAfter = new TimeSlot(withinTime,afterTime);
        TimeSlot withinWithin = new TimeSlot(withinTime,withinTime.plusMinutes(10));
        assertFalse(currentTs.overlapsWith(beforeBefore));
        assertFalse(currentTs.overlapsWith(afterAfter));
        assertTrue(currentTs.overlapsWith(beforeAfter));
        assertTrue(currentTs.overlapsWith(beforeWithin));
        assertTrue(currentTs.overlapsWith(withinAfter));
        assertTrue(currentTs.overlapsWith(withinWithin));
    }
}


