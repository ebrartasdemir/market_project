package com.market_p.market_p.repository;

import com.market_p.market_p.entity.Adress;
import com.market_p.market_p.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdressRepository extends JpaRepository<Adress,Integer> {
    List<Adress> findAllByUserId(int userId);
    Adress findByTitle(String title);
}
