package uz.aknb.app.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import db.entity.EntUser;
import db.entity.EntVerifyToken;
import db.repository.VerifyTokenRepository;
import uz.aknb.app.exception.RedditException;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class VerifyTokenService {

    private final VerifyTokenRepository verifyTokenRepository;


    public String  create(EntUser user) {

        String token = UUID.randomUUID().toString();

        EntVerifyToken verifyToken = new EntVerifyToken(token, user, LocalDateTime.now().plusMinutes(15));

        verifyTokenRepository.save(verifyToken);

        return token;
    }

    public EntUser isValidToken(String token) {
        EntVerifyToken verifyToken = verifyTokenRepository.findByToken(token).orElseThrow(
                ()-> new RedditException("Verify token not found")
        );

        if (verifyToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            log.error("Verify token expired user mail: {}", verifyToken.getUser().getEmail());
            throw new RedditException("Verify token expired");
        }
        return verifyToken.getUser();
    }
}
