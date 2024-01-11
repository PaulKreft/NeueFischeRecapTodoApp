package de.neuefische.paulkreft.neuefischerecaptodoapp.todo.controllers;

import de.neuefische.paulkreft.neuefischerecaptodoapp.todo.models.Todo;
import de.neuefische.paulkreft.neuefischerecaptodoapp.todo.models.TodoRequest;
import de.neuefische.paulkreft.neuefischerecaptodoapp.todo.services.TodoAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ai/todo")
@RequiredArgsConstructor
public class TodoAiController {
    private final TodoAiService todoAiService;

    @GetMapping
    public List<Todo> getTodos() {
        return todoAiService.getTodos();
    }

    @GetMapping("{id}")
    public Todo getTodoById(@PathVariable String id) {
        return todoAiService.getTodoById(id);
    }

    @PostMapping
    public Todo addTodo(@RequestBody TodoRequest request) {
        return todoAiService.addTodo(request);
    }

    @PutMapping("{id}")
    public Todo updateTodoById(@PathVariable String id, @RequestBody TodoRequest todoRequest) {
        return todoAiService.updateTodoById(id, todoRequest);
    }

    @DeleteMapping("{id}")
    public Todo deleteTodoById(@PathVariable String id) {
        return todoAiService.deleteTodoById(id);
    }
}
