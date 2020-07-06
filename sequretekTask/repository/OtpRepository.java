package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.entity.OtpRegister;
@Repository
@Transactional
public interface OtpRepository extends JpaRepository<OtpRegister, Integer>{

@Query("select e from OtpRegister e where e.otp=:otp") 
OtpRegister findByText(@Param("otp") String otp);
	 
	 // OtpRegister findByText(String otp);
}
