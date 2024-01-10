package de.neuefische.paulkreft.neuefischerecaptodoapp.models;

import lombok.With;

import java.util.UUID;

@With
public record Todo(
        String id,
        String description,
        TodoStatus status
) {
    public Todo(TodoRequest request) {
        this(UUID.randomUUID().toString(), request.description(), request.status());
    }

    public Todo(String description) {
        this(UUID.randomUUID().toString(), description, TodoStatus.OPEN);
    }
}
