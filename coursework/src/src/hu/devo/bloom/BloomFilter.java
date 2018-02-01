package hu.devo.bloom;

public interface BloomFilter<E> {
    void add(E elem);
    boolean contains(E elem);
}
