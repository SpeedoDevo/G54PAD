package hu.devo.bloom;

import java.util.stream.IntStream;

public class OriginalBloomFilter<E> extends SingleHashBloomFilter<E> {
    public OriginalBloomFilter(int size, int numHashes, HashFunction hashFunction) {
        super(size, numHashes, hashFunction);
    }

    public OriginalBloomFilter(double expectedInsertions, double falsePositives, HashFunction hashFunction) {
        super(expectedInsertions, falsePositives, hashFunction);
    }

    @Override
    IntStream getHashes(byte[] data) {
        return IntStream
                .rangeClosed(1, this.numHashes)
                .map(i -> this.nthHash(i, data));
    }
}
