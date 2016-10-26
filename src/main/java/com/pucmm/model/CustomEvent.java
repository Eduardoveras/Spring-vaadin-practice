package com.pucmm.model;

import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.EditableCalendarEvent;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Eduardo veras on 22-Oct-16.
 */


@Entity
@Table(name = "event")
public class CustomEvent implements Serializable, CalendarEvent, EditableCalendarEvent, CalendarEvent.EventChangeNotifier {

    @Id
    @GeneratedValue
    private long id;
    @Column
    @Size(min = 3, max = 50)
    private String caption;
    @Column
    @Size(max = 255)
    private String description;
    @Column
    private String styleName;
    @Column
    private boolean isAllDay;
    @Column
    private boolean notified = false;
    @Column
    @DateTimeFormat
    private Date start;
    @Column
    @DateTimeFormat
    private Date end;


    @Override
    public void addEventChangeListener(EventChangeListener listener) {

    }

    @Override
    public void removeEventChangeListener(EventChangeListener listener) {

    }

    @Override
    public void setCaption(String caption) {

    }

    @Override
    public void setDescription(String description) {

    }

    @Override
    public void setEnd(Date end) {

    }

    @Override
    public void setStart(Date start) {

    }

    @Override
    public void setStyleName(String styleName) {

    }

    @Override
    public void setAllDay(boolean isAllDay) {

    }

    @Override
    public Date getStart() {
        return null;
    }

    @Override
    public Date getEnd() {
        return null;
    }

    @Override
    public String getCaption() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getStyleName() {
        return null;
    }

    @Override
    public boolean isAllDay() {
        return false;
    }

    public CustomEvent() { }


    public CustomEvent(String caption, String description, boolean isAllDay, Date start, Date end) {
        this.caption = caption;
        this.description = description;
        this.isAllDay = isAllDay;
        this.start = start;
        this.end = end;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

}
