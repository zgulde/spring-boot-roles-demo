package com.zgulde.rolesdemo.repositories;

import com.zgulde.rolesdemo.models.Grade;
import com.zgulde.rolesdemo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeDao extends JpaRepository<Grade, Long> {
    public List<Grade> findByStudent(User student);
}
