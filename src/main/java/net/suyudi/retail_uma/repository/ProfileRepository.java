package net.suyudi.retail_uma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.suyudi.retail_uma.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

	@Query("FROM Profile WHERE user_id = :id")
    Profile findByUser(@Param("id") Long id);

}