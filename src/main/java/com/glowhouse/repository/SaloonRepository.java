package com.glowhouse.repository;

import com.glowhouse.entity.Saloon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaloonRepository extends JpaRepository <Saloon, Long> {

    @Query("SELECT s FROM Saloon s WHERE s.ownerId = :ownerId")
    List<Saloon> findByOwnerId(@Param("ownerId") Long ownerId);

    @Query(
            "SELECT s FROM Saloon s WHERE " +
                    "LOWER(s.city) LIKE LOWER(CONCAT('%', :data, '%')) OR " +
                    "LOWER(s.name) LIKE LOWER(CONCAT('%', :data, '%')) OR " +
                    "LOWER(s.address) LIKE LOWER(CONCAT('%', :data, '%'))"
    )
    public List<Saloon> getSalonDetailsBasedOnRequiredData(@Param("data") String data);

}
