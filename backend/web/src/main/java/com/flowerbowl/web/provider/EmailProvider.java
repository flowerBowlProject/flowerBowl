package com.flowerbowl.web.provider;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmailProvider {

    private final JavaMailSender javaMailSender;
    private final String SUBJECT = "[flowerBowl] 이메일 인증 메일입니다.";

    public boolean sendCertification(String email, String certificationNum) {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            String htmlContent = getCertificationMessage(certificationNum);

            messageHelper.setTo(email);
            messageHelper.setSubject(SUBJECT);
            messageHelper.setText(htmlContent, true);

            javaMailSender.send(message);

        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        return true;
    }

    private String getCertificationMessage(String certificationNum) {

        String certificationMessage = "";
        certificationMessage += "<h1 style=\"text-align: center\">[flowerBowl] 이메일 인증 메일</h1>" +
                "<h3 style=\"text-align: center\">" +
                "  인증코드 :" +
                "  <strong style=\"font-size: 32px; letter-spacing: 8px\">" +
                certificationNum +
                "</strong>" +
                "</h3>";
        return certificationMessage;
    }
}
