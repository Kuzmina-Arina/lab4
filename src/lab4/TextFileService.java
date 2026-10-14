package lab4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для чтения и записи текстовых файлов.
 * <p>
 * Все файлы читаются и записываются в кодировке UTF-8, чтобы корректно
 * обрабатывать русский текст независимо от настроек операционной системы.
 */
public class TextFileService {

    /**
     * Создаёт объект для работы с текстовыми файлами.
     */
    public TextFileService() {
    }

    /**
     * Читает все строки текстового файла по порядку.
     * <p>
     * Пустые строки тоже считаются строками и попадают в результат.
     *
     * @param path путь к файлу
     * @return список строк файла в исходном порядке; для пустого файла пустой список
     * @throws NoSuchFileException если файл не существует
     * @throws IOException         если произошла ошибка чтения
     */
    public List<String> readLines(Path path) throws IOException {
        if (!Files.exists(path)) {
            throw new NoSuchFileException(path.toAbsolutePath().toString(), null, "файл не найден");
        }
        List<String> lines = new ArrayList<>();
        BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } finally {
            reader.close(); // закрываем файл даже при ошибке чтения
        }
        return lines;
    }

    /**
     * Записывает строки в файл в том порядке, в котором их выдаёт итератор коллекции.
     * <p>
     * Если файл уже существует, он перезаписывается.
     *
     * @param path  путь к файлу результата
     * @param lines коллекция строк для записи
     * @throws IOException если произошла ошибка записи
     */
    public void writeLines(Path path, Iterable<String> lines) throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
        try {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } finally {
            writer.close(); // close() также сбрасывает буфер (flush) на диск
        }
    }
}
