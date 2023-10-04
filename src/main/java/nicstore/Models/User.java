package nicstore.Models;


import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.*;
import nicstore.security.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Введите имя")
    @Size(max = 100, message = "Максимум 100 символов")
    private String firstname;

    @NotBlank(message = "Введите фамилию")
    @Size(max = 100, message = "Максимум 100 символов")
    private String lastname;

    @NotBlank(message = "Email не указан")
    @Size(max = 100, message = "Максимум 100 символов")
    @Email
    private String email;

    @NotBlank(message = "Поле для пароля не может быть пустым")
    @Size(max = 100, message = "Максимум 100 символов")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
        return true;
    }
}
