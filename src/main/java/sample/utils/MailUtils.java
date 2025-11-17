package sample.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

/** メール送信ユーティリティ */
@Component
@RequiredArgsConstructor
public class MailUtils {
    /** メール送信DI */
    private final JavaMailSender mailSender;
    /** 送信元メールアドレス */
    @Value("${spring.mail.properties.from}")
    private String from;

    @Builder
    public record MailSenderObject(String to, String subject, String body) {
    }

    /**
     * メール送信
     * 
     * @param obj メール送信オブジェクト
     */
    public void sendMail(MailSenderObject obj) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        try {
            message.setFrom(from);
            message.setTo(obj.to());
            message.setSubject(obj.subject());
            message.setText(obj.body());
            mailSender.send(message);
        } catch (MailException e) {
            throw new MailSendException("メールの送信に失敗しました。", e);
        }
    }

}
