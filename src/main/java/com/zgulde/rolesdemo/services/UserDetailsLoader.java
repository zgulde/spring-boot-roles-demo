package com.zgulde.rolesdemo.services;

import com.zgulde.rolesdemo.models.User;
import com.zgulde.rolesdemo.repositories.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsLoader implements UserDetailsService {
    private final UserDao userDao;

    public UserDetailsLoader(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user with username '" + username + "' found!");
        }
        return user;
    }
}
