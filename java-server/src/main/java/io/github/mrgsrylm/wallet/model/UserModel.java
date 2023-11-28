package io.github.mrgsrylm.wallet.model;

import io.github.mrgsrylm.wallet.model.enums.TokenClaims;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@EqualsAndHashCode(of = {"username", "email"})
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 20, nullable = false, unique = true)
    private String username;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleModel> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<WalletModel> wallets = new HashSet<>();

    public void addRole(RoleModel roleModel) {
        roles.add(roleModel);
        roleModel.getUsers().add(this);
    }

    public void removeRole(RoleModel roleModel) {
        roles.remove(roleModel);
        roleModel.getUsers().remove(this);
    }

    public void addWallet(WalletModel walletModel) {
        wallets.add(walletModel);
        walletModel.setUser(this);
    }

    public void removeWallet(WalletModel walletModel) {
        wallets.remove(walletModel);
        walletModel.setUser(null);
    }

    public Map<String, Object> getClaims() {
        final Map<String, Object> claims = new HashMap<>();
        claims.put(TokenClaims.ID.getValue(), this.id);
        claims.put(TokenClaims.USERNAME.getValue(), this.username);
        claims.put(TokenClaims.ROLES.getValue(), this.roles);
        return claims;
    }
}
