package com.JESIKOM.HowManager.controllers;

import com.JESIKOM.HowManager.models.CalendarActivity;
import com.JESIKOM.HowManager.models.Client;
import com.JESIKOM.HowManager.models.Planning;
import com.JESIKOM.HowManager.models.TypeIdentite;
import com.JESIKOM.HowManager.service.ClientService;
import com.JESIKOM.HowManager.service.PlanningService;
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
import java.time.temporal.WeekFields;
import java.util.*;

@Controller
public class CalendarClientController implements Initializable {

    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    @Autowired
    private PlanningService planningService;

    @Autowired
    private ClientService clientService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (clientService.getClientByEmail("demo@client.com").isEmpty()) {
            Client client = new Client();
            client.setNom("Démo");
            client.setPrenom("Testeur");
            client.setEmail("demo@client.com");
            client.setTelephone("0123456789");
            client.setAdresse("Rue de test");
            client.setDateNaissance(LocalDate.of(1990, 1, 1));
            client.setNationalite("Française");
            client.setNumeroIdentite("ID123");
            client.setTypeIdentite(TypeIdentite.CIN);
            clientService.addClient(client);
        }

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

        int monthMaxDate = dateFocus.getMonth().maxLength();
        if (dateFocus.getYear() % 4 != 0 && monthMaxDate == 29) {
            monthMaxDate = 28;
        }
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
                        double textTranslationY = -(rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        date.setStyle("-fx-font-weight: bold; -fx-fill: #333;");
                        stackPane.getChildren().add(date);

                        List<CalendarActivity> calendarActivities = calendarActivityMap.get(currentDate);
                        if (calendarActivities != null) {
                            createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    if (today.getYear() == dateFocus.getYear()
                            && today.getMonth() == dateFocus.getMonth()
                            && today.getDayOfMonth() == currentDate) {
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    private void createCalendarActivity(List<CalendarActivity> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
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

        for (int k = 0; k < calendarActivities.size(); k++) {
            if (k >= 2) {
                Text moreActivities = new Text("...");
                moreActivities.setWrappingWidth(boxWidth * 0.95);
                moreActivities.setStyle("-fx-font-size: 11px;");
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> System.out.println(calendarActivities));
                break;
            }

            Text text = new Text(calendarActivities.get(k).getClientName() + ", " + calendarActivities.get(k).getDate().toLocalTime());
            text.setWrappingWidth(boxWidth * 0.95);
            text.setStyle("-fx-font-size: 11px;");
            calendarActivityBox.getChildren().add(text);
            text.setOnMouseClicked(mouseEvent -> System.out.println(text.getText()));
        }

        calendarActivityBox.setTranslateY(rectangleHeight * 0.30); // ✅ DESCENDU ici
        StackPane.setAlignment(calendarActivityBox, Pos.TOP_CENTER);
        stackPane.getChildren().add(calendarActivityBox);
    }

    private Map<Integer, List<CalendarActivity>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) {
        List<Planning> plannings = planningService.listerTous();
        List<CalendarActivity> calendarActivities = new ArrayList<>();

        for (Planning planning : plannings) {
            if (planning.getClient() != null) {
                try {
                    ZonedDateTime mondayOfWeek = ZonedDateTime.of(planning.getAnnee(), 1, 1, 10, 0, 0, 0, dateFocus.getZone())
                            .with(WeekFields.ISO.weekOfWeekBasedYear(), planning.getSemaine())
                            .with(DayOfWeek.MONDAY);

                    if (mondayOfWeek.getMonthValue() != dateFocus.getMonthValue()) {
                        continue;
                    }

                    calendarActivities.add(new CalendarActivity(
                            mondayOfWeek,
                            planning.getClient().getPrenom() + " " + planning.getClient().getNom()

                    ));
                } catch (Exception e) {
                    System.out.println("Erreur date planning : " + e.getMessage());
                }
            }
        }

        return createCalendarMap(calendarActivities);
    }

    private Map<Integer, List<CalendarActivity>> createCalendarMap(List<CalendarActivity> calendarActivities) {
        Map<Integer, List<CalendarActivity>> calendarActivityMap = new HashMap<>();
        for (CalendarActivity activity : calendarActivities) {
            int activityDate = activity.getDate().getDayOfMonth();
            calendarActivityMap.computeIfAbsent(activityDate, k -> new ArrayList<>()).add(activity);
        }
        return calendarActivityMap;
    }
}
