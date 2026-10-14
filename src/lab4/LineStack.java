package lab4;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

/**
 * Стек строк.
 * <p>
 * Внутри используется {@link java.util.Stack}, но через композицию, а не наследование:
 * снаружи доступны только операции стека, а методы класса {@code Vector}
 * (например, {@code get(int)} или {@code add(int, Object)}) скрыты.
 * <p>
 * Стандартный итератор {@code java.util.Stack} унаследован от {@code Vector}
 * и обходит элементы снизу вверх. Поэтому класс возвращает собственный
 * итератор {@link TopDownIterator}, который обходит стек сверху вниз (LIFO).
 */
public class LineStack implements Iterable<String> {

    /** Внутреннее хранилище элементов стека. */
    private final Stack<String> stack = new Stack<>();

    /** Длина самой большой строки среди элементов стека. */
    private int maxLineLength = 0;

    /**
     * Создаёт пустой стек строк.
     */
    public LineStack() {
    }

    /**
     * Помещает строку на вершину стека.
     *
     * @param line добавляемая строка
     */
    public void push(String line) {
        stack.push(line);
        maxLineLength = Math.max(maxLineLength, line.length());
    }

    /**
     * Извлекает строку с вершины стека.
     *
     * @return строка, находившаяся на вершине стека
     * @throws EmptyStackException если стек пуст
     */
    public String pop() {
        if (stack.empty()) {
            throw new EmptyStackException();
        }
        String line = stack.pop();
        recalculateMaxLength();
        return line;
    }

    /**
     * Возвращает строку с вершины стека, не извлекая её.
     *
     * @return строка на вершине стека
     * @throws EmptyStackException если стек пуст
     */
    public String peek() {
        return stack.peek();
    }

    /**
     * Проверяет, пуст ли стек.
     *
     * @return {@code true}, если в стеке нет элементов
     */
    public boolean isEmpty() {
        return stack.empty();
    }

    /**
     * Возвращает количество строк в стеке.
     *
     * @return количество элементов стека
     */
    public int size() {
        return stack.size();
    }

    /**
     * Возвращает длину самой большой строки в стеке.
     *
     * @return длина самой большой строки или 0, если стек пуст
     */
    public int maxLineLength() {
        return maxLineLength;
    }

    /**
     * Возвращает итератор, обходящий стек от вершины к дну.
     *
     * @return итератор строк стека в порядке LIFO
     */
    @Override
    public Iterator<String> iterator() {
        return new TopDownIterator<>(stack);
    }

    /**
     * Пересчитывает длину самой большой строки после удаления элемента.
     */
    private void recalculateMaxLength() {
        maxLineLength = 0;
        for (String line : stack) {
            maxLineLength = Math.max(maxLineLength, line.length());
        }
    }
}
