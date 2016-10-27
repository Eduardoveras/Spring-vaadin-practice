package com.pucmm.UI;

import com.pucmm.Services.AccessControlService;
import com.pucmm.Services.EventService;
import com.pucmm.model.CustomEvent;
import com.sendgrid.*;
import com.vaadin.event.ShortcutAction;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Eduardo veras on 25-Oct-16.
 */

@Component
@UIScope
@SpringUI
public class emailModal extends FormLayout {

    TextField emailFrom = new TextField("From:");
    TextField emailTo = new TextField("To:");
    TextField caption = new TextField("Caption");
    TextArea description = new TextArea("Description");

    Button addBtn = new Button("Send");
    Button cancelBtn = new Button("Cancel");



    public emailModal(String email) {
        emailTo.setValue(email);
        setup();

    }

    private void setup() {

        setSizeUndefined();
        setMargin(true);
        setSpacing(true);

        addBtn.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        addBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Email from = new Email(emailFrom.getValue());
                String subject = caption.getValue();
                Email to = new Email(emailTo.getValue());
                Content content = new Content("text/plain", description.getValue()+"Email Service by Eduardo Veras and Djidjelly Siclait");
                Mail mail = new Mail(from, subject, to, content);

                SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
                Request request = new Request();
                try {
                    request.method = Method.POST;
                    request.endpoint = "mail/send";
                    request.body = mail.build();
                    Response response = sg.api(request);
                    System.out.println(response.statusCode);
                    System.out.println(response.body);
                    System.out.println(response.headers);
                } catch (IOException ex) {
                    //throw ex;
                }
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

        emailFrom.setCaption("From:");
        emailFrom.setValue("test@test.com");
        emailTo.setCaption("To:");

        caption.setCaption("Title:");
        description.setCaption("Description:");

        addComponents( emailFrom,emailTo,caption, description,buttons);
    }
}
