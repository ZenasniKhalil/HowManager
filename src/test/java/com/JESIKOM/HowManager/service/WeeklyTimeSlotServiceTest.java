package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.WeeklyTimeSlot;
import com.JESIKOM.HowManager.repository.WeeklyTimeSlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WeeklyTimeSlotServiceTest {

    @InjectMocks
    private com.JESIKOM.HowManager.service.WeeklyTimeSlotService weeklyTimeSlotService;

    @Mock
    private WeeklyTimeSlotRepository weeklyTimeSlotRepository;

    private WeeklyTimeSlot currentTs;
    private final LocalTime beforeTime = LocalTime.of(0, 0);
    private final LocalTime afterTime = LocalTime.of(3, 0);
    private final LocalTime withinTime = LocalTime.of(1, 30);
    private final DayOfWeek beforeDate = DayOfWeek.MONDAY;
    private final DayOfWeek afterDate = DayOfWeek.WEDNESDAY;
    private final DayOfWeek sameDate = DayOfWeek.TUESDAY;

    @BeforeEach
    void setUp() {
        currentTs = new WeeklyTimeSlot(DayOfWeek.TUESDAY, LocalTime.of(1, 0), DayOfWeek.TUESDAY, LocalTime.of(2, 0));
    }

    @Test
    void mergeTest() {
        WeeklyTimeSlot nextTs = new WeeklyTimeSlot(sameDate, beforeTime, afterDate, beforeTime.plusMinutes(30));
        WeeklyTimeSlot ts = weeklyTimeSlotService.merge(currentTs, nextTs);

        assertEquals(currentTs.getStartDay(), ts.getStartDay());
        assertEquals(nextTs.getEndDay(), ts.getEndDay());
        assertEquals(nextTs.getStartTime(), ts.getStartTime());
        assertEquals(nextTs.getEndTime(), ts.getEndTime());
    }

    @Test
    void isBeforeTest() {
        assertFalse(weeklyTimeSlotService.isBefore(currentTs, sameDate, beforeTime));
        assertFalse(weeklyTimeSlotService.isBefore(currentTs, sameDate, withinTime));
        assertFalse(weeklyTimeSlotService.isBefore(currentTs, beforeDate, afterTime));
        assertTrue(weeklyTimeSlotService.isBefore(currentTs, afterDate, beforeTime));
        assertTrue(weeklyTimeSlotService.isBefore(currentTs, sameDate, afterTime));
    }

    @Test
    void isAfterTest() {
        assertTrue(weeklyTimeSlotService.isAfter(currentTs, sameDate, beforeTime));
        assertFalse(weeklyTimeSlotService.isAfter(currentTs, sameDate, withinTime));
        assertTrue(weeklyTimeSlotService.isAfter(currentTs, beforeDate, afterTime));
        assertFalse(weeklyTimeSlotService.isAfter(currentTs, afterDate, beforeTime));
        assertFalse(weeklyTimeSlotService.isAfter(currentTs, sameDate, afterTime));
    }

    @Test
    void isWithinTest() {
        assertFalse(weeklyTimeSlotService.isWithin(currentTs, afterDate, beforeTime));
        assertFalse(weeklyTimeSlotService.isWithin(currentTs, sameDate, afterTime));
        assertTrue(weeklyTimeSlotService.isWithin(currentTs, sameDate, withinTime));
        assertTrue(weeklyTimeSlotService.isWithin(currentTs, sameDate, currentTs.getStartTime()));
        assertTrue(weeklyTimeSlotService.isWithin(currentTs, sameDate, currentTs.getEndTime()));
    }

    @Test
    void overlapsWithTest() {
        WeeklyTimeSlot beforeAfter = new WeeklyTimeSlot(beforeDate, beforeTime, sameDate, afterTime);
        WeeklyTimeSlot beforeBefore = new WeeklyTimeSlot(beforeDate, afterTime, sameDate, beforeTime);
        WeeklyTimeSlot afterAfter = new WeeklyTimeSlot(sameDate, afterTime, afterDate, beforeTime);
        WeeklyTimeSlot beforeWithin = new WeeklyTimeSlot(beforeDate, beforeTime, sameDate, withinTime);
        WeeklyTimeSlot withinAfter = new WeeklyTimeSlot(sameDate, withinTime, sameDate, afterTime);
        WeeklyTimeSlot withinWithin = new WeeklyTimeSlot(sameDate, withinTime, sameDate, withinTime.plusMinutes(10));

        assertFalse(weeklyTimeSlotService.overlapsWith(currentTs, beforeBefore));
        assertFalse(weeklyTimeSlotService.overlapsWith(currentTs, afterAfter));
        assertTrue(weeklyTimeSlotService.overlapsWith(currentTs, beforeAfter));
        assertTrue(weeklyTimeSlotService.overlapsWith(currentTs, beforeWithin));
        assertTrue(weeklyTimeSlotService.overlapsWith(currentTs, withinAfter));
        assertTrue(weeklyTimeSlotService.overlapsWith(currentTs, withinWithin));
    }

    @Test
    void computeTimeTest() {
        WeeklyTimeSlot threeH = new WeeklyTimeSlot(DayOfWeek.MONDAY, beforeTime, DayOfWeek.MONDAY, afterTime);
        WeeklyTimeSlot twentyFiveHalf = new WeeklyTimeSlot(DayOfWeek.MONDAY, beforeTime, DayOfWeek.TUESDAY, withinTime);

        assertEquals(3.0, weeklyTimeSlotService.computeTime(threeH));
        assertEquals(25.5, weeklyTimeSlotService.computeTime(twentyFiveHalf));
    }
}