package de.neuefische.paulkreft.neuefischerecaptodoapp.services;

import de.neuefische.paulkreft.neuefischerecaptodoapp.models.Todo;
import de.neuefische.paulkreft.neuefischerecaptodoapp.models.TodoRequest;
import de.neuefische.paulkreft.neuefischerecaptodoapp.models.repositories.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public List<Todo> getTodos() {
        return todoRepository.getTodos();
    }

    public Todo addTodo(TodoRequest todoRequest) {
        Todo todo = new Todo(todoRequest);

        todoRepository.addTodo(todo);
        return todo;
    }
}
