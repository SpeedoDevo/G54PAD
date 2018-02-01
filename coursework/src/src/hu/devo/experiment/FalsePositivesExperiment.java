package hu.devo.experiment;

import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import hu.devo.bloom.BloomFilter;
import hu.devo.bloom.LessHashingBloomFilter;
import hu.devo.util.Url;
import hu.devo.util.Util;

import java.util.Arrays;
import java.util.List;

public class FalsePositivesExperiment {

    private static final int MAX_HASH_FUNCTIONS = 20;
    private static final int MAX_BITS_PER_ELEMENT = 10;
    private static final int BITS_PER_ELEMENT_SAMPLES = 100;

    static void run(List<Url> urls) {
        CsvWriterSettings settings = new CsvWriterSettings();
        settings.setMaxColumns(BITS_PER_ELEMENT_SAMPLES + 1);
        CsvWriter csv = new CsvWriter(System.out, settings);

        String[] bads = urls
                .stream()
                .filter(url -> !url.good)
                .map(url -> url.url)
                .toArray(String[]::new);
        String[] goods = urls
                .stream()
                .filter(url -> url.good)
                .map(url -> url.url)
                .toArray(String[]::new);

        csv.addValue("number of hash fns/bits per element");
        float bitsPerElementStep = (float) MAX_BITS_PER_ELEMENT / BITS_PER_ELEMENT_SAMPLES;
        for (float i = 1; i <= BITS_PER_ELEMENT_SAMPLES; i++) {
            float bitsPerElement = (float) Util.round(i * bitsPerElementStep, 1);
            csv.addValue(bitsPerElement);
        }
        csv.writeValuesToRow();

        for (int i = 1; i <= MAX_HASH_FUNCTIONS; i++) {
            csv.addValue(i);
            for (float j = 1; j <= BITS_PER_ELEMENT_SAMPLES; j++) {
                float bitsPerElement = (float) Util.round(j * bitsPerElementStep, 1);
                BloomFilter<String> filter =
                        new LessHashingBloomFilter<>(Math.round(bads.length * bitsPerElement), i, Util.MURMUR);
                Arrays.stream(bads)
                        .forEach(filter::add);

                long falsePositives = Arrays.stream(goods)
                        .map(filter::contains)
                        .filter(Util.IDENTITY)
                        .count();

                csv.addValue((float) falsePositives / goods.length);
            }

            csv.writeValuesToRow();
            csv.flush();
        }
    }
}
