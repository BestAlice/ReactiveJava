package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.interfaces.IReactive;

import java.util.List;

/**
 * Класс содержит информацию о команде.
 */
@Data
@AllArgsConstructor
public class Team implements IReactive {
    /**
     * Название команды.
     */
    private String name;

    /**
     * Список участников команды.
     */
    private List<String> members;

    /**
     * Количество побед, которое одержала команда.
     */
    private int wins;

    /**
     * Количество поражений команды.
     */
    private int loses;
}