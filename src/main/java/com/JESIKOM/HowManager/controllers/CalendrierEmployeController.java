package com.JESIKOM.HowManager.controllers;

import com.JESIKOM.HowManager.models.Personnel;
import com.JESIKOM.HowManager.models.WeeklyTimeSlot;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CalendrierEmployeController {

    @FXML private FlowPane calendar;
    @FXML private Text month;
    @FXML private Text year;

    private YearMonth currentYearMonth;
    private Personnel employe;

    @FXML
    public void initialize() {
        currentYearMonth = YearMonth.now();

        // Pour les tests sans BDD
        employe = createFakeEmployeAvecSlots();
        updateCalendar();
    }

    public void setEmploye(Personnel employe) {
        this.employe = employe;
        updateCalendar();
    }

    @FXML
    public void backOneMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        updateCalendar();
    }

    @FXML
    public void forwardOneMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        updateCalendar();
    }

    private void updateCalendar() {
        calendar.getChildren().clear();
        month.setText(currentYearMonth.getMonth().toString());
        year.setText(String.valueOf(currentYearMonth.getYear()));

        LocalDate firstDayOfMonth = currentYearMonth.atDay(1);
        int daysInMonth = currentYearMonth.lengthOfMonth();

        for (int i = 0; i < daysInMonth; i++) {
            LocalDate currentDate = firstDayOfMonth.plusDays(i);
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

            List<WeeklyTimeSlot> slotsDuJour = getSlotsPourJour(employe.getWeeklyTimeSlots(), dayOfWeek);

            TextFlow dayCell = new TextFlow();
            dayCell.setPrefWidth(90);
            dayCell.setPrefHeight(90);
            dayCell.setStyle("-fx-border-color: #ccc; -fx-background-color: #f9f9f9;");

            // Date
            Text dateText = new Text(currentDate.getDayOfMonth() + "\n");
            dateText.setStyle("-fx-font-weight: bold;");
            dayCell.getChildren().add(dateText);

            for (WeeklyTimeSlot slot : slotsDuJour) {
                String line = employe.getNom() + " " + employe.getPrenom() +
                        ": " + slot.getStartTime() + " - " + slot.getEndTime() + "\n";
                Text slotText = new Text(line);
                dayCell.getChildren().add(slotText);
            }

            calendar.getChildren().add(dayCell);
        }
    }

    private List<WeeklyTimeSlot> getSlotsPourJour(List<WeeklyTimeSlot> slots, DayOfWeek jour) {
        List<WeeklyTimeSlot> res = new ArrayList<>();
        for (WeeklyTimeSlot slot : slots) {
            if (slot.getStartDay().equals(jour)) {
                res.add(slot);
            }
        }
        return res;
    }

    // 👇 Simulation pour test hors BDD
    private Personnel createFakeEmployeAvecSlots() {
        Personnel fakeEmploye = new Personnel();
        fakeEmploye.setNom("Dupont");
        fakeEmploye.setPrenom("Alice");

// Associe des créneaux horaires fictifs
        List<WeeklyTimeSlot> fakeSlots = new ArrayList<>();

        List<WeeklyTimeSlot> slots = new ArrayList<>();

        slots.add(new WeeklyTimeSlot(DayOfWeek.MONDAY, LocalTime.of(9, 0), DayOfWeek.MONDAY, LocalTime.of(12, 0)));
        slots.add(new WeeklyTimeSlot(DayOfWeek.TUESDAY, LocalTime.of(14, 0), DayOfWeek.TUESDAY, LocalTime.of(17, 0)));
        slots.add(new WeeklyTimeSlot(DayOfWeek.FRIDAY, LocalTime.of(10, 0), DayOfWeek.FRIDAY, LocalTime.of(13, 0)));



        fakeEmploye.setWeeklyTimeSlots(slots);
        return fakeEmploye;
    }
}
