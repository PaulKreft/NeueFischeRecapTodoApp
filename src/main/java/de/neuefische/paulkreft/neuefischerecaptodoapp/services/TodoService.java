package de.neuefische.paulkreft.neuefischerecaptodoapp.services;

import de.neuefische.paulkreft.neuefischerecaptodoapp.models.Todo;
import de.neuefische.paulkreft.neuefischerecaptodoapp.models.TodoRequest;
import de.neuefische.paulkreft.neuefischerecaptodoapp.services.repositories.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public List<Todo> getTodos() {
        return todoRepository.getTodos();
    }

    public Todo getTodoById(String id) {
        Optional<Todo> todo = todoRepository.getTodoById(id);

        if (todo.isEmpty()) {
            throw new NoSuchElementException();
        }

        return todo.get();
    }

    public Todo addTodo(TodoRequest todoRequest) {
        Todo todo = new Todo(todoRequest);

        todoRepository.addTodo(todo);
        return todo;
    }

    public Todo updateTodoById(String id, TodoRequest todoRequest) {
        Optional<Todo> updatedTodo = todoRepository.updateTodoById(id, todoRequest);

        if (updatedTodo.isEmpty()) {
            throw new NoSuchElementException();
        }
        
        return updatedTodo.get();
    }


    public Todo deleteTodoById(String id) {
        Optional<Todo> deletedTodo = todoRepository.getTodoById(id);

        if (deletedTodo.isEmpty()) {
            throw new NoSuchElementException();
        }

        todoRepository.removeTodoById(id);

        return deletedTodo.get();
    }
}
