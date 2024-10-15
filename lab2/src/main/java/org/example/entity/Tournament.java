package org.example.entity;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

/**
 * Record класс, содержащий информацию о турнире.
 *
 * @param name      название турнира.
 * @param place     место проведения турнира.
 * @param startDate дата начала турнира.
 * @param endDate   дата окончания турнира.
 */
public record Tournament(String name, String place, LocalDate startDate, LocalDate endDate) {
    /**
     * Метод позволяет представить объекта класса {@link Tournament} в виде строки.
     *
     * @return Строковое представление объекта класса {@link Tournament}.
     */
    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "Tournament{name=%s, place=%s, startDate=%s, endDate=%s}"
                .formatted(this.name, this.place, this.startDate, this.endDate);
    }
}