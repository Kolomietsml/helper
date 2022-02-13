package academy.productstore.domain;

import academy.productstore.validation.ValidEmail;
import academy.productstore.validation.ValidPhoneNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "Field is mandatory")
    private String firstname;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Field is mandatory")
    private String lastname;

    @Column(name = "phone", nullable = false)
    @NotBlank(message = "Field is mandatory")
    @ValidPhoneNumber
    private String phone;

    @Column(name = "email", nullable = false)
    @NotBlank(message = "Field is mandatory")
    @ValidEmail
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Field is mandatory")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "accounts_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = Collections.singleton(new Role(1L, "ROLE_USER"));
}