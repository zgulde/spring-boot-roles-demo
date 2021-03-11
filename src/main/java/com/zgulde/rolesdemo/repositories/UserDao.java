package com.zgulde.rolesdemo.repositories;

import com.zgulde.rolesdemo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
}
