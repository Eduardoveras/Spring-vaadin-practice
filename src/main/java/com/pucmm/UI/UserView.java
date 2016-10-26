/**
 * Created by Djidjelly Siclait on 10/26/2016.
 */
package com.pucmm.UI;

import com.pucmm.Services.AccessControlService;
import com.pucmm.model.User;
import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ForkJoinPoolFactoryBean;

@SpringUI(path = "/userInfo")
@Theme("valo")
public class UserView extends UI {
    // Service
    @Autowired
    private AccessControlService accessControlService;

    private VerticalLayout layout;
    private HorizontalLayout fittingLayout;
    private FormLayout editPassword;
    private VerticalLayout infoLayout;

    private User user;

    @Override
    protected void init(VaadinRequest vaadinRequest){
        if (accessControlService.fetchAllRegisteredUser().isEmpty())
            getUI().getPage().setLocation("/");
        else
            user = accessControlService.fetchAllRegisteredUser().get(0);

        setupLayout();
        addHeader();
        displayUserInfo();
        formatLayout();
    }

    public void setupLayout()
    {
        Page.getCurrent().setTitle("USER INFO");

        layout = new VerticalLayout();
        fittingLayout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setSizeFull();
        layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        setContent(layout);
    }

    public void addHeader()
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
        Label password = new Label("Password: " + user.getPassword());
        password.addStyleName(ValoTheme.LABEL_H3);

        infoLayout.addComponents(email, name, password);

        fittingLayout.addComponent(infoLayout);
    }

    private void formatLayout(){
        layout.addComponent(fittingLayout);
    }
}
