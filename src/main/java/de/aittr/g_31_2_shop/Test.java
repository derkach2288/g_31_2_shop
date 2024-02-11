package de.aittr.g_31_2_shop;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {
    public static void main(String[] args) {

        // получение зашифрованного пароля
        String password = "asd";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode(password);
        System.out.println(encodePassword);
    }
}
