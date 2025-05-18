package com.JESIKOM.HowManager.models;

import java.time.ZonedDateTime;


public class CalendarActivity {
    private ZonedDateTime date;
    private String clientName;
    private int logementId;

    public CalendarActivity(ZonedDateTime date, String clientName, int logementId) {
        this.date = date;
        this.clientName = clientName;
        this.logementId = logementId;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public String getClientName() {
        return clientName;
    }

    public int getLogementId() {
        return logementId;
    }
}
