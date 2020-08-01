package net.suyudi.retail_uma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.suyudi.retail_uma.model.PasswordReset;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {

}