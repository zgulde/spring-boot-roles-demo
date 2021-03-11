package com.zgulde.rolesdemo.repositories;

import com.zgulde.rolesdemo.models.InstructorProfile;
import com.zgulde.rolesdemo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorProfileDao extends JpaRepository<InstructorProfile, Long> {
    InstructorProfile findByUser(User user);
}
