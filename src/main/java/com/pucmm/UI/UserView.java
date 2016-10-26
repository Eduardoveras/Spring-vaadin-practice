/**
 * Created by Djidjelly Siclait on 10/26/2016.
 */
package com.pucmm.UI;

import com.pucmm.Services.AccessControlService;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI(path = "/userInfo")
@Theme("valo")
public class UserView extends UI {
    // Service
    @Autowired
    private AccessControlService accessControlService;

    @Override
    public void init(VaadinRequest vaadinRequest){

    }
}
