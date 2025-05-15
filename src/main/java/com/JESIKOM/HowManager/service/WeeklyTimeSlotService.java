
package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.WeeklyTimeSlot;
import com.JESIKOM.HowManager.repository.WeeklyTimeSlotRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;



@Service
public class WeeklyTimeSlotService {

    private final WeeklyTimeSlotRepository weeklyTimeSlotRepository;

    public WeeklyTimeSlotService(WeeklyTimeSlotRepository weeklyTimeSlotRepository) {
        this.weeklyTimeSlotRepository = weeklyTimeSlotRepository;
    }

    public List<WeeklyTimeSlot> getAllTimeSlots() {
        return weeklyTimeSlotRepository.findAll();
    }

    public Optional<WeeklyTimeSlot> getTimeSlotById(Long id) {
        return weeklyTimeSlotRepository.findById(id);
    }

    public WeeklyTimeSlot addTimeSlot(WeeklyTimeSlot timeSlot) throws IllegalArgumentException {
        if (!isValid(timeSlot))
            throw new IllegalArgumentException("Start time doit etre avant end time dans la semaine");
        return weeklyTimeSlotRepository.save(timeSlot);
    }

    public WeeklyTimeSlot updateTimeSlot(Long id, WeeklyTimeSlot newTimeSlot) throws IllegalArgumentException {
        if (!isValid(newTimeSlot))
            throw new IllegalArgumentException("Start time doit etre avant end time dans la semaine");
        return weeklyTimeSlotRepository.findById(id)
                .map(existingSlot -> {
                    existingSlot.setStartDay(newTimeSlot.getStartDay());
                    existingSlot.setEndDay(newTimeSlot.getEndDay());
                    existingSlot.setStartTime(newTimeSlot.getStartTime());
                    existingSlot.setEndTime(newTimeSlot.getEndTime());
                    return weeklyTimeSlotRepository.save(existingSlot);
                })
                .orElse(null) ;
    }

    public void deleteTimeSlot(Long id) {
        if (!weeklyTimeSlotRepository.existsById(id)) {
            throw new IllegalArgumentException("WeeklyTimeSlot avec ID " + id + " pas trouvé");
        }
        weeklyTimeSlotRepository.deleteById(id);
    }

    // ✅ Logique métier déplacée ici

    public boolean isValid(WeeklyTimeSlot slot){

            return(slot.getStartDay().compareTo(slot.getEndDay()) > 0 ||
                    (slot.getStartDay() == slot.getEndDay() && slot.getStartTime().isAfter(slot.getEndTime())));
            //throw new IllegalArgumentException("Start time must be before end time within the week");
    }

    public boolean isBefore(WeeklyTimeSlot slot, DayOfWeek day, LocalTime time) {
        return (slot.getEndDay().compareTo(day) < 0) || (day == slot.getEndDay() && (slot.getEndTime().isBefore(time)));
    }

    public boolean isAfter(WeeklyTimeSlot slot, DayOfWeek day, LocalTime time) {
        return (slot.getStartDay().compareTo(day) > 0) || (day == slot.getStartDay() && slot.getStartTime().isAfter(time));
    }

    public boolean isWithin(WeeklyTimeSlot slot, DayOfWeek day, LocalTime time) {
        return !isBefore(slot, day, time) && !isAfter(slot, day, time);
    }

    public boolean isFollowing( WeeklyTimeSlot slotFollower, WeeklyTimeSlot slotFollowed){
        return  slotFollower.getStartDay().equals(slotFollowed.getEndDay()) &&
                slotFollower.getStartTime().equals(slotFollowed.getEndTime());
    }
    public List<WeeklyTimeSlot> getByEmploye(Long employeId) {
        return weeklyTimeSlotRepository.findByPersonnelMatricule(employeId);
    }


    public boolean overlapsWith(WeeklyTimeSlot slot1, WeeklyTimeSlot slot2) {
        return  !(isFollowing(slot1,slot2)||isFollowing(slot2,slot1)) &&
                !(slot1.getEndDay().compareTo(slot2.getStartDay()) < 0 || slot1.getStartDay().compareTo(slot2.getEndDay()) > 0) &&
                !(slot1.getEndDay() == slot2.getStartDay() && slot1.getEndTime().isBefore(slot2.getStartTime())) &&
                !(slot1.getStartDay() == slot2.getEndDay() && slot1.getStartTime().isAfter(slot2.getEndTime()));
    }

    public float computeTime(WeeklyTimeSlot slot) {
        if (slot.getStartDay() == slot.getEndDay()) {
            long duration = Duration.between(slot.getStartTime(), slot.getEndTime()).toMinutes();
            return duration % 60 + ((float) duration - ((float) duration % 60)) / 60;
        } else {
            int counter = 0;
            DayOfWeek day = slot.getStartDay();
            while (day.compareTo(slot.getEndDay()) < 0) {
                counter++;
                day = day.plus(1);
            }
            long duration = Duration.between(LocalTime.of(0,0,0), slot.getEndTime()).toMinutes();
            float hourDuration = (float) duration / 60 ;
            return (float)(24.0 * counter) + hourDuration;
        }
    }

    public WeeklyTimeSlot merge(WeeklyTimeSlot ts1, WeeklyTimeSlot ts2) {
        if (ts1.getStartDay().compareTo(ts2.getEndDay()) > 0 || ts2.getStartDay().compareTo(ts1.getEndDay()) > 0) {
            throw new IllegalArgumentException("Cannot merge non-overlapping weekly time slots");
        }

        DayOfWeek newStartDay = ts1.getStartDay().compareTo(ts2.getStartDay()) < 0 ? ts1.getStartDay() : ts2.getStartDay();
        LocalTime newStartTime = ts1.getStartDay().equals(ts2.getStartDay()) ?
                (ts1.getStartTime().isBefore(ts2.getStartTime()) ? ts1.getStartTime() : ts2.getStartTime()) :
                (newStartDay.equals(ts1.getStartDay()) ? ts1.getStartTime() : ts2.getStartTime());

        DayOfWeek newEndDay = ts1.getEndDay().compareTo(ts2.getEndDay()) > 0 ? ts1.getEndDay() : ts2.getEndDay();
        LocalTime newEndTime = ts1.getEndDay().equals(ts2.getEndDay()) ?
                (ts1.getEndTime().isAfter(ts2.getEndTime()) ? ts1.getEndTime() : ts2.getEndTime()) :
                (newEndDay.equals(ts1.getEndDay()) ? ts1.getEndTime() : ts2.getEndTime());

        return new WeeklyTimeSlot(newStartDay, newStartTime, newEndDay, newEndTime);
    }
}

