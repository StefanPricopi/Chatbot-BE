package nl.fontys.s3.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "two_factor_auth_entity")
public class TwoFactorAuthEntity {
    @Id
    private Long userid;
    private String code;
}