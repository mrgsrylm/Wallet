package com.poywallet.poywalletbackend.domain.auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "jwt_refresh")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtRefresh {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence-jwt-refresh"
    )
    @SequenceGenerator(
            name = "sequence-jwt-refresh",
            sequenceName = "sequence_jwt_refresh",
            allocationSize = 5
    )
    private Long id;

    @Column(unique = true)
    public String token;

    @Column(name = "created_at")
    public Instant createdAt;
}
