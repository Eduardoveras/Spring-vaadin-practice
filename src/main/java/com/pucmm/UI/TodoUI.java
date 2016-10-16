package com.pucmm.UI;

import com.pucmm.Entity.Todo;
import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
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
        layout.setSpacing(true);
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
        taskField.setWidth("100%");

        Button addButton = new Button("ADD");
        addButton.setIcon(FontAwesome.PLUS);
        addButton.setStyleName(ValoTheme.BUTTON_PRIMARY);

        formLayout.addComponents(taskField,addButton);
        formLayout.setExpandRatio(taskField,1);
        layout.addComponent(formLayout);


        addButton.addClickListener(click -> {
            todoList.addTodo(new Todo(taskField.getValue()));
            taskField.setValue("");
            taskField.focus();
        });
        addButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

    }

    public void addTodoList()
    {
        todoList.setWidth("80%");
        layout.addComponent(todoList);
    }

    public void addButton()
    {
        Button deleteButton = new Button("Delete completed");

        deleteButton.addClickListener(click->todoList.deleteCompleted());

        layout.addComponent(deleteButton);
    }
}
