package hu.devo.util;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Csv {
    private static final int MAX_CHARS_PER_COLUMN = 1024 * 8;

    public static <E> List<E> readIntoBeanList(String path, Class<E> klass) {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setDelimiterDetectionEnabled(true);
        settings.setMaxCharsPerColumn(MAX_CHARS_PER_COLUMN);

        BeanListProcessor<E> proc = new BeanListProcessor<>(klass);
        settings.setProcessor(proc);
        settings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(settings);
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        try (Reader reader = new InputStreamReader(new FileInputStream(path), "UTF-8")) {
            parser.parse(reader);

            return proc.getBeans();
        } catch (IOException e) {
            System.out.println("couldn't open file " + path);
            System.exit(1);
        }
        return null;
    }
}
