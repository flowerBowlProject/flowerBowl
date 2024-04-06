package com.flowerbowl.web.util;

import java.util.Random;

public class EmailUtil {

    /**
     * 이메일 인증 시 랜덤 번호 발생 메소드(6자리)
     */
    public static String getRandomValue() {
        StringBuilder certificationNum = new StringBuilder();
        Random rnd = new Random();

        String mailCondition = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < 6; i++) {
            int randomIndex = rnd.nextInt(mailCondition.length());
            certificationNum.append(mailCondition.charAt(randomIndex));
        }

        return certificationNum.toString();
    }
}
