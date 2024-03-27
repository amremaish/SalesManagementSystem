package com.sales.dao.repo;

import java.util.Optional;

import com.sales.dao.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	Optional<User> findByPhoneNumber(String phoneNumber);
}
