package com.pucmm.UI;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Eduardo veras on 16-Oct-16.
 */



@SpringUI
@Theme("valo")
public class TodoUI extends UI{

    private VerticalLayout layout;

    @Autowired
    TodoList todoList;

    @Override
    protected void init(VaadinRequest request) {
        setupLayout();
        addHeader();
        addForm();
        addTodoList();
        addButton();

    }

    public void setupLayout()
    {
        layout= new VerticalLayout();
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(layout);

    }

    public void addHeader()
    {
        Label header = new Label("TODO");
        header.addStyleName(ValoTheme.LABEL_H1);
        header.setSizeUndefined();
        layout.addComponent(header);

    }

    public void addForm()
    {
        HorizontalLayout formLayout= new HorizontalLayout();
        formLayout.setSpacing(true);
        formLayout.setWidth("80%");
        TextField taskField = new TextField();
        Button addButton = new Button("ADD");
        formLayout.addComponents(taskField,addButton);
        layout.addComponent(formLayout);

    }

    public void addTodoList()
    {
        todoList.setWidth("80%");
        layout.addComponent(todoList);
    }

    public void addButton()
    {
        Button delete = new Button("DELETE");
        layout.addComponent(delete);
    }
}
