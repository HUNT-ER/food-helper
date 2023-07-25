package com.boldyrev.foodhelper.services.impl;

import com.boldyrev.foodhelper.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersService usersService;

    @Autowired
    public UserDetailsServiceImpl(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new com.boldyrev.foodhelper.security.UserDetails(
            usersService.findByUsername(username));
    }
}
