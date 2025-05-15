package com.JESIKOM.HowManager.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "weekly_time_slot")

@NoArgsConstructor
@AllArgsConstructor
/**
 * Classe de créneau horaire
 */

public class WeeklyTimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "personnel_id", nullable = false)
    private Personnel personnel;

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }
    /**
     * Jour de debut (énuméré)
     */
    @Column(nullable = false,name ="start_day")
    @Enumerated(EnumType.STRING)
    private DayOfWeek startDay;
    /**
     * Heure de debut (h:min:sec)
     */
    @Column(nullable = false,name ="start_time")
    private LocalTime startTime;
    /**
     * Jour de fin (énuméré)
     */
    @Column(nullable = false,name ="end_day")
    @Enumerated(EnumType.STRING)
    private DayOfWeek endDay;
    /**
     * Heure de fin (h:min:sec)
     */
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

    public DayOfWeek getStartDay() {
        return startDay;
    }

    public void setStartDay(DayOfWeek startDay) {
        this.startDay = startDay;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public DayOfWeek getEndDay() {
        return endDay;
    }

    public void setEndDay(DayOfWeek endDay) {
        this.endDay = endDay;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}

