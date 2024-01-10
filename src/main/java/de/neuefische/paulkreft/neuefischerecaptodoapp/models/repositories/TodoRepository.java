package de.neuefische.paulkreft.neuefischerecaptodoapp.models.repositories;

import de.neuefische.paulkreft.neuefischerecaptodoapp.models.Todo;
import de.neuefische.paulkreft.neuefischerecaptodoapp.models.TodoRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TodoRepository {
    private final List<Todo> todoList = new ArrayList<>();

    public List<Todo> getTodos() {
        return todoList;
    }

    public Todo getTodoById(String id) {
        return getTodos().stream()
                .filter(todo -> todo.id().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addTodo(Todo todo) {
        todoList.add(todo);
    }

    public Todo updateTodoById(String id, TodoRequest todoRequest) {
        Todo toUpdate = getTodoById(id);

        if (toUpdate == null) {
            return null;
        }

        Todo updatedTodo = toUpdate.withDescription(todoRequest.description()).withStatus(todoRequest.status());

        todoList.remove(toUpdate);
        todoList.add(updatedTodo);

        return updatedTodo;
    }

    public void removeTodoById(String id) {
        todoList.removeIf(todo -> todo.id().equals(id));
    }
}
