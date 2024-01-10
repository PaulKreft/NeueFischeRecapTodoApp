package de.neuefische.paulkreft.neuefischerecaptodoapp.models.repositories;

import de.neuefische.paulkreft.neuefischerecaptodoapp.models.Todo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TodoRepository {
    List<Todo> todoList = new ArrayList<>();


    public List<Todo> getTodos() {
        return todoList;
    }

    public void addTodo(Todo todo) {
        todoList.add(todo);
    }


}
