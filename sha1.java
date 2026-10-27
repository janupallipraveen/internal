import java.security.MessageDigest;
import java.util.Scanner;

public class SHA1 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter text: ");
        String text = sc.nextLine();

        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(text.getBytes("UTF-8"));

        System.out.print("SHA-1 Digest: ");
        for (byte b : digest) {
            System.out.printf("%02x", b & 0xff);
        }

        sc.close();
    }
}
