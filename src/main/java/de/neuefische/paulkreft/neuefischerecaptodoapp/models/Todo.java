package de.neuefische.paulkreft.neuefischerecaptodoapp.models;

public record Todo(
        String description,
        TodoStatus status
) {
    public Todo(TodoRequest request) {
        this(request.description(), TodoStatus.OPEN);
    }
}
