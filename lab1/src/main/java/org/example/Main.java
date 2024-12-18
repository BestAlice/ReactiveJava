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
        // 2 и 3 задания
        execute(100);

        // 4 задание
        execute(5000);
        execute(50000);
        execute(250000);
    }

    /**
     * В данном методе происходит вызов необходимых функций и расчет затраченного времени.
     *
     * @param num количество элементов, которое необходимо сгенерировать.
     */
    private static void execute(int num) {
        System.out.printf("%n------------------Запуск для num = %d------------------%n", num);
        // 2 задание
        ArrayList<Match> matchArrayList = generateMatchCollection(num);

        // 3 и 4 задания
        long start = System.currentTimeMillis();
        for (int i = 0; i < 12; ++i) {
            countByLoop(matchArrayList);
        }
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.printf("Итеративное выполнение с num = %d. Время: %f с.%n", num, elapsedTime / 1000F / 12);

        start = System.currentTimeMillis();
        for (int i = 0; i < 12; ++i) {
            countByDefaultCollector(matchArrayList);
        }
        elapsedTime = System.currentTimeMillis() - start;
        System.out.printf("Выполнение со встроенным коллектором с num = %d. Время: %f с.%n", num, elapsedTime / 1000F / 12);

        start = System.currentTimeMillis();
        for (int i = 0; i < 12; ++i) {
            countByCustomCollector(matchArrayList);
        }
        elapsedTime = System.currentTimeMillis() - start;
        System.out.printf("Выполнение с собственным коллектором с num = %d. Время: %f с.%n", num, elapsedTime / 1000F / 12);
    }
}