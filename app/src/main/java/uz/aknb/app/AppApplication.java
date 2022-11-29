package uz.aknb.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@SpringBootApplication(scanBasePackages = {"uz.aknb", "security"})
@EnableJpaRepositories(basePackages = {"db.repository"})
@EnableConfigurationProperties(RsaKeyProperties.class)
@EntityScan(basePackages = {"db.entity"})
@EnableTransactionManagement
@EnableJpaAuditing
@EnableAsync
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}
