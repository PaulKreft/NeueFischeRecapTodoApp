package de.neuefische.paulkreft.neuefischerecaptodoapp.todo.controllers;

import de.neuefische.paulkreft.neuefischerecaptodoapp.todo.exception.GlobalExceptionHandler;
import de.neuefische.paulkreft.neuefischerecaptodoapp.todo.models.Todo;
import de.neuefische.paulkreft.neuefischerecaptodoapp.todo.models.TodoRequest;
import de.neuefische.paulkreft.neuefischerecaptodoapp.todo.models.TodoStatus;
import de.neuefische.paulkreft.neuefischerecaptodoapp.todo.services.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TodoControllerTest {

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(todoController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getTodos_ShouldInitiallyReturnEmptyListOfTodos() throws Exception {
        when(todoService.getTodos()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getTodos_ShouldReturnListOfTodos() throws Exception {
        // Given
        List<Todo> todos = List.of(
                new Todo("1", "Task 1", TodoStatus.OPEN),
                new Todo("2", "Task 2", TodoStatus.IN_PROGRESS)
        );
        when(todoService.getTodos()).thenReturn(todos);

        // When and Then
        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(todos.size()));
    }

    @Test
    void getTodoById_ShouldReturnTodo_WhenTodoExists() throws Exception {
        // Given
        Todo todo = new Todo("1", "Task 1", TodoStatus.OPEN);
        when(todoService.getTodoById("1")).thenReturn(todo);

        // When and Then
        mockMvc.perform(get("/api/todo/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(todo.id()))
                .andExpect(jsonPath("$.description").value(todo.description()))
                .andExpect(jsonPath("$.status").value(todo.status().toString()));
    }


    @Test
    void addTodo_ShouldReturnAddedTodo() throws Exception {
        // Given
        Todo addedTodo = new Todo("1", "New Task", TodoStatus.OPEN);
        when(todoService.addTodo(any(TodoRequest.class))).thenReturn(addedTodo);

        // When and Then
        mockMvc.perform(post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"New Task\",\"status\":\"OPEN\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(addedTodo.id()))
                .andExpect(jsonPath("$.description").value(addedTodo.description()))
                .andExpect(jsonPath("$.status").value(addedTodo.status().toString()));
    }

    @Test
    void updateTodoById_ShouldReturnUpdatedTodo_WhenTodoExists() throws Exception {
        // Given
        Todo updatedTodo = new Todo("1", "Updated Task", TodoStatus.DONE);
        when(todoService.updateTodoById(anyString(), any(TodoRequest.class))).thenReturn(updatedTodo);

        // When and Then
        mockMvc.perform(put("/api/todo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Updated Task\",\"status\":\"DONE\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(updatedTodo.id()))
                .andExpect(jsonPath("$.description").value(updatedTodo.description()))
                .andExpect(jsonPath("$.status").value(updatedTodo.status().toString()));
    }

    @Test
    void deleteTodoById_ShouldReturnDeletedTodo_WhenTodoExists() throws Exception {
        // Given
        Todo deletedTodo = new Todo("1", "Task 1", TodoStatus.OPEN);
        when(todoService.deleteTodoById("1")).thenReturn(deletedTodo);

        // When and Then
        mockMvc.perform(delete("/api/todo/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(deletedTodo.id()))
                .andExpect(jsonPath("$.description").value(deletedTodo.description()))
                .andExpect(jsonPath("$.status").value(deletedTodo.status().toString()));
    }
}
