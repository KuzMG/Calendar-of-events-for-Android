package com.example.education.model;

import java.time.LocalDate;
import java.util.List;

public class Events {
    private final String date;
    private final List<Event> event;

    public Events(String date, List<Event> event) {
        this.date = date;
        this.event = event;
    }

    public String getDate() {
        return date;
    }

    public List<Event> getEvent() {
        return event;
    }
}
