package de.neuefische.paulkreft.neuefischerecaptodoapp.todo.services;

import de.neuefische.paulkreft.neuefischerecaptodoapp.todo.models.Todo;
import de.neuefische.paulkreft.neuefischerecaptodoapp.todo.models.TodoRequest;
import de.neuefische.paulkreft.neuefischerecaptodoapp.todo.repositories.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService implements TodoServiceInterface {
    private final TodoRepository todoRepository;

    @Override
    public List<Todo> getTodos() {
        return todoRepository.getTodos();
    }

    @Override
    public Todo getTodoById(String id) {
        Optional<Todo> todo = todoRepository.getTodoById(id);

        if (todo.isEmpty()) {
            throw new NoSuchElementException();
        }

        return todo.get();
    }

    @Override
    public Todo addTodo(TodoRequest todoRequest) {
        Todo todo = new Todo(todoRequest);

        todoRepository.addTodo(todo);
        return todo;
    }

    @Override
    public Todo updateTodoById(String id, TodoRequest todoRequest) {
        Optional<Todo> updatedTodo = todoRepository.updateTodoById(id, todoRequest);

        if (updatedTodo.isEmpty()) {
            throw new NoSuchElementException();
        }

        return updatedTodo.get();
    }


    @Override
    public Todo deleteTodoById(String id) {
        Optional<Todo> deletedTodo = todoRepository.getTodoById(id);

        if (deletedTodo.isEmpty()) {
            throw new NoSuchElementException();
        }

        todoRepository.removeTodoById(id);

        return deletedTodo.get();
    }
}
