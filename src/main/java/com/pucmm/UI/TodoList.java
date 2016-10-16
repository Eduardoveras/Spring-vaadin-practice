package com.pucmm.UI;

import com.pucmm.Entity.Todo;
import com.pucmm.Layout.TodoLayout;
import com.pucmm.Listener.TodoChangeListener;
import com.pucmm.Repository.TodoRepository;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Eduardo veras on 16-Oct-16.
 */

@SpringComponent @UIScope
public class TodoList extends VerticalLayout implements TodoChangeListener {

    @Autowired
    TodoRepository repository;
    private List<Todo> todos;


    @PostConstruct
    void init() {
        setWidth("80%");
        setSpacing(true);

        update();
    }

    private void update() {
        setTodos(repository.findAll());
    }

    private void setTodos(List<Todo> todos) {
        this.todos = todos;
        removeAllComponents();
        todos.forEach(todo -> addComponent(new TodoLayout(todo, this)));
    }

    void addTodo(Todo todo) {
        repository.save(todo);
        update();
    }

    @Override
    public void todoChanged(Todo todo) {
        addTodo(todo);
    }


    public void deleteCompleted() {
        repository.deleteInBatch(
                todos.stream().filter(Todo::isDone).collect(Collectors.toList())
        );
        update();
    }
}
