package com.market_p.market_p.repository;

import com.market_p.market_p.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}
