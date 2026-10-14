package lab4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Консольный интерфейс программы.
 * <p>
 * Класс выполняет задание целиком: запрашивает у пользователя пути к файлам,
 * читает строки исходного файла в стек, записывает их в файл результата
 * в обратном порядке и выводит статистику. Весь ввод с клавиатуры и весь
 * вывод на экран в программе выполняются только через этот класс,
 * а работа с файлами поручена {@link TextFileService}.
 */
public class ConsoleUI {

    /** Путь к исходному файлу, используемый при пустом вводе. */
    private static final String DEFAULT_INPUT = "input.txt";

    /** Путь к файлу результата, используемый при пустом вводе. */
    private static final String DEFAULT_OUTPUT = "output.txt";

    /** Сканер для чтения ввода пользователя с клавиатуры. */
    private final Scanner scanner;

    /** Объект для чтения и записи текстовых файлов. */
    private final TextFileService fileService;

    /**
     * Конструктор по умолчанию. Создаёт консольный интерфейс
     * со стандартным объектом для работы с файлами.
     */
    public ConsoleUI() {
        this(new TextFileService());
    }

    /**
     * Создаёт консольный интерфейс с заданным объектом для работы с файлами.
     *
     * @param fileService объект для работы с файлами
     */
    public ConsoleUI(TextFileService fileService) {
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        this.fileService = fileService;
    }

    /**
     * Выполняет задание.
     * <p>
     * Последовательность действий: запросить пути к файлам, прочитать строки
     * в стек, записать их в файл результата в обратном порядке, вывести
     * содержимое стека и статистику. Ошибки работы с файлами перехватываются
     * и выводятся пользователю, программа при этом не падает.
     */
    public void run() {
        Path input = askPath("Введите путь к исходному файлу", DEFAULT_INPUT);
        Path output = askPath("Введите путь к файлу результата", DEFAULT_OUTPUT);

        try {
            List<String> lines = fileService.readLines(input);

            LineStack stack = new LineStack();
            for (String line : lines) {
                stack.push(line);
            }

            // LineStack реализует Iterable, его итератор идёт от вершины к дну,
            // поэтому строки записываются в обратном порядке, а стек остаётся целым
            fileService.writeLines(output, stack);

            showStack(stack);
            showStats(stack.size(), stack.maxLineLength());
            System.out.println("Строки в обратном порядке сохранены в файл: " + output.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Ошибка работы с файлом: " + e.getMessage());
        }
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
    private Path askPath(String prompt, String defaultValue) {
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
    private void showStack(LineStack stack) {
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
    private void showStats(int lineCount, int maxLength) {
        System.out.println("Количество строк: " + lineCount);
        System.out.println("Длина самой большой строки: " + maxLength);
    }
}