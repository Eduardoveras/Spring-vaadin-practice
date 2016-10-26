/**
 * Created by Djidjelly Siclait on 10/26/2016.
 */
package com.pucmm.UI;

import com.pucmm.Services.AccessControlService;
import com.pucmm.model.User;
import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;

@SpringUI(path = "/")
@Theme("valo")
public class LoginView extends UI {

    // Service
    @Autowired
    private AccessControlService accessControlService;

    private VerticalLayout layout = new VerticalLayout();

    @Override
    protected void init(VaadinRequest request){

        setupLayout();
        addHeader();
        addForm();

    }

    public void setupLayout()
    {
        Page.getCurrent().setTitle("Login");

        layout= new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setSizeFull();
        layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        setContent(layout);

    }

    private void addForm() {

        TextField email = new TextField("Email");

        PasswordField password = new PasswordField("Password");

        TextField firstName = new TextField("First Name");

        TextField lastName = new TextField("Last Name");

        Button clickButton = accessControlService.fetchAllRegisteredUser().isEmpty() ? new Button("Register") : new Button("Login");

        clickButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        clickButton.setIcon(FontAwesome.PLUS);

        if(accessControlService.fetchAllRegisteredUser().isEmpty())
            layout.addComponents(email, firstName, lastName, password, clickButton);
        else
            layout.addComponents(email, password, clickButton);

        clickButton.addClickListener(new Button.ClickListener(){
            @Override
            public void buttonClick(Button.ClickEvent event){
                if (accessControlService.fetchAllRegisteredUser().isEmpty()){
                    try {
                        accessControlService.registerUser(email.getValue(), firstName.getValue(), lastName.getValue(), password.getValue());
                        getUI().getPage().setLocation("/");
                    } catch (PersistenceException exp){
                        //
                    } catch (NullPointerException exp){
                        //
                    } catch (Exception exp){
                        //
                    }
                }
                else {
                    if (accessControlService.validateUserCredentials(email.getValue(), password.getValue())) {
                        try {
                            User user = accessControlService.fetchAllRegisteredUser().get(0);

                            user.setLoggedIn(true);

                            accessControlService.editUser(user);

                            getUI().getPage().setLocation("/calendar");
                        } catch (PersistenceException exp){
                            //
                        } catch (NullPointerException exp){
                            //
                        } catch (Exception exp){
                            //
                        }
                    }
                    else
                        getUI().getPage().setLocation("/");
                }
            }
        });

        clickButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    }

    public void addHeader()
    {
        Label header = accessControlService.fetchAllRegisteredUser().isEmpty() ? new Label("PLEASE REGISTER AN ACCOUNT") : new Label("LOGIN TO ACCESS SERVICES");
        header.addStyleName(ValoTheme.LABEL_H3);
        header.setSizeUndefined();
        layout.addComponent(header);
    }
}
