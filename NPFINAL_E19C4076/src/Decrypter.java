import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Decrypter {
    public static String decryptText(SecretKey key, String encryptedText){
        String plainText = null;
        try{
            Cipher cipher = Cipher.getInstance("AES");
            Base64.Decoder decoder = Base64.getDecoder();

            byte[] encryptedBytes = decoder.decode(encryptedText);

            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            plainText = new String(decryptedBytes);
            return plainText;
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | IllegalBlockSizeException
                | BadPaddingException ex){
            System.err.println(ex);
        }
        return null;
    }
}
