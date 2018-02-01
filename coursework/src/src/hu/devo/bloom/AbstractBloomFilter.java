package hu.devo.bloom;

import hu.devo.util.Util;

import java.util.BitSet;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public abstract class AbstractBloomFilter<E> implements BloomFilter<E> {
    private static final IntUnaryOperator NON_NEGATIVE = val -> val < 0 ? ~val : val;
    private IntUnaryOperator inRange;
    protected BitSet bits;
    protected int numHashes;

    public AbstractBloomFilter(int size, int numHashes) {
        init(size, numHashes);
    }

    public AbstractBloomFilter(double expectedInsertions, double falsePositives) {
        int size = optimalSize(expectedInsertions, falsePositives);
        init(size, optimalHashes(expectedInsertions, size));
    }

    private void init(int size, int numHashes) {
        this.numHashes = numHashes;
        this.bits = new BitSet(size);
        this.inRange = val -> val % this.bits.size();
    }

    private int optimalHashes(double expectedInsertions, int size) {
        return (int) Math.max(1, Math.round(size / expectedInsertions * Math.log(2)));
    }

    private int optimalSize(double expectedInsertions, double falsePositives) {
        return (int) (-expectedInsertions * Math.log(falsePositives) / Math.pow(Math.log(2), 2));
    }

    abstract IntStream getHashes(byte[] data);

    private IntStream getHashIndices(byte[] data) {
        return getHashes(data)
                .map(NON_NEGATIVE)
                .map(inRange);
    }

    public void add(byte[] data) {
        getHashIndices(data)
                .forEach(index -> bits.set(index));
    }

    public void add(E data) {
        if (data instanceof String) {
            add(((String) data).getBytes());
        } else {
            add(String.valueOf(data.hashCode()).getBytes());
        }
    }

    public boolean contains(byte[] data) {
        return getHashIndices(data)
                .mapToObj(index -> bits.get(index))
                .allMatch(Util.IDENTITY);
    }

    public boolean contains(E data) {
        if (data instanceof String) {
            return contains(((String) data).getBytes());
        } else {
            return contains(String.valueOf(data.hashCode()).getBytes());
        }
    }
}
