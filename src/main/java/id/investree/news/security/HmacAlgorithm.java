package id.investree.news.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

@Component
public class HmacAlgorithm {

    @Value("$key")
    private String key;

    private String toHexString(byte[] bytes) {
        String result = "";
        try (Formatter formatter = new Formatter()) {
            for (byte b : bytes) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        } catch (Exception ex) {
            return result;
        }
    }

    public String generate(String plain) throws InvalidKeyException, NoSuchAlgorithmException {
        return generate(plain, key, "HmacSHA256");
    }

    public String generate(String plain, String key, String alg) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), alg);
        Mac mac = Mac.getInstance(alg);
        mac.init(signingKey);
        return toHexString(mac.doFinal(plain.getBytes()));
    }
}
