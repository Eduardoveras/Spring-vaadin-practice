package com.pucmm.UI;

import com.pucmm.Services.AccessControlService;
import com.pucmm.Services.EventService;
import com.pucmm.model.CustomEvent;
import com.pucmm.model.User;
import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.components.calendar.CalendarComponentEvents;
import com.vaadin.ui.components.calendar.event.EditableCalendarEvent;
import com.vaadin.ui.components.calendar.handler.BasicEventMoveHandler;
import com.vaadin.ui.components.calendar.handler.BasicEventResizeHandler;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Eduardo veras on 16-Oct-16.
 */



@SpringUI(path = "/calendar")
@Theme("valo")
public class MainView extends UI{

    @Autowired
    private AccessControlService accessControlService;
    @Autowired
    public EventService eventService;


    @Autowired
    private EventModal eventModal;
    private emailModal emailModal;


    private VerticalLayout layout = new VerticalLayout();
    public static Calendar cal= new Calendar();

    @Autowired
    public MainView(EventModal eventMod)
    {
        this.eventModal = eventMod;


        //this.eventModal.setCalendar(cal);
    }

    @Override
    protected void init(VaadinRequest request) {
        //cal

        if (accessControlService.fetchAllRegisteredUser().isEmpty())
            getUI().getPage().setLocation("/");
        else if (!accessControlService.fetchAllRegisteredUser().get(0).isLoggedIn())
            getUI().getPage().setLocation("/");
        else {
            setupLayout();
            addHeader();
            addCalendar();
            //cal.setEventProvider(customEventProvider);
            eventModal = new EventModal();
            emailModal = new emailModal(accessControlService.fetchAllRegisteredUser().get(0).getEmail());
            addForm();
        }
    }

    public void setupLayout()
    {
        Page.getCurrent().setTitle("Spring Vaadin Calendar");

        layout= new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setSizeFull();
        layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        setContent(layout);

    }

    private void addForm() {

        HorizontalLayout footerLayout = new HorizontalLayout();

        footerLayout.setSpacing(true);
        footerLayout.setMargin(true);

        Button addButton = new Button("Add Event");
        Button emailBtn = new Button("Send Email");
        Button logOut = new Button("LogOut");
        Button viewInfo = new Button("User Info");

        emailBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        emailBtn.setIcon(FontAwesome.ENVELOPE);
        addButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addButton.setIcon(FontAwesome.PLUS);
        logOut.addStyleName(ValoTheme.BUTTON_PRIMARY);
        logOut.setIcon(FontAwesome.XING);;
        viewInfo.addStyleName(ValoTheme.BUTTON_PRIMARY);
        viewInfo.setIcon(FontAwesome.ARCHIVE);

        if (!eventService.findAll().isEmpty())
            setUpButtonModalView(addButton, "Add New Event (" + eventService.findAll().size() + ")", eventModal);
        else
            setUpButtonModalView(addButton, "Add New Event", eventModal);
        setUpButtonModalView(emailBtn, "Send Email", emailModal);

        footerLayout.addComponents(addButton, emailBtn, logOut, viewInfo);

        logOut.addClickListener(new Button.ClickListener(){
            @Override
            public void buttonClick(Button.ClickEvent event){
                try {
                    User user = accessControlService.fetchAllRegisteredUser().get(0);
                    user.setLoggedIn(false);
                    accessControlService.editUser(user);
                } catch (PersistenceException ex){
                    //
                } catch (NullPointerException ex){
                    //
                } catch (Exception ex){
                    //
                }
                getUI().getPage().setLocation("/");
            }
        });

        viewInfo.addClickListener(new Button.ClickListener(){
            @Override
            public void buttonClick(Button.ClickEvent event){
                getUI().getPage().setLocation("/userInfo");
            }
        });

        layout.addComponent(footerLayout);

        //layout.setComponentAlignment(addButton, Alignment.TOP_CENTER);


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

        Button addButton = new Button("ADD");
        addButton.setIcon(FontAwesome.PLUS);
        addButton.setStyleName(ValoTheme.BUTTON_PRIMARY);

        cal.setFirstDayOfWeek(1);
        cal.setTimeFormat(Calendar.TimeFormat.Format12H);

        cal.setWeeklyCaptionFormat("yyyy-MM-dd");
        cal.setFirstVisibleDayOfWeek(1);
        cal.setLastVisibleDayOfWeek(7);
        //cal.setFirstVisibleHourOfDay(6);
        //cal.setLastVisibleHourOfDay(20);
        cal.setSizeFull();
        cal.setHeight("100%");


        cal.setHandler(new CalendarComponentEvents.EventClickHandler() {
            @Override
            public void eventClick(CalendarComponentEvents.EventClick event) {
                CustomEvent e = (CustomEvent) event.getCalendarEvent();

                new Notification("Event clicked: " + e.getCaption(), e.getDescription()).show(Page.getCurrent());
            }
        });


        cal.setHandler(new BasicEventMoveHandler() {
            private java.util.Calendar javaCalendar;

            public void eventMove(CalendarComponentEvents.MoveEvent event) {
                javaCalendar = event.getComponent().getInternalCalendar();
                super.eventMove(event);
            }

            protected void setDates(EditableCalendarEvent event,
                                    Date start, Date end) {
                CustomEvent e = (CustomEvent) event;
                e.setStart(start);
                e.setEnd(end);
                eventService.registerEvent(e.getCaption(), e.getDescription(), e.isAllDay(), e.getStart(), e.getEnd());
            }
        });

        cal.setHandler(new BasicEventResizeHandler() {
            protected void setDates(EditableCalendarEvent event,
                                    Date start, Date end) {
                CustomEvent e = (CustomEvent) event;
                e.setStart(start);
                e.setEnd(end);
                eventService.registerEvent(e.getCaption(), e.getDescription(), e.isAllDay(), e.getStart(), e.getEnd());
            }
        });
        cal.setHandler(new CalendarComponentEvents.RangeSelectHandler() {
            @Override
            public void rangeSelect(CalendarComponentEvents.RangeSelectEvent event) {
                eventModal.setDates(event.getStart(), event.getEnd());
                openModalView("Add New Event", eventModal);
            }
        });


        cal.setLocale(Locale.US);
        cal.setStartDate(new GregorianCalendar().getTime());
        GregorianCalendar calEnd = new GregorianCalendar();
        calEnd.set(java.util.Calendar.DATE, 1);
        calEnd.roll(java.util.Calendar.DATE, -1);
        cal.setEndDate(calEnd.getTime());
        cal.setTimeFormat(Calendar.TimeFormat.Format12H);
        cal.setFirstVisibleDayOfWeek(1);
        cal.setLastVisibleDayOfWeek(7);
        cal.setFirstVisibleHourOfDay(6);
        cal.setLastVisibleHourOfDay(20);
        cal.setSizeFull();

        layout.addComponent(cal);
        layout.setExpandRatio(cal, 1.0f);

    }

    private void openModalView(String title, FormLayout form) {
        Window modalView = new Window(title);
        modalView.center();
        modalView.setResizable(false);
        modalView.setModal(true);
        modalView.setClosable(true);
        modalView.setDraggable(false);
        modalView.setContent(form);

        addWindow(modalView);
    }
    private void setUpButtonModalView(Button button, String title, FormLayout form) {
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                openModalView(title, form);
            }
        });
    }

    private boolean isThisMonth(java.util.Calendar cal, Date date) {
        cal.setTime(new Date());
        int thisMonth = cal.get(java.util.Calendar.MONTH);
        cal.setTime(date);
        return cal.get(java.util.Calendar.MONTH) == thisMonth;
    }

}
