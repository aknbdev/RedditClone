package security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import static io.jsonwebtoken.Jwts.parser;

@Service
public class JwtProvider {

    private KeyStore keyStore;

   @PostConstruct
   public void init() {
       try {
           keyStore = KeyStore.getInstance("JKS");
           InputStream stream = getClass().getResourceAsStream("/springblog.jks");
           keyStore.load(stream, "secret".toCharArray());
       } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
           throw new RuntimeException("Exception occurred while loading keystore");
       }
   }

    public String generateToken(Authentication authentication){
        User principal = (User) authentication.getPrincipal();

        return generateTokenWithUserMail(principal.getUsername());
    }

    public boolean validateToken(String jwt) {

       parser()
               .setSigningKey(getPublicKey())
               .parseClaimsJws(jwt);
       return true;
    }

    public String getEmailFromToken(String jwt) {

        Claims claims = parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(jwt)
                .getBody();

        return claims.getSubject();
    }


    private String generateTokenWithUserMail(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new RuntimeException("Exception occurred while retrieving public key from keystore");
        }
    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("springblog").getPublicKey();
        } catch (KeyStoreException e) {
            throw new RuntimeException("Exception occurred while retrieving public key from keystore");
        }
    }


}
