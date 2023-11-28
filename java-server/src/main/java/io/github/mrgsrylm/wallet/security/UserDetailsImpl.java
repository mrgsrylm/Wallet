package io.github.mrgsrylm.wallet.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.mrgsrylm.wallet.model.UserModel;
import io.github.mrgsrylm.wallet.model.enums.TokenClaims;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;

    @JsonIgnore
    private String password;

    private Map<String, Object> tokenClaims;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(UserModel userModel) {
        final List<SimpleGrantedAuthority> authorities = userModel.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getType().name()))
                .toList();

        final Map<String, Object> claims = new HashMap<>();
        claims.put(TokenClaims.ID.getValue(), userModel.getId());
        claims.put(TokenClaims.USERNAME.getValue(), userModel.getUsername());
        claims.put(TokenClaims.ROLES.getValue(), userModel.getRoles());

        return new UserDetailsImpl(
                userModel.getId(),
                userModel.getUsername(),
                userModel.getPassword(),
                claims,
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
