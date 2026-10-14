package lab4;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Класс, описывающий сценарий работы программы.
 * <p>
 * Последовательность действий: запросить пути к файлам, прочитать строки
 * исходного файла в стек, записать строки в файл результата в обратном
 * порядке и показать статистику. С консолью класс напрямую не работает,
 * весь ввод и вывод выполняется через {@link ConsoleUI}.
 */
public class App {

    /** Объект для работы с консолью. */
    private final ConsoleUI ui;

    /** Объект для чтения и записи текстовых файлов. */
    private final TextFileService fileService;

    /**
     * Создаёт приложение.
     *
     * @param ui          объект для работы с консолью
     * @param fileService объект для работы с файлами
     */
    public App(ConsoleUI ui, TextFileService fileService) {
        this.ui = ui;
        this.fileService = fileService;
    }

    /**
     * Запускает выполнение задания.
     * <p>
     * Ошибки работы с файлами перехватываются и выводятся пользователю
     * через {@link ConsoleUI#showError(String)}, программа при этом не падает.
     */
    public void run() {
        Path input = ui.askPath("Введите путь к исходному файлу", "input.txt");
        Path output = ui.askPath("Введите путь к файлу результата", "output.txt");

        try {
            List<String> lines = fileService.readLines(input);

            LineStack stack = new LineStack();
            for (String line : lines) {
                stack.push(line);
            }

            // LineStack реализует Iterable, его итератор идёт от вершины к дну,
            // поэтому строки записываются в обратном порядке, а стек остаётся целым
            fileService.writeLines(output, stack);

            ui.showStack(stack);
            ui.showStats(stack.size(), stack.maxLineLength());
            ui.showMessage("Строки в обратном порядке сохранены в файл: " + output.toAbsolutePath());
        } catch (IOException e) {
            ui.showError("Ошибка работы с файлом: " + e.getMessage());
        }
    }
}
