package org.example.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private RoleType name;

    public Role(RoleType name) {
        this.name = name;
    }

    public Role() {

    }

    public static Role fromString(String roleName) {
        RoleType roleType = RoleType.valueOf(roleName.toUpperCase());
        return new Role(roleType);
    }
}
