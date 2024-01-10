package de.neuefische.paulkreft.neuefischerecaptodoapp.services.repositories;

import de.neuefische.paulkreft.neuefischerecaptodoapp.models.Todo;
import de.neuefische.paulkreft.neuefischerecaptodoapp.models.TodoRequest;
import de.neuefische.paulkreft.neuefischerecaptodoapp.models.TodoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TodoRepositoryTest {

    private TodoRepository todoRepository;

    @BeforeEach
    void setUp() {
        todoRepository = new TodoRepository();
    }

    @Test
    void getTodos_ShouldReturnEmptyList_WhenNoTodosAdded() {
        // When
        List<Todo> result = todoRepository.getTodos();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void addTodo_ShouldAddTodoToRepository() {
        // Given
        Todo todo = new Todo("1", "Task 1", TodoStatus.OPEN);

        // When
        todoRepository.addTodo(todo);
        List<Todo> result = todoRepository.getTodos();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(todo));
    }

    @Test
    void getTodoById_ShouldReturnTodo_WhenTodoExists() {
        // Given
        Todo todo = new Todo("1", "Task 1", TodoStatus.OPEN);
        todoRepository.addTodo(todo);

        // When
        Todo result = todoRepository.getTodoById("1");

        // Then
        assertNotNull(result);
        assertEquals(todo.id(), result.id());
        assertEquals(todo.description(), result.description());
        assertEquals(todo.status(), result.status());
    }

    @Test
    void getTodoById_ShouldReturnNull_WhenTodoDoesNotExist() {
        // When
        Todo result = todoRepository.getTodoById("nonexistent");

        // Then
        assertNull(result);
    }

    @Test
    void updateTodoById_ShouldReturnUpdatedTodo_WhenTodoExists() {
        // Given
        Todo todo = new Todo("1", "Task 1", TodoStatus.OPEN);
        todoRepository.addTodo(todo);
        TodoRequest updatedTodoRequest = new TodoRequest("Updated Task", TodoStatus.DONE);

        // When
        Todo result = todoRepository.updateTodoById("1", updatedTodoRequest);

        // Then
        assertNotNull(result);
        assertEquals("1", result.id());
        assertEquals(updatedTodoRequest.description(), result.description());
        assertEquals(updatedTodoRequest.status(), result.status());
    }

    @Test
    void updateTodoById_ShouldReturnNull_WhenTodoDoesNotExist() {
        // Given
        TodoRequest updatedTodoRequest = new TodoRequest("Updated Task", TodoStatus.DONE);

        // When
        Todo result = todoRepository.updateTodoById("nonexistent", updatedTodoRequest);

        // Then
        assertNull(result);
    }

    @Test
    void removeTodoById_ShouldRemoveTodo_WhenTodoExists() {
        // Given
        Todo todo = new Todo("1", "Task 1", TodoStatus.OPEN);
        todoRepository.addTodo(todo);

        // When
        todoRepository.removeTodoById("1");
        List<Todo> result = todoRepository.getTodos();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void removeTodoById_ShouldNotRemoveAnything_WhenTodoDoesNotExist() {
        // When
        todoRepository.removeTodoById("nonexistent");
        List<Todo> result = todoRepository.getTodos();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
