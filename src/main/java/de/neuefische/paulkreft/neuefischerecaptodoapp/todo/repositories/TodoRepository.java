package de.neuefische.paulkreft.neuefischerecaptodoapp.todo.repositories;

import de.neuefische.paulkreft.neuefischerecaptodoapp.todo.models.Todo;
import de.neuefische.paulkreft.neuefischerecaptodoapp.todo.models.TodoRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TodoRepository {
    private final List<Todo> todoList = new ArrayList<>();

    public List<Todo> getTodos() {
        return todoList;
    }

    public Optional<Todo> getTodoById(String id) {
        return getTodos().stream()
                .filter(todo -> todo.id().equals(id))
                .findFirst();
    }

    public void addTodo(Todo todo) {
        todoList.add(todo);
    }

    public Optional<Todo> updateTodoById(String id, TodoRequest todoRequest) {
        Optional<Todo> toUpdate = getTodoById(id);

        if (toUpdate.isEmpty()) {
            return toUpdate;
        }

        Todo updatedTodo = toUpdate.get().withDescription(todoRequest.description()).withStatus(todoRequest.status());

        todoList.remove(toUpdate.get());
        todoList.add(updatedTodo);

        return Optional.of(updatedTodo);
    }

    public void removeTodoById(String id) {
        todoList.removeIf(todo -> todo.id().equals(id));
    }
}
