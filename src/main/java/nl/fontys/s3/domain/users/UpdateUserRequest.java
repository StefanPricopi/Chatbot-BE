package nl.fontys.s3.domain.users;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
    private Long userId;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    private Set<String> roles;
}
