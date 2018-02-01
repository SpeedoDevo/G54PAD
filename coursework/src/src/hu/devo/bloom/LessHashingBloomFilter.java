package hu.devo.bloom;

import java.util.stream.IntStream;

public class LessHashingBloomFilter<E> extends SingleHashBloomFilter<E> {
    public LessHashingBloomFilter(int size, int numHashes, HashFunction hashFunction) {
        super(size, numHashes, hashFunction);
    }

    public LessHashingBloomFilter(double expectedInsertions, double falsePositives, HashFunction hashFunction) {
        super(expectedInsertions, falsePositives, hashFunction);
    }

    @Override
    IntStream getHashes(byte[] data) {
        int hash1 = nthHash(1, data);
        int hash2 = nthHash(2, data);

        return IntStream
                .rangeClosed(1, this.numHashes)
                .map(i -> hash1 + i * hash2);
    }
}
