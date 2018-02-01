package hu.devo.experiment;

import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import hu.devo.bloom.BloomFilter;
import hu.devo.bloom.LessHashingBloomFilter;
import hu.devo.bloom.OriginalBloomFilter;
import hu.devo.util.Timer;
import hu.devo.util.Url;
import hu.devo.util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class ComparisonExperiment {
    private static final int MAX_HASHES = 50;
    private static final int DATA_SLICES = 10;
    private static final int SAMPLE_COUNT = 100;
    private static final int K_BYTE = 1024 * 8;

    public static void run(List<Url> urls) {
        _run(urls, nHashes -> new OriginalBloomFilter<>(K_BYTE, nHashes, Util.MURMUR));
        _run(urls, nHashes -> new LessHashingBloomFilter<>(K_BYTE, nHashes, Util.MURMUR));
    }

    private static void _run(List<Url> urls, Function<Integer, BloomFilter<String>> filterSupplier) {
        Timer t = new Timer();
        CsvWriter csv = new CsvWriter(System.out, new CsvWriterSettings());

        String[] bads = urls
                .stream()
                .filter(url -> !url.good)
                .map(url -> url.url)
                .toArray(String[]::new);
        int badsCount = bads.length;
        long sliceSize = (long) Math.ceil((double) badsCount / DATA_SLICES);

        csv.addValue("number of hash fns/inserted elements");
        for (int i = 1; i <= DATA_SLICES; i++) {
            long upTo = i == DATA_SLICES ? badsCount : sliceSize * i;
            csv.addValue(upTo);
        }
        csv.writeValuesToRow();

        for (int i = 1; i <= MAX_HASHES; i++) {
            csv.addValue(i);
            BloomFilter<String> filter = filterSupplier.apply(i);

            for (int j = 1; j <= DATA_SLICES; j++) {
                long upTo = j == DATA_SLICES ? badsCount : sliceSize * j;

                for (int k = 0; k < SAMPLE_COUNT; k++) {
                    t.sampleStart("add");
                    Arrays.stream(bads)
                            .limit(upTo)
                            .forEach(filter::add);
                    t.sampleEnd("add");
                }

                csv.addValue(t.measurementEnd("add"));
            }
            csv.writeValuesToRow();
            csv.flush();
        }
    }
}
