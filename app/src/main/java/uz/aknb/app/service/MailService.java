package uz.aknb.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import uz.aknb.app.dto.NotificationMail;
import uz.aknb.app.exception.RedditException;
import uz.aknb.app.server.ServerConfig;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class MailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private final ServerConfig serverConfig;

    public MailService(TemplateEngine templateEngine,
                       JavaMailSender javaMailSender, ServerConfig serverConfig) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
        this.serverConfig = serverConfig;
    }

    void sendVerifyMail(String to, String token) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("message", serverConfig.getServerUrl() + "/api/auth/verify-mail/" + token);
        sendMessage(
                new NotificationMail(
                        "Verify Mail", to, builder(variables, "mailTemplate")
                )
        );
    }

    @Async
    public void sendMessage(NotificationMail notificationMail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
            message.setTo(notificationMail.getRecipient());

            if (serverConfig.getMailFrom() != null) {
                message.setFrom(serverConfig.getMailFrom());
            } else {
                message.setFrom(serverConfig.getMailFrom());
            }
            message.setSubject(notificationMail.getSubject());
            message.setText(notificationMail.getBody(), true);
            log.info("Sending email to: {}", notificationMail.getRecipient());
            javaMailSender.send(mimeMessage);
            log.debug(
                    "Email has been sent: {}{}",
                    notificationMail.getRecipient(),
                    notificationMail.getSubject()
            );
        } catch (Exception e) {
            log.info("Notification mail: {}", notificationMail.getRecipient());
            log.error("Error:", e);
            throw new RedditException(e.getMessage());
        }
    }

    private String builder(Map<String, Object> variables, String templateName) {
        Context context = new Context();
        context.setVariables(variables);

        return templateEngine.process(templateName, context);
    }
}
