package com.pucmm.UI;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.components.calendar.CalendarTargetDetails;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.event.BasicEventProvider;
import com.vaadin.ui.themes.ValoTheme;
import de.essendi.vaadin.ui.component.numberfield.NumberField;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Eduardo veras on 16-Oct-16.
 */



@SpringUI
@Theme("valo")
public class TodoUI extends UI{

    private VerticalLayout layout;
    private Calendar cal;


    @Override
    protected void init(VaadinRequest request) {
        setupLayout();
        addHeader();
        addForm();
        addCalendar();
    }

    public void setupLayout()
    {
        layout= new VerticalLayout();
        layout.setSpacing(true);
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(layout);

    }

    private void addForm() {
        HorizontalLayout formLayout = new HorizontalLayout();
        formLayout.setWidth("80%");
        formLayout.setSpacing(true);

        TextField titulo = new TextField();
        titulo.focus();

        TextField descripcion = new TextField();

        DateField inicio = new DateField("inicio");
        inicio.setDateFormat("yyyy/MM/dd h:m:s");


        //NumberField duracion = new NumberField();



        titulo.setWidth("100%");
        Button addButton = new Button("");
        addButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addButton.setIcon(FontAwesome.PLUS);

        formLayout.addComponents(titulo, descripcion,inicio,addButton);

        layout.addComponent(formLayout);

        addButton.addClickListener(click -> {
            GregorianCalendar start = new GregorianCalendar();
            GregorianCalendar end   = new GregorianCalendar();
            start= inicio.get
            end.add(java.util.Calendar.HOUR, 10);
            cal.addEvent(new BasicEvent(titulo.getCaption(),
                    descripcion.getCaption(),
                    inicio.getValue(), inicio.getValue().add(java.util.Calendar.HOUR, 10)));

        });
        addButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    }

    public void addHeader()
    {
        Label header = new Label("THE CALENDAR OF LIFE");
        header.addStyleName(ValoTheme.LABEL_H1);
        header.setSizeUndefined();
        layout.addComponent(header);

    }

    public void addCalendar()
    {
        HorizontalLayout formLayout= new HorizontalLayout();
        formLayout.setSpacing(true);
        formLayout.setWidth("80%");



        Button addButton = new Button("ADD");
        addButton.setIcon(FontAwesome.PLUS);
        addButton.setStyleName(ValoTheme.BUTTON_PRIMARY);

        layout.addComponent(formLayout);
        cal= new Calendar();
        cal.setFirstDayOfWeek(1);
        //cal.setFirstVisibleHourOfDay(6);
        //cal.setLastVisibleHourOfDay(21);


        GregorianCalendar start = new GregorianCalendar();
        GregorianCalendar end   = new GregorianCalendar();
        end.add(java.util.Calendar.HOUR, 5);
        cal.addEvent(new BasicEvent("Calendar study",
                "Learning how to use Vaadin Calendar",
                start.getTime(), end.getTime()));

        layout.addComponent(cal);


    }









}
