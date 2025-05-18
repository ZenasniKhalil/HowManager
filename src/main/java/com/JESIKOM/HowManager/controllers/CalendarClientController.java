package com.JESIKOM.HowManager.controllers;

import com.JESIKOM.HowManager.models.CalendarActivity;
import com.JESIKOM.HowManager.models.Reservation;
import com.JESIKOM.HowManager.service.ReservationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.*;
import java.util.*;

@Controller
public class CalendarClientController implements Initializable {

    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML private Text year;
    @FXML private Text month;
    @FXML private FlowPane calendar;

    @Autowired
    private ReservationService reservationService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void drawCalendar() {
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        Map<Integer, List<CalendarActivity>> calendarActivityMap = getCalendarActivitiesMonth(dateFocus);

        int monthMaxDate = dateFocus.getMonth().length(dateFocus.toLocalDate().isLeapYear());
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0, dateFocus.getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight / 6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j + 1) + (7 * i);
                if (calculatedDate > dateOffset) {
                    int currentDate = calculatedDate - dateOffset;
                    if (currentDate <= monthMaxDate) {
                        Text date = new Text(String.valueOf(currentDate));
                        date.setTranslateY(-(rectangleHeight / 2) * 0.75);
                        date.setStyle("-fx-font-weight: bold; -fx-fill: #333;");
                        stackPane.getChildren().add(date);

                        List<CalendarActivity> calendarActivities = calendarActivityMap.get(currentDate);
                        if (calendarActivities != null) {
                            createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
                        }

                        if (today.getYear() == dateFocus.getYear()
                                && today.getMonth() == dateFocus.getMonth()
                                && today.getDayOfMonth() == currentDate) {
                            rectangle.setStroke(Color.BLUE);
                        }
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    private void createCalendarActivity(List<CalendarActivity> activities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();
        calendarActivityBox.setSpacing(2);

        double boxWidth = rectangleWidth * 0.85;
        calendarActivityBox.setMaxWidth(boxWidth);
        calendarActivityBox.setPrefWidth(boxWidth);
        calendarActivityBox.setMinWidth(boxWidth);

        calendarActivityBox.setMaxHeight(rectangleHeight * 0.55);
        calendarActivityBox.setPrefHeight(rectangleHeight * 0.55);
        calendarActivityBox.setStyle(
                "-fx-background-color: #d6eaff; " +
                        "-fx-background-radius: 8; " +
                        "-fx-padding: 4 6 4 6; " +
                        "-fx-border-color: #88bde6; " +
                        "-fx-border-radius: 8;"
        );

        for (int k = 0; k < activities.size(); k++) {
            if (k >= 2) {
                Text more = new Text("...");
                more.setStyle("-fx-font-size: 11px;");
                calendarActivityBox.getChildren().add(more);
                break;
            }

            Text text = new Text(activities.get(k).getClientName() + "\nlog " + activities.get(k).getLogementId());

            text.setStyle("-fx-font-size: 11px;");
            calendarActivityBox.getChildren().add(text);
        }

        calendarActivityBox.setTranslateY(rectangleHeight * 0.30);
        StackPane.setAlignment(calendarActivityBox, Pos.TOP_CENTER);
        stackPane.getChildren().add(calendarActivityBox);
    }

    private Map<Integer, List<CalendarActivity>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) {
        List<Reservation> reservations = reservationService.getAllReservations();
        List<CalendarActivity> activities = new ArrayList<>();

        for (Reservation res : reservations) {
            if (res.getClient() != null && res.getDateDebut() != null) {
                ZonedDateTime resDate = res.getDateDebut().atStartOfDay(ZoneId.systemDefault());

                if (resDate.getMonthValue() == dateFocus.getMonthValue() &&
                        resDate.getYear() == dateFocus.getYear()) {
                    activities.add(new CalendarActivity(resDate, res.getClient().getPrenom() + " " + res.getClient().getNom(),res.getLogement().getNumero()));
                }
            }
        }

        return createCalendarMap(activities);
    }

    private Map<Integer, List<CalendarActivity>> createCalendarMap(List<CalendarActivity> activities) {
        Map<Integer, List<CalendarActivity>> map = new HashMap<>();
        for (CalendarActivity a : activities) {
            int day = a.getDate().getDayOfMonth();
            map.computeIfAbsent(day, k -> new ArrayList<>()).add(a);
        }
        return map;
    }
}
