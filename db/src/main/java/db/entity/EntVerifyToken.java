package db.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "verify_token")
public class EntVerifyToken extends EntBase{

    @Column(name = "token")
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    private EntUser user;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;
}
