package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.entity.UserRegister;
@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserRegister, Integer>{

@Query("select e from OtpRegister e where e.otp=:otp") 
UserRegister findByText(@Param("otp") String otp);
	 
}
