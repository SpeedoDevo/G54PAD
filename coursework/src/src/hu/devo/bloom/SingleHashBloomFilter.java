package hu.devo.bloom;

public abstract class SingleHashBloomFilter<E> extends AbstractBloomFilter<E> {
    protected final HashFunction hashFunction;

    public SingleHashBloomFilter(int size, int numHashes, HashFunction hashFunction) {
        super(size, numHashes);
        this.hashFunction = hashFunction;
    }

    public SingleHashBloomFilter(double expectedInsertions, double falsePositives, HashFunction hashFunction) {
        super(expectedInsertions, falsePositives);
        this.hashFunction = hashFunction;
    }

    protected int nthHash(int n, byte[] data) {
        return this.hashFunction.apply(n, data);
    }

    public interface HashFunction {
        int apply(int n, byte[] data);
    }
}
