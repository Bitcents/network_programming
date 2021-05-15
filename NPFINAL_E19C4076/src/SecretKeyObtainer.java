import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

public class SecretKeyObtainer {
    public static SecretKey getSecretKey(){
        final String keyStoreFile = "secretkeystore.jks";
        final String password = "thisIsASimplePassword";
        SecretKey key = null;
        try{
            File file = new File(keyStoreFile);
            final KeyStore keyStore = KeyStore.getInstance("JCEKS");
            keyStore.load(new FileInputStream(file), password.toCharArray());
            KeyStore.PasswordProtection keyPassword = new KeyStore.PasswordProtection("anotherPassword?".toCharArray());
            KeyStore.Entry entry = keyStore.getEntry("initialKey", keyPassword);
            key = ((KeyStore.SecretKeyEntry)entry).getSecretKey();
            return key;
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | UnrecoverableEntryException e) {
            e.printStackTrace();
        }

        return null;
    }
}
