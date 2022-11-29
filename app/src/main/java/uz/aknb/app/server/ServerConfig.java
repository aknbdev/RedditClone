package uz.aknb.app.server;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ServerConfig {

    @Value("${mail.reply.from}")
    private String mailFrom;

    @Value("${server.url}")
    private String serverUrl;
}
