import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class SymmetricKeyStoreCreation {
    private static KeyStore createKeyStore(String filename, String password){
        try {
            File file = new File(filename);
            final KeyStore keyStore = KeyStore.getInstance("JCEKS");
            if(file.exists()){
                keyStore.load(new FileInputStream(file), password.toCharArray());
            } else {
                keyStore.load(null, null);
                keyStore.store(new FileOutputStream(filename), password.toCharArray());
            }
            return keyStore;
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException ex){
            System.err.println(ex);
        }
        return null;
    }

    public static void main(String[] args){
        try{
            final String keyStoreFile = "secretkeystore.jks";
            final String password = "thisIsASimplePassword";
            KeyStore keyStore = createKeyStore(keyStoreFile, password);
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecretKey key = keyGenerator.generateKey();
            KeyStore.SecretKeyEntry keyStoreEntry = new KeyStore.SecretKeyEntry(key);
            KeyStore.PasswordProtection keyPassword = new KeyStore.PasswordProtection("anotherPassword?".toCharArray());
            keyStore.setEntry("initialKey", keyStoreEntry, keyPassword);
            keyStore.store(new FileOutputStream(keyStoreFile), password.toCharArray());
        }
        catch (NoSuchAlgorithmException | KeyStoreException | IOException | CertificateException e) {
            e.printStackTrace();
        }
    }
}
