package com.pucmm.UI;

import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by Eduardo veras on 16-Oct-16.
 */



@SpringUI
@Theme("valo")
public class TodoUI extends UI{

    private VerticalLayout layout;


    @Override
    protected void init(VaadinRequest request) {
        setupLayout();
        addHeader();
        addCalendar();
        addButton();

    }

    public void setupLayout()
    {
        layout= new VerticalLayout();
        layout.setSpacing(true);
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(layout);

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
        Calendar cal = new Calendar();
        layout.addComponent(cal);


        addButton.addClickListener(click -> {
            //ADD TO CALENDAR

        });
        addButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

    }


    public void addButton()
    {
        Button deleteButton = new Button("Delete completed");

        deleteButton.addClickListener(click->{
            //      DO THE MAGIC
                }
        );

        layout.addComponent(deleteButton);
    }
}
