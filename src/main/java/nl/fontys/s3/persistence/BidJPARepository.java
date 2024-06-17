package nl.fontys.s3.persistence;

import nl.fontys.s3.persistence.entity.BidEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidJPARepository extends JpaRepository<BidEntity, Integer> {
    List<BidEntity> findByUserId(int userId);
}
