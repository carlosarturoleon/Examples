import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PayloadDecryptor {
    public static void main(String[] args) throws Exception {
        // Encrypted data from Node.js (Base64-encoded)
        String base64encrypted = "2e9saJxZYtS1O8YF9LrTqlUYqDiuRnnyqMDP3FdAWaVfx6/RPseOgIRNoklQnb2jw/Co"; // Replace with actual data
        byte[] encryptedData = Base64.getDecoder().decode(base64encrypted);

        ByteBuffer buffer = ByteBuffer.wrap(encryptedData);

        // Extract the IV (first 12 bytes)
        byte[] iv = new byte[12];
        buffer.get(iv);

        // Extract the encrypted payload and tag
        byte[] payloadWithTag = new byte[buffer.remaining()];
        buffer.get(payloadWithTag);

        // Initialize the cipher for decryption
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv);
        SecretKeySpec secretKeySpec = new SecretKeySpec(getKey(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);

        // Decrypt the payload including the tag
        byte[] decryptedPayload = cipher.doFinal(payloadWithTag);
        String plaintext = new String(decryptedPayload, StandardCharsets.UTF_8);

        System.out.println("Decrypted Payload: " + plaintext);
    }

    private static byte[] getKey() {
        // Provide the key used for encryption (Base64-encoded)
        String base64Key = "g9wBSmGbhhSR5qyCSrFYCJwh6xHV0ZpOk47Vd0OKeF4="; // Replace with actual key
        return Base64.getDecoder().decode(base64Key);
    }
}
