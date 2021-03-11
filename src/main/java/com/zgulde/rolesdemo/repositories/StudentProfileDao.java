package com.zgulde.rolesdemo.repositories;

import com.zgulde.rolesdemo.models.StudentProfile;
import com.zgulde.rolesdemo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentProfileDao extends JpaRepository<StudentProfile, Long> {
    StudentProfile findByUser(User user);
}
