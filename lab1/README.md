# Лабораторная работа №1

## Задание
1. Написать для согласованной предметной области как минимум 3 базовых класса и генераторы объектов. Генератор должен 
уметь создавать указанное количество различных объектов сответствующего класса со случайными (но при этом валидными) 
характеристиками. Класс, представляющий собой массовый объект должен обязательно содержать поля следующих типов:
   - Один из примитивов (`int`, `long`, `double`).
   - `String`.
   - Дата/время (`LocalDate`, `LocalTime`, ...).
   - `Enum`.
   - `Record`.
   - Массив или коллекция.

   Остальные поля - произвольные, какие нужны для предметной области.  
   Два оставшихся класса должны представлять собой дополнительные атрибуты и характеристики массового класса.
2. С помощью генератора создать коллекцию объектов.
3. Написать код, реализующий расчет согласованных агрегированных статических данных тремя способами:
   - Итерационным циклом по коллекции.
   - Конвейером с помощью Stream API на базе коллекторов из стандартной библиотеки.
   - Конвейером с помощью собственного коллектора.
4. Для каждого варианта измерить время выполнения, зафиксировав моменты начала и окончания расчета для количества 
элементов в коллекции - 5000, 50000 и 250000. Время измерять с помощью методов класса `System` или `Instant`.