package org.example.task.forkjoinpool;

import org.example.entity.Match;
import org.example.entity.Team;
import org.example.entity.Tournament;
import org.example.enums.MatchType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * В данном классе вызываются методы для расчета статистических функций с использованием собственного коллектора.
 */
public class CustomForkJoinPoolAction extends RecursiveAction {
    /**
     * Объект класса {@link Logger}, используемый для логирования.
     */
    private final Logger LOGGER = Logger.getLogger(CustomForkJoinPoolAction.class.getName());

    private final List<Match> matchArrayList;

    private final int delay;

    private final int members1;

    private final int members2;

    private final int score1;

    private final int score2;

    private final int length;

    private final LocalDateTime localDateTime;

    private final MatchType matchType;

    private int result;

    /**
     * Главный метод, в котором происходит вызов всех методов для расчета. Также здесь выводится результат работы
     * каждого из методов.
     *
     * @param matchArrayList список объектов класса {@link Match}, для которых необходимо рассчитать статистические
     *                       характеристики.
     * @param delay          количество секунд, на которое нужно установить задержку.
     */
    public CustomForkJoinPoolAction(List<Match> matchArrayList, int delay) {
        this.matchArrayList = matchArrayList;
        this.delay = delay;
        this.members1 = 2;
        this.members2 = 3;
        this.score1 = 5;
        this.score2 = 10;
        this.length = 7;
        this.localDateTime = LocalDateTime.of(LocalDate.ofEpochDay(378), LocalTime.ofSecondOfDay(15 * 20 * 10));
        this.matchType = MatchType.DEATHMATCH;
    }

    @Override
    protected void compute() {
        if (matchArrayList.size() < 536) {
            System.out.printf("Вызов %s для списка размеров %d элементов.%n",
                    CustomForkJoinPoolAction.class.getName(), matchArrayList.size());



            return;
        }
        int midIndex = ((matchArrayList.size() / 2) - (((matchArrayList.size() % 2) > 0) ? 0 : 1));

        CustomForkJoinPoolTask firstHalfArrayValueSumCounter = new CustomForkJoinPoolTask(matchArrayList.subList(0, midIndex), delay);
        CustomForkJoinPoolTask secondHalfArrayValueSumCounter = new CustomForkJoinPoolTask(matchArrayList.subList(midIndex, matchArrayList.size()), delay);
        firstHalfArrayValueSumCounter.fork();
        secondHalfArrayValueSumCounter.fork();

        result += firstHalfArrayValueSumCounter.join() + secondHalfArrayValueSumCounter.join();
    }
}
