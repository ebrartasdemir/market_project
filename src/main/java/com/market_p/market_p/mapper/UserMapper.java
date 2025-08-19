package com.market_p.market_p.mapper;

import com.market_p.market_p.dto.RegisterDto;
import com.market_p.market_p.entity.Role;
import com.market_p.market_p.entity.User;
import com.market_p.market_p.repository.RoleRepository;
import com.market_p.market_p.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    @Autowired
    RoleRepository roleRepository;

    public User registerDtoToUser(String password,RegisterDto registerDto) {
        Role role=roleRepository.findByTitle("USER").get();
        return new User(registerDto.getFirstName(), registerDto.getSurname(),password, registerDto.getEmail(), registerDto.getPhoneNum(),role);
    }
}
