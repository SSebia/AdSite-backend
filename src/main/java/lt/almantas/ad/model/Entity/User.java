package lt.almantas.ad.model.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lt.almantas.ad.model.fdto.UserRegisterFDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "\"user\"")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String username;
    private String email;
    private String password;
    private int roleID;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AdComment> comments;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public User(UserRegisterFDTO userRegisterFDTO) {
        this.username = userRegisterFDTO.getUsername();
        this.email = userRegisterFDTO.getEmail();
        this.password = userRegisterFDTO.getPassword();
    }

    public User() {

    }

    public void addComment(AdComment adComment) {
        comments.add(adComment);
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}