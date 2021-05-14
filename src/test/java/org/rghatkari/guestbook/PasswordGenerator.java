package org.rghatkari.guestbook;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "Administrator";

        String encodedPassword = encoder.encode(password);
        System.out.println(encodedPassword);
    }
}
