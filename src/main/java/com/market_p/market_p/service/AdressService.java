package com.market_p.market_p.service;

import com.market_p.market_p.dto.AdressDto;
import com.market_p.market_p.entity.Adress;

import java.util.List;

public interface AdressService {
    List<Adress> getAllAdresses();
    List<Adress> getAdressByUser(int userId);
    Adress getAdressByTitle(String title);
    Adress getAdressById(int id);
    void updateAdress(int id,AdressDto adressDto);
    void addAdress(int userId, AdressDto adressDto);
    void deleteAdress(int id);
}
