
package models.elements;

import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
    private static final String ALGORITHM = "AES"; 
    private static final String KEY = "2Loq480usIdK8aPM";
    
    public static String encrypt(String value) throws Exception{
        Key key = generateKey(); // generate the encapsulated hash key
        
        Cipher cipher = Cipher.getInstance(Encryption.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        
        String encryptedValue = Base64.getEncoder().encodeToString(encryptedByteValue);
        
        return encryptedValue;
    }
    
    public static String decrypt(String value) throws Exception{
        Key key = generateKey(); // generate the encapsulated hash key
        
        Cipher cipher = Cipher.getInstance(Encryption.ALGORITHM); // Returns a Cipher object that implements the specified transformation.
        cipher.init(Cipher.DECRYPT_MODE, key); // Initializes this cipher with the public key from the given certificate.
        byte [] decryptedValue64 = Base64.getDecoder().decode(value); // put the value in a byte array
        byte [] decryptedByteValue = cipher.doFinal(decryptedValue64); // decrypting and using of the key
        
        String decryptedValue = new String(decryptedByteValue, "utf-8");
        
        return decryptedValue;
    }
    
    private static Key generateKey() throws Exception{
        // Constructs a secret key from the given byte array.
        Key key = new SecretKeySpec(Encryption.KEY.getBytes(), Encryption.ALGORITHM); // encapsulate hash key
        return key;
    }
}