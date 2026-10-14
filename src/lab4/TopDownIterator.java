package lab4;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Итератор, обходящий {@link java.util.Stack} от вершины к дну (в порядке LIFO).
 * <p>
 * Вершина стека хранится в последней ячейке внутреннего {@code Vector},
 * поэтому обход идёт по индексам от {@code size() - 1} до 0.
 * Стек при обходе не изменяется.
 *
 * @param <T> тип элементов стека
 */
public class TopDownIterator<T> implements Iterator<T> {

    /** Стек, по которому выполняется обход. */
    private final Stack<T> stack;

    /** Размер стека на момент создания итератора, нужен для обнаружения изменений. */
    private final int expectedSize;

    /** Индекс следующего возвращаемого элемента. */
    private int index;

    /**
     * Конструктор по умолчанию. Создаёт итератор по пустому стеку:
     * {@link #hasNext()} сразу возвращает {@code false}.
     */
    public TopDownIterator() {
        this(new Stack<>());
    }

    /**
     * Создаёт итератор, установленный на вершину стека.
     *
     * @param stack стек для обхода
     */
    public TopDownIterator(Stack<T> stack) {
        this.stack = stack;
        this.expectedSize = stack.size();
        this.index = stack.size() - 1; // вершина стека — последний элемент Vector
    }

    /**
     * Проверяет, остались ли элементы для обхода.
     *
     * @return {@code true}, если есть следующий элемент
     */
    @Override
    public boolean hasNext() {
        return index >= 0;
    }

    /**
     * Возвращает следующий элемент, двигаясь от вершины стека к дну.
     *
     * @return следующий элемент стека
     * @throws ConcurrentModificationException если стек был изменён во время обхода
     * @throws NoSuchElementException          если элементы закончились
     */
    @Override
    public T next() {
        if (stack.size() != expectedSize) {
            throw new ConcurrentModificationException("Стек изменён во время обхода");
        }
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return stack.get(index--);
    }
}