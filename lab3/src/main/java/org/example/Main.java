package org.example;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.example.entity.Match;
import org.example.task.CustomForkJoinPoolTask;
import org.example.task.ReactiveTask;
import org.example.task.subscriber.ReactiveSubscriber;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
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
        execute(500, delay);
//        runReactive();
    }

    private static void runReactive() {
        Flowable<Match> flowable = Flowable.create(emitter -> {
            ArrayList<Match> matchArrayList = generateMatchCollection(1000);
            for (Match match : matchArrayList) {
                emitter.onNext(match);
            }
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);

        ReactiveSubscriber subscriber = new ReactiveSubscriber("test");

        flowable.subscribeOn(Schedulers.io()).subscribe(subscriber);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
/*
 * Реактивно собираем с сайта, реактивно генерим, реактивно сохраняем в БД, реактивно шлем пользователю.
 По UI: сделать одну страницу, куда будут выводиться информация о всех матчах, на которые подписан.
 Сделать отображение того, что с прошлого момента просмотра появились новые данные=
 *
 *
 *
 */

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