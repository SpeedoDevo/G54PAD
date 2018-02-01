package hu.devo.hash;

public class Murmur3 {
    private static final int UNSIGNED_BYTE = 0xFF;
    private static final long UNSIGNED_LONG = 0xFFFFFFFFL;
    private static final int CONST_1 = 0xCC9E2D51;
    private static final int CONST_2 = 0x1B873593;

    public static long hash(byte[] data, long seed) {
        int length = data.length;
        int blocks = length >> 2;
        long hash = seed & UNSIGNED_LONG;

        for (int i = 0; i < blocks; i++) {
            int j = i << 2;

            long k = data[j] & UNSIGNED_BYTE;
            k |= (data[j + 1] & UNSIGNED_BYTE) << 8;
            k |= (data[j + 2] & UNSIGNED_BYTE) << 16;
            k |= (data[j + 3] & UNSIGNED_BYTE) << 24;

            k = (k * CONST_1) & UNSIGNED_LONG;
            k = Long.rotateLeft(k, 15);
            k = (k * CONST_2) & UNSIGNED_LONG;

            hash ^= k;
            hash = Long.rotateLeft(hash, 13);
            hash = (((hash * 5) & UNSIGNED_LONG) + 0xE6546B64L) & UNSIGNED_LONG;
        }

        int offset = (blocks << 2);
        long l = 0;

        switch (length & 3) {
            case 3:
                l ^= (data[offset + 2] << 16) & UNSIGNED_LONG;

            case 2:
                l ^= (data[offset + 1] << 8) & UNSIGNED_LONG;

            case 1:
                l ^= data[offset];
                l = (l * CONST_1) & UNSIGNED_LONG;
                l = Long.rotateLeft(l, 15);
                l = (l * CONST_2) & UNSIGNED_LONG;
                hash ^= l;
        }

        hash ^= length;

        hash ^= (hash >> 16) & UNSIGNED_LONG;
        hash = (hash * 0x85EBCA6B) & UNSIGNED_LONG;
        hash ^= (hash >> 13) & UNSIGNED_LONG;
        hash = (hash * 0xC2B2AE35) & UNSIGNED_LONG;
        hash ^= (hash >> 16) & UNSIGNED_LONG;

        return hash;
    }
}
