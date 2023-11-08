package sebastien.andreu.spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @Column(nullable=false, name = "email", unique=true)
    private String email;

    @Column(nullable=false, name = "pseudo", unique=true)
    private String pseudo;

    @Column(nullable=false, name = "password")
    private String password;

    @Column(nullable=false, name = "role")
    private String role;

    @Override
    public String toString() {
        return "{" +
                "\"userId\":" + userId +
                ", \"email\":\"" + email + '\"' +
                ", \"pseudo\":\"" + pseudo + "\"" +
                ", \"password\":\"" + password + '\"' +
                '}';
    }

}