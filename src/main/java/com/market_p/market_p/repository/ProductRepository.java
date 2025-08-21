package com.market_p.market_p.repository;

import com.market_p.market_p.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByCategoryId(int categoryId);
    List<Product> findByName(String name);
    boolean existsById(int id);


}
