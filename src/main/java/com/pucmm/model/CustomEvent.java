package com.pucmm.model;

import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.EditableCalendarEvent;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Eduardo veras on 22-Oct-16.
 */


@Entity
@Table(name = "event")
public class CustomEvent implements CalendarEvent, EditableCalendarEvent, CalendarEvent.EventChangeNotifier {

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


    private transient List<EventChangeListener> listeners = new ArrayList<EventChangeListener>();

    public CustomEvent() { }

    public CustomEvent(String caption, String description, boolean isAllDay, Date start, Date end) {
        this.caption = caption;
        this.description = description;
        this.isAllDay = isAllDay;
        this.start = start;
        this.end = end;
    }

    @Override
    public void addEventChangeListener(EventChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeEventChangeListener(EventChangeListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void setCaption(String caption) {
        this.caption = caption;
        fireEventChange();
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
        fireEventChange();
    }

    @Override
    public void setEnd(Date end) {
        this.end = end;
        fireEventChange();
    }

    @Override
    public void setStart(Date start) {
        this.start = start;
    }

    @Override
    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    @Override
    public void setAllDay(boolean isAllDay) {
        this.isAllDay = isAllDay;
        fireEventChange();
    }

    @Override
    public Date getStart() {
        return start;
    }

    @Override
    public Date getEnd() {
        return end;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getStyleName() {
        return styleName;
    }

    @Override
    public boolean isAllDay() {
        return isAllDay;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    protected void fireEventChange() {
        EventChangeEvent event = new EventChangeEvent(this);

        for (EventChangeListener listener : listeners) {
            listener.eventChange(event);
        }
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

}
