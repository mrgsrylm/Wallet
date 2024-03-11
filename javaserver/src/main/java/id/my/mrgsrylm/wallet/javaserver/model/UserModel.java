package id.my.mrgsrylm.wallet.javaserver.model;

import id.my.mrgsrylm.wallet.javaserver.model.enums.RoleType;
import id.my.mrgsrylm.wallet.javaserver.model.enums.TokenClaims;
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

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false, unique = true)
    private RoleType role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<WalletModel> wallets = new HashSet<>();

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
        claims.put(TokenClaims.ROLES.getValue(), this.role);
        return claims;
    }
}
