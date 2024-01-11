package de.neuefische.paulkreft.neuefischerecaptodoapp.todo.models;

import lombok.With;

@With
public record TodoRequest(
        String description,
        TodoStatus status
) {
}
