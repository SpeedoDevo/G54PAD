package hu.devo.util;

import hu.devo.bloom.SingleHashBloomFilter;
import hu.devo.hash.Murmur3;
import hu.devo.hash.SHA256;

import java.nio.ByteBuffer;
import java.util.function.Predicate;
import java.util.zip.CRC32;

public class Util {
    private static final CRC32 crc = new CRC32();
    public static final SingleHashBloomFilter.HashFunction MURMUR  =
            (n, data) -> (int) Murmur3.hash(data, 0x7F3A21EAL + (long) n);
    public static final SingleHashBloomFilter.HashFunction SHA =
            (n, data) -> (int) SHA256.hash(data, n);
    public static final Predicate<Boolean> IDENTITY = val -> val;

    private Util() { }

    public static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes, 0, Long.BYTES);
        buffer.flip();
        return buffer.getLong();
    }
}
