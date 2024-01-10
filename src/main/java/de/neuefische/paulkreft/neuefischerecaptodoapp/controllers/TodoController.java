package de.neuefische.paulkreft.neuefischerecaptodoapp.controllers;

import de.neuefische.paulkreft.neuefischerecaptodoapp.models.Todo;
import de.neuefische.paulkreft.neuefischerecaptodoapp.models.TodoRequest;
import de.neuefische.paulkreft.neuefischerecaptodoapp.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping
    public List<Todo> getTodos() {
        return todoService.getTodos();
    }

    @GetMapping("{id}")
    public Todo getTodoById(@PathVariable String id) {
        return todoService.getTodoById(id);
    }

    @PostMapping
    public Todo addTodo(@RequestBody TodoRequest request) {
        return todoService.addTodo(request);
    }

    @PutMapping("{id}")
    public Todo updateTodoById(@PathVariable String id, @RequestBody TodoRequest todoRequest) {
        return todoService.updateTodoById(id, todoRequest);
    }

    @DeleteMapping("{id}")
    public Todo deleteTodoById(@PathVariable String id) {
        return todoService.deleteTodoById(id);
    }
}
