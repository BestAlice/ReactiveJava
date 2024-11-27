package org.example;

import org.example.entity.Match;
import org.example.task.CustomForkJoinPoolTask;
import org.example.task.ReactiveTask;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

import static org.example.util.Generator.generateMatchCollection;

/**
 * Главный класс.
 */
//@BenchmarkMode(Mode.AverageTime)
//@OutputTimeUnit(TimeUnit.SECONDS)
//@Warmup(iterations = 3)
//@Fork(value = 2, warmups = 1)
//@Threads(4)
//@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
//@State(Scope.Thread)
public class Main {
    public static void main(String[] args) {
        int delay = 5;
        execute(2000, delay);
    }

    private static void execute(int num, int delay) {
        System.out.printf("%n------------------Запуск для num = %d------------------%n", num);
        ArrayList<Match> matchArrayList = generateMatchCollection(num);
        ReactiveTask reactiveTask = new ReactiveTask(matchArrayList, delay);

        int iterations = 1;

        long start = System.currentTimeMillis();
        for (int i = 0; i < iterations; ++i) {
            reactiveTask.executeReactive();
        }
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.printf("Реактивное выполнение с num = %d. Время: %f с.%n", num, elapsedTime / 1000F / iterations);

        CustomForkJoinPoolTask counter = new CustomForkJoinPoolTask(matchArrayList, delay);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        start = System.currentTimeMillis();
        for (int i = 0; i < iterations; ++i) {
            forkJoinPool.invoke(counter);
        }
        elapsedTime = System.currentTimeMillis() - start;
        System.out.printf("\n\nВыполнение с ForkJoinPool с num = %d. Время: %f с.%n", num, elapsedTime / 1000F / iterations);
    }
}