/**
 * Created by Djidjelly Siclait on 10/26/2016.
 */
package com.pucmm.UI;

import com.pucmm.Services.AccessControlService;
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

    private HorizontalLayout layout;
    private FormLayout editPassword;
    private VerticalLayout infoLayout;

    @Override
    public void init(VaadinRequest vaadinRequest){

        if (accessControlService.fetchAllRegisteredUser().isEmpty())
            getUI().getPage().setLocation("/");

        setupLayout();
        addHeader();
    }

    public void setupLayout()
    {
        Page.getCurrent().setTitle("Login");

        layout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setSizeFull();
        layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        setContent(layout);
    }

    public void addHeader()
    {
        Label header = new Label("YOUR ARE VIEWING THE PROFILE ASSIGNED TO " + accessControlService.fetchAllRegisteredUser().get(0).getFullName());
        header.addStyleName(ValoTheme.LABEL_H3);
        header.setSizeUndefined();
        layout.addComponent(header);
    }

}
