package lab4;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Класс для работы с консолью.
 * <p>
 * Весь ввод данных от пользователя и весь вывод на экран в программе
 * выполняются только через этот класс.
 */
public class ConsoleUI {

    /** Сканер для чтения ввода пользователя с клавиатуры. */
    private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

    /**
     * Создаёт объект для работы с консолью, читающий ввод с клавиатуры в кодировке UTF-8.
     */
    public ConsoleUI() {
    }

    /**
     * Запрашивает у пользователя путь к файлу.
     * <p>
     * Если пользователь ничего не ввёл и нажал Enter, используется
     * значение по умолчанию.
     *
     * @param prompt       текст приглашения к вводу
     * @param defaultValue путь, используемый при пустом вводе
     * @return путь к файлу, введённый пользователем, или путь по умолчанию
     */
    public Path askPath(String prompt, String defaultValue) {
        System.out.print(prompt + " [" + defaultValue + "]: ");
        String input = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
        return Path.of(input.isEmpty() ? defaultValue : input);
    }

    /**
     * Выводит содержимое стека от вершины к дну.
     * <p>
     * Для обхода явно используется итератор стека
     * (методы {@code hasNext()} и {@code next()}).
     *
     * @param stack стек строк для вывода
     */
    public void showStack(LineStack stack) {
        System.out.println("Содержимое стека (от вершины к дну):");
        Iterator<String> iterator = stack.iterator();
        int position = 1;
        while (iterator.hasNext()) {
            System.out.println("  " + position + ": " + iterator.next());
            position++;
        }
    }

    /**
     * Выводит статистику по прочитанным строкам.
     *
     * @param lineCount количество строк
     * @param maxLength длина самой большой строки
     */
    public void showStats(int lineCount, int maxLength) {
        System.out.println("Количество строк: " + lineCount);
        System.out.println("Длина самой большой строки: " + maxLength);
    }

    /**
     * Выводит информационное сообщение.
     *
     * @param message текст сообщения
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Выводит сообщение об ошибке в поток ошибок.
     *
     * @param message текст сообщения об ошибке
     */
    public void showError(String message) {
        System.err.println(message);
    }
}
