package academy.productstore.persistence.entity;

import academy.productstore.validation.ValidEmail;
import academy.productstore.validation.ValidPhoneNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
public class Account implements UserDetails {

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

    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "accounts_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = Collections.singleton(new Role(1L, "ROLE_USER"));

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return firstname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return getId() == account.getId();
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }
}