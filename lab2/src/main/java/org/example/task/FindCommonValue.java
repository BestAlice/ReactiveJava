package org.example.task;

import org.example.entity.Match;

import java.util.ArrayList;

import static org.example.task.linear.CountByCustomCollectorLinear.countByCustomCollectorLinear;
import static org.example.task.linear.CountByDefaultCollectorLinear.countByDefaultCollectorLinear;
import static org.example.task.parallel.CountByCustomCollectorParallel.countByCustomCollectorParallel;
import static org.example.task.parallel.CountByDefaultCollectorParallel.countByDefaultCollectorParallel;
import static org.example.util.Generator.generateMatchCollection;

/**
 * Данный класс решает задачу поиска количества элементов, время обработки которого будет одинаково для параллельного и
 * последовательного стрима.
 */
public class FindCommonValue {
    /**
     * Переменная, обозначающая погрешность между полученными значениями времени параллельного и последовательного стрима.
     */
    private static final float eps = 0.0000000001f;

    /**
     * Данный метод позволяет определить примерное количество элементов, при котором параллельный и последовательный
     * стрим будут иметь одинаковое время выполнения. Далее, полученное значение проверяется с использованием JVH.
     *
     * @param delay количество секунд, на которое нужно установить задержку.
     */
    public static void getCommonCount(int delay) {
        float timeDefaultLinear = 1.0f;
        float timeCustomLinear = 1.0f;

        float timeDefaultParallel = 0.0f;
        float timeCustomParallel = 0.0f;

        int defaultCommonVal = -1;
        int customCommonVal = -1;

        for (int i = 2; Math.abs(timeDefaultLinear - timeDefaultParallel) > eps && Math.abs(timeCustomLinear - timeCustomParallel) > eps; i++) {
            ArrayList<Match> matchArrayList = generateMatchCollection(i);

            long start, elapsedTime;
            if (Math.abs(timeDefaultLinear - timeDefaultParallel) > eps) {
                start = System.currentTimeMillis();
                for (int j = 0; j < 12; ++j) {
                    countByDefaultCollectorParallel(matchArrayList, delay);
                }
                elapsedTime = System.currentTimeMillis() - start;
                timeDefaultParallel = elapsedTime / 1000F / 12;

                start = System.currentTimeMillis();
                for (int j = 0; j < 12; ++j) {
                    countByDefaultCollectorLinear(matchArrayList, delay);
                }
                elapsedTime = System.currentTimeMillis() - start;
                timeDefaultLinear = elapsedTime / 1000F / 12;
                defaultCommonVal = i;
            }

            if (Math.abs(timeCustomLinear - timeCustomParallel) > eps) {
                start = System.currentTimeMillis();
                for (int j = 0; j < 12; ++j) {
                    countByCustomCollectorParallel(matchArrayList, delay);
                }
                elapsedTime = System.currentTimeMillis() - start;
                timeCustomParallel = elapsedTime / 1000F / 12;

                start = System.currentTimeMillis();
                for (int j = 0; j < 12; ++j) {
                    countByCustomCollectorLinear(matchArrayList, delay);
                }
                elapsedTime = System.currentTimeMillis() - start;
                timeCustomLinear = elapsedTime / 1000F / 12;
                customCommonVal = i;
            }
        }
        System.out.printf("""
                Для стандартного коллектора:
                Время параллельного исполнения: %f секунд.
                Время линейного исполнения: %f секунд.
                Общее количество элементов: %d.
                Задержка: %d.
                %n""", timeDefaultParallel, timeDefaultLinear, defaultCommonVal, delay);

        System.out.printf("""
                Для собственного коллектора:
                Время параллельного исполнения: %f секунд.
                Время линейного исполнения: %f секунд.
                Общее количество элементов: %d.
                Задержка: %d.
                %n""", timeCustomParallel, timeCustomLinear, customCommonVal, delay);
    }
}