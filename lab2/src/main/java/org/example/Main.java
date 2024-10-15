package org.example;

import org.example.entity.Match;

import java.util.ArrayList;

import static org.example.task.CountByCustomCollector.countByCustomCollector;
import static org.example.task.CountByDefaultCollector.countByDefaultCollector;
import static org.example.task.CountByLoop.countByLoop;
import static org.example.util.Generator.generateMatchCollection;

/**
 * Главный класс.
 */
public class Main {
    public static void main(String[] args) {
        // Количество секунд для задержки
        int delay = 5;

        // 2-4 задания
        execute(5000, delay);
        execute(50000, delay);
        execute(250000, delay);
    }

    /**
     * В данном методе происходит вызов необходимых функций и расчет затраченного времени.
     *
     * @param num   количество элементов, которое необходимо сгенерировать.
     * @param delay количество секунд, на которое нужно установить задержку.
     */
    private static void execute(int num, int delay) {
        System.out.printf("%n------------------Запуск для num = %d------------------%n", num);
        ArrayList<Match> matchArrayList = generateMatchCollection(num);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 12; ++i) {
            countByLoop(matchArrayList, delay);
        }
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.printf("Итеративное выполнение с num = %d. Время: %f с.%n", num, elapsedTime / 1000F / 12);

        start = System.currentTimeMillis();
        for (int i = 0; i < 12; ++i) {
            countByDefaultCollector(matchArrayList, delay);
        }
        elapsedTime = System.currentTimeMillis() - start;
        System.out.printf("Выполнение со встроенным коллектором с num = %d. Время: %f с.%n", num, elapsedTime / 1000F / 12);

        start = System.currentTimeMillis();
        for (int i = 0; i < 12; ++i) {
            countByCustomCollector(matchArrayList, delay);
        }
        elapsedTime = System.currentTimeMillis() - start;
        System.out.printf("Выполнение с собственным коллектором с num = %d. Время: %f с.%n", num, elapsedTime / 1000F / 12);
    }
}