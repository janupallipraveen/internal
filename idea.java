public class IDEA {
    static int mul(int a, int b) { return (int) ((a == 0 ? 65536L : a) * (b == 0 ? 65536L : b) % 65537) & 65535; }
    static int inv(int x) { return java.math.BigInteger.valueOf(x == 0 ? 65536 : x).modInverse(java.math.BigInteger.valueOf(65537)).intValue() & 65535; }
    static int[] encKeys(int[] key) {                        // 128-bit key -> 52 subkeys
        int[] k = java.util.Arrays.copyOf(key, 52);
        for (int i = 8; i < 52; i++) k[i] = (k[i % 8 == 7 ? i - 15 : i - 7] << 9 | k[i % 8 < 6 ? i - 6 : i - 14] >> 7) & 65535;
        return k;
    }
    static int[] decKeys(int[] k) {                          // reverse key schedule for decryption
        int[] d = new int[52];
        for (int r = 0; r < 9; r++) {
            int p = 6 * (8 - r), q = 6 * r, s = r % 8 == 0 ? 1 : 2;
            d[q] = inv(k[p]); d[q + 3] = inv(k[p + 3]); d[q + 1] = -k[p + s] & 65535; d[q + 2] = -k[p + 3 - s] & 65535;
            if (r < 8) { d[q + 4] = k[p - 2]; d[q + 5] = k[p - 1]; }
        }
        return d;
    }
    static int[] crypt(int[] x, int[] k) {                   // one 64-bit block (4 x 16-bit words)
        int a = x[0], b = x[1], c = x[2], d = x[3], t, u, v;
        for (int p = 0; p < 48; p += 6) {
            a = mul(a, k[p]); b = b + k[p + 1] & 65535; c = c + k[p + 2] & 65535; d = mul(d, k[p + 3]);
            t = mul(a ^ c, k[p + 4]); u = mul((b ^ d) + t & 65535, k[p + 5]); v = t + u & 65535;
            t = b ^ v; b = c ^ u; c = t; a ^= u; d ^= v;
        }
        return new int[]{mul(a, k[48]), c + k[49] & 65535, b + k[50] & 65535, mul(d, k[51])};
    }
    public static void main(String[] args) {
        int[] ek = encKeys(new int[]{1, 2, 3, 4, 5, 6, 7, 8}), c = crypt(new int[]{0, 1, 2, 3}, ek), m = crypt(c, decKeys(ek));
        System.out.printf("Cipher: %04X %04X %04X %04X%n", c[0], c[1], c[2], c[3]);   // 11FB ED2B 0198 6DE5
        System.out.printf("Plain : %04X %04X %04X %04X%n", m[0], m[1], m[2], m[3]);
    }
}
