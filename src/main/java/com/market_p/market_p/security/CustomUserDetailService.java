package com.market_p.market_p.security;

import com.market_p.market_p.entity.User;
import com.market_p.market_p.example.constants.Messages;
import com.market_p.market_p.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(String.format(Messages.User.RECORD_NOT_FOUND_BY_EMAIL,email)));
        return new User(user.getName(), user.getSurname(),user.getPassword(),user.getUsername(),user.getPhone(),user.getRoles());
    }
    //burası bir diğerinde farklı devdekinde user userDetails iimplemente ediyor

}
