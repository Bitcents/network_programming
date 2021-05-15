import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encrypter {

    public static String encryptText(SecretKey key, String plainText){
        String encryptedText = null;
        try{
            Cipher cipher = Cipher.getInstance("AES");
            byte[] plainTextBytes = plainText.getBytes();
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(plainTextBytes);
            Base64.Encoder encoder = Base64.getEncoder();
            encryptedText = encoder.encodeToString(encryptedBytes);
            return encryptedText;
        }
        catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | IllegalBlockSizeException
                | BadPaddingException exception
        ) {
            System.err.println(exception);
        }

        return null;
    }

}
