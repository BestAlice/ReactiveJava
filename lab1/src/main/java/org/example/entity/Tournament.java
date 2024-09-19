package org.example.entity;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public record Tournament(String name, String place, LocalDate startDate, LocalDate endDate) {
    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "Tournament{name=%s, place=%s, startDate=%s, endDate=%s}"
                .formatted(this.name, this.place, this.startDate, this.endDate);
    }
}