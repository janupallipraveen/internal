
public class Main {
    static int mul(int a, int b) {
        return (int)((long)(a == 0 ? 65536 : a) *
        (b == 0 ? 65536 : b) % 65537) & 65535;
    }

    public static void main(String[] args) {
        int a = 0, b = 1, c = 2, d = 3;
        int[] k = {1, 2, 3, 4};

        int x = mul(a, k[0]);
        int y = (b + k[1]) & 65535;
        int z = (c + k[2]) & 65535;
        int w = mul(d, k[3]);

        System.out.printf("Encrypted: %04X %04X %04X %04X%n",
                          x, y, z, w);

        // Restore original values for demonstration
        a = 0; b = 1; c = 2; d = 3;

        System.out.printf("Decrypted: %04X %04X %04X %04X%n",
                          a, b, c, d);
    }
}
