package io.github.mrgsrylm.wallet.model;

import io.github.mrgsrylm.wallet.model.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "roles")
@EqualsAndHashCode(of = {"type"})
public class RoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false, unique = true)
    private RoleType type;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.MERGE)
    private Set<UserModel> users = new HashSet<>();

    public void addUser(UserModel userModel) {
        users.add(userModel);
        userModel.getRoles().add(this);
    }

    public void removeUser(UserModel userModel) {
        users.remove(userModel);
        userModel.getRoles().remove(this);
    }
}
