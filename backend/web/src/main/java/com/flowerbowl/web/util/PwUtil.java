package com.flowerbowl.web.util;

import java.util.Random;

public class PwUtil {

    /**
     * 
     * @return 랜덤 비밀번호 생성
     */
    public static String getRandomPassword() {
        StringBuilder password = new StringBuilder();
        Random rnd = new Random();

        String al = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        String num = "0123456789";

        for (int i = 0; i < 4; i++) {
            int randomIndex = rnd.nextInt(al.length());
            password.append(al.charAt(randomIndex));
        }

        for (int i = 0; i < 5; i++) {
            int randomIndex = rnd.nextInt(num.length());
            password.append(num.charAt(randomIndex));
        }

        password.append("!");
        shuffle(password);

        return password.toString();
    }

    private static void shuffle(StringBuilder password) {
        Random rnd = new Random();
        for (int i = password.length() - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            char temp = password.charAt(index);
            password.setCharAt(index, password.charAt(i));
            password.setCharAt(i, temp);
        }
    }
}
