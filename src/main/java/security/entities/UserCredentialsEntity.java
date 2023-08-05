package security.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_credentials")
public class UserCredentialsEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SequenceUserCredentialsId"
    )
    @SequenceGenerator(
            name = "SequenceUserCredentialsId",
            sequenceName = "users_credentials_id_seq",
            allocationSize = 1,
            initialValue = 5
    )
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
