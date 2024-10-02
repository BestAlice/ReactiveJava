package org.example.collector;

import org.example.interfaces.IReactive;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Класс является собственной реализацией коллектора для взаимодействия с экземплярами интерфейса {@link IReactive}.
 */
public class ReactiveCollector implements Collector<IReactive, List<IReactive>, List<IReactive>> {
    /**
     * Создает новый изменяемый контейнер.
     *
     * @return Функция, которая возвращает новый изменяемый контейнер.
     */
    @Override
    public Supplier<List<IReactive>> supplier() {
        return ArrayList::new;
    }

    /**
     * Определяет как добавлять элементы в коллектор.
     *
     * @return Функция, добавляющая новый элемент в коллектор.
     */
    @Override
    public BiConsumer<List<IReactive>, IReactive> accumulator() {
        return List::add;
    }

    /**
     * Функция соединяет две коллекции в одну.
     *
     * @return Функция, которая позволяет соединять две коллекции в одну.
     */
    @Override
    public BinaryOperator<List<IReactive>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    /**
     * Функция выполняет преобразование накопленных значений в окончательный вид.
     *
     * @return Функция, которая преобразует накопленные значения в окончательный вид.
     */
    @Override
    public Function<List<IReactive>, List<IReactive>> finisher() {
        return Function.identity();
    }

    /**
     * Функция возвращает характеристики коллектора.
     *
     * @return Множество характеристик коллектора.
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Set.of();
    }
}