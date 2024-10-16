package org.example;

import org.example.entity.Match;
import org.example.task.forkjoinpool.CustomForkJoinPoolTask;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import static org.example.task.CountByLoop.countByLoop;
import static org.example.task.FindCommonValue.getCommonCount;
import static org.example.task.parallel.CountByCustomCollectorParallel.countByCustomCollectorParallel;
import static org.example.task.parallel.CountByDefaultCollectorParallel.countByDefaultCollectorParallel;
import static org.example.util.Generator.generateMatchCollection;

/**
 * Главный класс.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 3)
@Fork(value = 2, warmups = 1)
@Threads(4)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Thread)
public class Main {
    /**
     * Количество миллисекунд для задержки.
     **/
    private int delay;

    private ArrayList<Match> matchArrayList;

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }

    @Setup
    public void setupMethod() {
        delay = 5;
        matchArrayList = generateMatchCollection(5000);
    }

    @Benchmark
    public void executeLoop() {
        countByLoop(matchArrayList, delay);
    }

    @Benchmark
    public void executeDefaultCollector() {
        countByDefaultCollectorParallel(matchArrayList, delay);
    }

    @Benchmark
    public void executeCustomCollector() {
        countByCustomCollectorParallel(matchArrayList, delay);
    }

    @Benchmark
    public void executeCustomForkJoinPool() {
        CustomForkJoinPoolTask counter = new CustomForkJoinPoolTask(matchArrayList, delay);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(counter);
    }
}