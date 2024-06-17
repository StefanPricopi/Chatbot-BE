package nl.fontys.s3.persistence;

import nl.fontys.s3.persistence.entity.TwoFactorAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwoFactorAuthRepository extends JpaRepository<TwoFactorAuthEntity, Long> {
}