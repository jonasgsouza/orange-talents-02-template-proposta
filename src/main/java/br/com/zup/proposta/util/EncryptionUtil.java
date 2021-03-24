package br.com.zup.proposta.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {
    private final String salt;
    private final String password;

    public EncryptionUtil(@Value("${encryption.password}") String password, @Value("${encryption.salt}") String salt) {
        this.password = password;
        this.salt = salt;
    }

    public String textEncrypt(String s) {
        return getTextEncryptor().encrypt(s);
    }

    public String textDecrypt(String s) {
        return getTextEncryptor().decrypt(s);
    }

    private TextEncryptor getTextEncryptor() {
        return Encryptors.text(password, salt);
    }

}
