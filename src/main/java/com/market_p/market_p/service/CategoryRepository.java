package com.market_p.market_p.service;

import com.market_p.market_p.entity.Category;
import org.springframework.data.repository.Repository;

import java.util.List;

interface CategoryRepository extends Repository<Category, Integer> {
    List<Category> getCategoryById(int id);
}
