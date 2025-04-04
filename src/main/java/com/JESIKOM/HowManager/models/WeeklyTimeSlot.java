package com.JESIKOM.HowManager.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
@Setter
@Entity
@Table(name = "weekly_time_slot")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyTimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name ="start_day")
    @Enumerated(EnumType.STRING)
    private DayOfWeek startDay;

    @Column(nullable = false,name ="start_time")
    private LocalTime startTime;

    @Column(nullable = false,name ="end_day")
    @Enumerated(EnumType.STRING)
    private DayOfWeek endDay;

    @Column(nullable = false,name ="end_time")
    private LocalTime endTime;

    public WeeklyTimeSlot(DayOfWeek startDay, LocalTime startTime, DayOfWeek endDay, LocalTime endTime) {
        this.startDay = startDay;
        this.startTime = startTime;
        this.endDay = endDay;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return startDay + " " + startTime + " to " + endDay + " " + endTime;
    }
}

