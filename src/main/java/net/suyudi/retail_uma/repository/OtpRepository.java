package net.suyudi.retail_uma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.suyudi.retail_uma.model.Otp;

public interface OtpRepository extends JpaRepository<Otp, Long> {

}