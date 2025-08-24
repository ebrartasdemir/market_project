package com.market_p.market_p.mapper;

import com.market_p.market_p.dto.Auth.RegisterDto;
import com.market_p.market_p.entity.Cart;
import com.market_p.market_p.entity.Role;
import com.market_p.market_p.entity.User;
import com.market_p.market_p.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    @Autowired
    RoleRepository roleRepository;

    public User registerDtoToUser(String password,RegisterDto registerDto) {
        return new User(registerDto.getFirstName(), registerDto.getSurname(),password, registerDto.getEmail(), registerDto.getPhoneNum(),null);
    }
}
