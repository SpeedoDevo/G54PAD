package hu.devo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Timer {
    private Map<String, List<Long>> measurements = new HashMap<>();
    private Map<String, Long> starts = new HashMap<>();

    public void sampleStart(String id) {
        starts.put(id, System.nanoTime());
    }

    public long sampleEnd(String id) {
        long end = System.nanoTime();

        if (!starts.containsKey(id)) {
            throw new IllegalStateException("no start time for sample");
        } else {
            long duration = end - starts.get(id);
            List<Long> samples = measurements.getOrDefault(id, new ArrayList<>());
            samples.add(duration);
            measurements.put(id, samples);

            return duration;
        }
    }

    public double measurementEnd(String id) {
        if (!measurements.containsKey(id)) {
            throw new IllegalStateException("no measurements yet");
        } else {
            double avg = measurements
                    .get(id)
                    .stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(-1);

            measurements.remove(id);
            starts.remove(id);

            return avg;
        }
    }
}
