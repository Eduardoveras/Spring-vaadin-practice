package com.pucmm.UI;

import com.pucmm.Services.EventService;
import com.pucmm.model.CustomEvent;
import com.vaadin.event.ShortcutAction;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Eduardo veras on 22-Oct-16.
 */
@Component
@UIScope
@SpringUI
public class eventModal extends FormLayout {

    @Autowired
    EventService eventService;



    DateField start = new PopupDateField("Start Date");
    DateField end = new PopupDateField("End Date");

    TextField caption = new TextField("Caption");
    TextArea description = new TextArea("Description");

    Button addBtn = new Button("Add");
    Button cancelBtn = new Button("Cancel");

    public eventModal(Date startDate, Date endDate) {
        start.setValue(startDate);
        end.setValue(endDate);
        setup();
    }

    public eventModal() {
        start.setValue(new Date());
        end.setValue(new Date());
        setup();

    }

    private void setup() {

        setSizeUndefined();
        setMargin(true);
        setSpacing(true);
        start.setResolution(Resolution.MINUTE);
        end.setResolution(Resolution.MINUTE);
        addBtn.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        addBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                CustomEvent e = new CustomEvent();
                e.setDescription(description.getValue());
                e.setCaption(caption.getValue());
                e.setStart(start.getValue());
                e.setEnd(end.getValue());
                e.setAllDay(false);
                MainView.cal.addEvent(e);
                ((Window)getParent()).close();
            }
        });



        cancelBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ((Window)getParent()).close();
            }
        });

        HorizontalLayout buttons = new HorizontalLayout(addBtn, cancelBtn);
        buttons.setSpacing(true);

        start.setCaption("Start:");
        end.setCaption("End:");
        caption.setCaption("Title:");
        description.setCaption("Description:");

        addComponents( caption, description, start, end,buttons);
    }

    public void setDates(Date startDate, Date endDate) {
        start.setValue(startDate);
        end.setValue(endDate);
    }


}
