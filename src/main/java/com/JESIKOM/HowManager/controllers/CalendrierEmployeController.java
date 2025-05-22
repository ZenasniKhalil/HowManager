package com.JESIKOM.HowManager.controllers;

import com.JESIKOM.HowManager.JavaFxApplicationSupport;
import com.JESIKOM.HowManager.models.Personnel;
import com.JESIKOM.HowManager.models.WeeklyTimeSlot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.JESIKOM.HowManager.service.PersonnelService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.*;

import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.springframework.stereotype.*;

import java.net.URL;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.*;
import java.util.ResourceBundle;

@Component
public class CalendrierEmployeController {

    @FXML private FlowPane calendar;
    @FXML private Text month;
    @FXML private Text year;

    private YearMonth currentYearMonth;
    private Personnel employe;
    private List<Personnel> employes;
    @Autowired
    private PersonnelService personnelService;

    @FXML
    public void initialize() {
        currentYearMonth = YearMonth.now();
        employes = personnelService.getAllPersonnel();
        updateCalendar();
    }


    public void setEmploye() {
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
        calendar.setPrefWrapLength(630); // 7 colonnes * 90px
        month.setText(currentYearMonth.getMonth().toString());
        year.setText(String.valueOf(currentYearMonth.getYear()));

        LocalDate firstDayOfMonth = currentYearMonth.atDay(1);
        int daysInMonth = currentYearMonth.lengthOfMonth();
        int startOffset = firstDayOfMonth.getDayOfWeek().getValue() % 7; // Dimanche = 0

        // Ajouter les cases vides en début de mois
        for (int i = 0; i < startOffset; i++) {
            TextFlow emptyCell = new TextFlow();
            emptyCell.setPrefSize(90, 90);
            calendar.getChildren().add(emptyCell);
        }

        for (int i = 0; i < daysInMonth; i++) {
            LocalDate currentDate = firstDayOfMonth.plusDays(i);
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

            TextFlow dayCell = new TextFlow();
            dayCell.setPrefSize(90, 90);
            dayCell.setStyle("-fx-border-color: #ccc; -fx-background-color: #ffffff; -fx-padding: 5px;");

            Text dateText = new Text(currentDate.getDayOfMonth() + "\n");
            dateText.setStyle("-fx-font-weight: bold;");
            dayCell.getChildren().add(dateText);

            for (Personnel employe : employes) {
                List<WeeklyTimeSlot> slotsDuJour = getSlotsPourJour(employe.getWeeklyTimeSlots(), dayOfWeek);
                for (WeeklyTimeSlot slot : slotsDuJour) {
                    Text slotText = new Text(employe.getNom() + ": " +
                            slot.getStartTime().toString() + "-" + slot.getEndTime().toString() + "\n");
                    slotText.setStyle("-fx-fill: #2c3e50; -fx-font-size: 10px;");
                    dayCell.getChildren().add(slotText);
                }
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
    public void retourTableauBord(ActionEvent event){
        try {
            System.out.println("retourTableauBord()");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
            loader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);
            Parent root = loader.load();

            //Obtenir le Stage actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root)); // Remplacer le contenu
            stage.show(); // si nécessaire

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void voirPersonnel(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Personnel.fxml"));
            loader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);  // Injection Spring dans le FXML
            Parent root = loader.load();

            //Obtenir le Stage actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root)); // Remplacer le contenu
            stage.show(); // si nécessaire

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
