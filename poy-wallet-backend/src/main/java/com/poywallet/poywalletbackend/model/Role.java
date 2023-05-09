package com.poywallet.poywalletbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@EqualsAndHashCode(of = {"type"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence-role"
    )
    @SequenceGenerator(
            name = "sequence-role",
            sequenceName = "sequence_role",
            allocationSize = 5
    )
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false, unique = true)
    private RoleEnum type;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public void addUser(User user) {
        users.add(user);
        user.getRoles().add(this);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.getRoles().remove(this);
    }
}
