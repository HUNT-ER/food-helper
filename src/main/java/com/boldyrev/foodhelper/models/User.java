package com.boldyrev.foodhelper.models;

import com.boldyrev.foodhelper.security.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_name")
    @NotNull(message = "Username can't be null")
    @Pattern(message = "Username should have only a-zA-Z0-9_ symbols", regexp = "\\w+")
    @Size(message = "Username length should be between 5 and 25 symbols", min = 5, max = 25)
    private String username;

    @Column(name = "password")
    @Size(message = "Password length should be equal or greater than 8", min = 8)
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role can't be null")
    private Role role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator")
    private List<Recipe> recipes;
}
