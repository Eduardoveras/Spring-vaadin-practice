package com.pucmm.model;

import com.pucmm.Services.EventService;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.components.calendar.event.BasicEventProvider;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Eduardo veras on 26-Oct-16.
 */
@SpringUI
public class CustomEventProvider extends BasicEventProvider {

    @Autowired
    EventService eventService;

    List<CalendarEvent> events;

    public CustomEventProvider() {
        events = new ArrayList<>();
    }

    @Override
    public void addEvent(CalendarEvent event) {
        super.addEvent(event);
        CustomEvent e = (CustomEvent) event;
        eventService.save(e);
    }

    @Override
    public List<CalendarEvent> getEvents(Date startDate, Date endDate) {
        events = new ArrayList<>(eventService.findBetween(startDate, endDate));
        return events;
    }
}