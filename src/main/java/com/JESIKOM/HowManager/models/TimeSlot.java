package com.JESIKOM.HowManager.models;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.server.DelegatingServerHttpResponse;
import jakarta.persistence.*;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class TimeSlot {
    public final LocalDateTime startDateTime;
    public final LocalDateTime endDateTime;



    @ManyToOne
    @JoinColumn(name = "weekly_time_slot_id", nullable = false)
    private WeeklyTimeSlot weeklyTimeSlot;

    private TimeSlot(TimeSlot ts1,TimeSlot ts2) {
        if(ts1.startDateTime.isBefore(ts2.startDateTime))
            this.startDateTime = ts1.startDateTime;
        else
            this.startDateTime = ts2.startDateTime;
        if (ts1.endDateTime.isAfter(ts2.endDateTime))
                this.endDateTime = ts1.endDateTime;
        else
            this.endDateTime = ts2.endDateTime;

    }
    public TimeSlot(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (startDateTime.isAfter(endDateTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public boolean isBefore(LocalDateTime dateTime) {
        return endDateTime.isBefore(dateTime);
    }

    public boolean isAfter(LocalDateTime dateTime) {
        return startDateTime.isAfter(dateTime);
    }

    public boolean isWithin(LocalDateTime dateTime) {
        return !isBefore(dateTime) && !isAfter(dateTime);
    }

    public boolean isBefore(LocalDate date) {
        return isBefore(date.atStartOfDay());
    }

    public boolean isAfter(LocalDate date) {
        return isAfter(date.atTime(LocalTime.MAX));
    }


    public  int getWeekNumber() {
        return startDateTime.toLocalDate().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
    }
    public boolean overlapsWith(TimeSlot other) {
        return !this.endDateTime.isBefore(other.startDateTime) && !this.startDateTime.isAfter(other.endDateTime);
    }
    static public TimeSlot merge(TimeSlot ts1, TimeSlot ts2) {
        return new TimeSlot(ts1,ts2);
    }

}