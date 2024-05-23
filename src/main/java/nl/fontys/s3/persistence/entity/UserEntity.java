package nl.fontys.s3.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Table (name = "user_entity")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String roles; // Store roles as a concatenated string

    public Set<String> getRolesSet() {
        if (roles == null || roles.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(Arrays.asList(roles.split(",")));
    }

    public void setRolesSet(Set<String> rolesSet) { this.roles = String.join(",", rolesSet).toUpperCase();}
}