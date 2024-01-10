package de.neuefische.paulkreft.neuefischerecaptodoapp.services;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import de.neuefische.paulkreft.neuefischerecaptodoapp.models.Todo;
import de.neuefische.paulkreft.neuefischerecaptodoapp.models.TodoRequest;
import de.neuefische.paulkreft.neuefischerecaptodoapp.models.TodoStatus;
import de.neuefische.paulkreft.neuefischerecaptodoapp.services.repositories.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TodoServiceTest {


    private TodoRepository todoRepository;
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        todoRepository = mock(TodoRepository.class);
        todoService = new TodoService(todoRepository);
    }

    @Test
    void getTodos_ShouldReturnEmptyList_WhenNoTodosExist() {
        // Given
        when(todoRepository.getTodos()).thenReturn(List.of());

        // When
        List<Todo> result = todoService.getTodos();

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void getTodos_ShouldReturnTodoList_WhenTodosExist() {
        // Given
        List<Todo> todos = Arrays.asList(
                new Todo("1", "Task 1", TodoStatus.OPEN),
                new Todo("2", "Task 2", TodoStatus.DONE)
        );
        when(todoRepository.getTodos()).thenReturn(todos);

        // When
        List<Todo> result = todoService.getTodos();

        // Then
        assertEquals(todos, result);
    }

    @Test
    void getTodos_ShouldReturnSingleTodo_WhenOneTodoExists() {
        // Given
        Todo singleTodo = new Todo("1", "Single Task", TodoStatus.IN_PROGRESS);
        when(todoRepository.getTodos()).thenReturn(List.of(singleTodo));

        // When
        List<Todo> result = todoService.getTodos();

        // Then
        assertEquals(1, result.size());
        assertEquals(singleTodo, result.getFirst());
    }

    @Test
    void getTodoById_ShouldReturnTodo_WhenTodoExists() {
        // Given
        String todoId = "1";
        Todo expectedTodo = new Todo(todoId, "Task 1", TodoStatus.OPEN);
        when(todoRepository.getTodoById(todoId)).thenReturn(expectedTodo);

        // When
        Todo result = todoService.getTodoById(todoId);

        // Then
        assertEquals(expectedTodo, result);
    }

    @Test
    void getTodoById_ShouldReturnNull_WhenTodoDoesNotExist() {
        // Given
        String todoId = "nonexistent";
        when(todoRepository.getTodoById(todoId)).thenReturn(null);

        // When
        Todo result = todoService.getTodoById(todoId);

        // Then
        assertNull(result);
    }

    @Test
    void addTodo_ShouldAddTodoAndReturnIt() {
        // Given
        TodoRequest todoRequest = new TodoRequest("New Task", TodoStatus.IN_PROGRESS);

        // When
        Todo result = todoService.addTodo(todoRequest);

        // Then
        assertNotNull(result);
        assertEquals(todoRequest.description(), result.description());
        assertEquals(todoRequest.status(), result.status());
        verify(todoRepository, times(1)).addTodo(result);
    }

    @Test
    void updateTodoById_ShouldReturnUpdatedTodo_WhenTodoExists() {
        // Given
        String todoId = "1";
        TodoRequest updatedTodoRequest = new TodoRequest("Updated Task", TodoStatus.DONE);
        Todo existingTodo = new Todo(todoId, "Task 1", TodoStatus.OPEN);
        when(todoRepository.updateTodoById(todoId, updatedTodoRequest)).thenReturn(existingTodo);

        // When
        Todo result = todoService.updateTodoById(todoId, updatedTodoRequest);

        // Then
        assertEquals(existingTodo, result);
    }

    @Test
    void updateTodoById_ShouldReturnNull_WhenTodoDoesNotExist() {
        // Given
        String todoId = "nonexistent";
        TodoRequest updatedTodoRequest = new TodoRequest("Updated Task", TodoStatus.DONE);
        when(todoRepository.updateTodoById(todoId, updatedTodoRequest)).thenReturn(null);

        // When
        Todo result = todoService.updateTodoById(todoId, updatedTodoRequest);

        // Then
        assertNull(result);
    }

    @Test
    void deleteTodoById_ShouldReturnDeletedTodo_WhenTodoExists() {
        // Given
        String todoId = "1";
        Todo existingTodo = new Todo(todoId, "Task 1", TodoStatus.OPEN);
        when(todoRepository.getTodoById(todoId)).thenReturn(existingTodo);

        // When
        Todo result = todoService.deleteTodoById(todoId);

        // Then
        assertEquals(existingTodo, result);
        verify(todoRepository, times(1)).removeTodoById(todoId);
    }

    @Test
    void deleteTodoById_ShouldReturnNull_WhenTodoDoesNotExist() {
        // Given
        String todoId = "nonexistent";
        when(todoRepository.getTodoById(todoId)).thenReturn(null);

        // When
        Todo result = todoService.deleteTodoById(todoId);

        // Then
        assertNull(result);
        verify(todoRepository, never()).removeTodoById(todoId);
    }
}

