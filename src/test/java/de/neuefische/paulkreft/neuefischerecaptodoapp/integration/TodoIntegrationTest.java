package de.neuefische.paulkreft.neuefischerecaptodoapp.integration;

import de.neuefische.paulkreft.neuefischerecaptodoapp.models.Todo;
import de.neuefische.paulkreft.neuefischerecaptodoapp.models.TodoRequest;
import de.neuefische.paulkreft.neuefischerecaptodoapp.models.TodoStatus;
import de.neuefische.paulkreft.neuefischerecaptodoapp.services.repositories.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TodoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void getTodos_ShouldReturnListOfTodos() throws Exception {
        // Given
        List<Todo> todos = List.of(
                new Todo("1", "Task 1", TodoStatus.OPEN),
                new Todo("2", "Task 2", TodoStatus.IN_PROGRESS)
        );
        todoRepository.getTodos().addAll(todos);

        // When and Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(todos.size()));
    }

    @Test
    void getTodoById_ShouldReturnTodo_WhenTodoExists() throws Exception {
        // Given
        Todo todo = new Todo("1", "Task 1", TodoStatus.OPEN);
        todoRepository.addTodo(todo);

        // When and Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(todo.id()))
                .andExpect(jsonPath("$.description").value(todo.description()))
                .andExpect(jsonPath("$.status").value(todo.status().toString()));
    }

    @Test
    void addTodo_ShouldReturnAddedTodo() throws Exception {
        // Given
        TodoRequest todoRequest = new TodoRequest("New Task", TodoStatus.OPEN);

        // When and Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"New Task\",\"status\":\"OPEN\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value(todoRequest.description()))
                .andExpect(jsonPath("$.status").value(todoRequest.status().toString()));
    }

    @Test
    void updateTodoById_ShouldReturnUpdatedTodo_WhenTodoExists() throws Exception {
        // Given
        TodoRequest todoRequest = new TodoRequest("Updated Task", TodoStatus.DONE);
        Todo existingTodo = new Todo("1", "Task 1", TodoStatus.OPEN);
        todoRepository.addTodo(existingTodo);

        // When and Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/todo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Updated Task\",\"status\":\"DONE\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value(todoRequest.description()))
                .andExpect(jsonPath("$.status").value(todoRequest.status().toString()));
    }

    @Test
    void deleteTodoById_ShouldReturnDeletedTodo_WhenTodoExists() throws Exception {
        // Given
        Todo existingTodo = new Todo("1", "Task 1", TodoStatus.OPEN);
        todoRepository.addTodo(existingTodo);

        // When and Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todo/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value(existingTodo.description()))
                .andExpect(jsonPath("$.status").value(existingTodo.status().toString()));
    }
}
