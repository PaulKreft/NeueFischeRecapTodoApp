package de.neuefische.paulkreft.neuefischerecaptodoapp.todo.interfaces;

import de.neuefische.paulkreft.neuefischerecaptodoapp.todo.models.Todo;
import de.neuefische.paulkreft.neuefischerecaptodoapp.todo.models.TodoRequest;

import java.util.List;

public interface TodoServiceInterface {
    List<Todo> getTodos();

    Todo getTodoById(String id);

    Todo addTodo(TodoRequest todoRequest);

    Todo updateTodoById(String id, TodoRequest todoRequest);

    Todo deleteTodoById(String id);
}
