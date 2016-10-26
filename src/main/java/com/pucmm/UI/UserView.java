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

@SpringUI(path = "/userInfo")
@Theme("valo")
public class UserView extends UI {
    // Service
    @Autowired
    private AccessControlService accessControlService;

    private VerticalLayout layout;
    private HorizontalLayout fittingLayout;
    private FormLayout editPassword;
    private FormLayout editInfo;
    private VerticalLayout infoLayout;

    private User user;

    @Override
    protected void init(VaadinRequest vaadinRequest){
        if (accessControlService.fetchAllRegisteredUser().isEmpty())
            getUI().getPage().setLocation("/");
        else if (!accessControlService.fetchAllRegisteredUser().get(0).isLoggedIn())
            getUI().getPage().setLocation("/");
        else
            user = accessControlService.fetchAllRegisteredUser().get(0);

        setupLayout();
        addHeader();
        displayUserInfo();
        addInfoForm();
        addPasswordForm();
        formatLayout();
    }

    private void setupLayout()
    {
        Page.getCurrent().setTitle("USER INFO");

        layout = new VerticalLayout();
        fittingLayout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        fittingLayout.setSpacing(true);
        fittingLayout.setMargin(true);
        layout.setSizeFull();
        layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        fittingLayout.setSizeFull();
        fittingLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        setContent(layout);
    }

    private void addHeader()
    {
        Label header = new Label("YOUR ARE VIEWING THE PROFILE ASSIGNED TO " + user.getFullName());
        header.addStyleName(ValoTheme.LABEL_H3);
        header.setSizeUndefined();
        layout.addComponent(header);
    }

    private void displayUserInfo(){
        infoLayout = new VerticalLayout();

        Label email = new Label("Email Address: " + user.getEmail());
        email.addStyleName(ValoTheme.LABEL_H4);
        Label name = new Label("Name: " + user.getFullName());
        name.addStyleName(ValoTheme.LABEL_H4);
        //Label password = new Label("Password: " + user.getPassword());
        //password.addStyleName(ValoTheme.LABEL_H4);

        infoLayout.addComponents(email, name/*, password*/);

        fittingLayout.addComponent(infoLayout);
    }

    private void addPasswordForm(){
        editPassword = new FormLayout();

        Label title = new Label("Change Password");
        title.addStyleName(ValoTheme.LABEL_H3);

        PasswordField oldPassword = new PasswordField("Old Password:");
        PasswordField newPassword = new PasswordField("New Password:");
        PasswordField confirm = new PasswordField("Confirm New Password: ");
        Button submit = new Button("Submit");

        submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
        submit.setIcon(FontAwesome.PLUS);

        editPassword.addComponents(oldPassword, newPassword, confirm, submit);

        submit.addClickListener(new Button.ClickListener(){
            @Override
            public void buttonClick(Button.ClickEvent event){
                if (oldPassword.getValue().equals(user.getPassword()))
                    if (newPassword.getValue().equals(confirm.getValue())) {
                        try {
                            user.setPassword(newPassword.getValue());
                            accessControlService.editUser(user);
                        } catch (PersistenceException exp){
                            //
                        } catch (NullPointerException exp){
                            //
                        } catch (Exception exp){
                            //
                        }

                        getUI().getPage().setLocation("/");
                    }
                    else
                        getUI().getPage().setLocation("/userInfo"); // TODO: add error exceptions
                else
                    getUI().getPage().setLocation("/userInfo"); // TODO: add error exceptions
            }
        });

        submit.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        fittingLayout.addComponent(editPassword);
    }

    private void addInfoForm(){
        editInfo = new FormLayout();

        Label title = new Label("Change Basic Info");
        title.addStyleName(ValoTheme.LABEL_H3);

        TextField newEmail = new TextField("New Email:");
        TextField newFirstName = new TextField("New First Name:");
        TextField newLastName = new TextField("New Last Name: ");
        PasswordField password = new PasswordField("Password:");
        Button submit = new Button("Submit");

        submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
        submit.setIcon(FontAwesome.PLUS);

        editInfo.addComponents(newEmail, newFirstName, newLastName, password, submit);

        submit.addClickListener(new Button.ClickListener(){
            @Override
            public void buttonClick(Button.ClickEvent event){
                if (password.getValue().equals(user.getPassword())){
                    try {
                        if (!newEmail.getValue().equals(""))
                            user.setEmail(newEmail.getValue());

                        if (!newFirstName.getValue().equals(""))
                         user.setFirstName(newFirstName.getValue());

                        if (!newLastName.getValue().equals(""))
                        user.setLastName(newLastName.getValue());

                        accessControlService.editUser(user);
                    } catch (PersistenceException exp){
                        //
                    } catch (NullPointerException exp){
                        //
                    } catch (Exception exp){
                        //
                    }

                    getUI().getPage().setLocation("/userInfo");
                }
                else
                    getUI().getPage().setLocation("/userInfo"); // TODO: add error exceptions
            }
        });

        submit.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        fittingLayout.addComponent(editInfo);
    }

    private void formatLayout(){
        layout.addComponent(fittingLayout);
    }
}
