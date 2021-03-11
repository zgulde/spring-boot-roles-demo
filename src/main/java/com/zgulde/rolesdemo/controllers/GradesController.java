package com.zgulde.rolesdemo.controllers;

import com.zgulde.rolesdemo.models.Grade;
import com.zgulde.rolesdemo.models.User;
import com.zgulde.rolesdemo.repositories.GradeDao;
import com.zgulde.rolesdemo.repositories.StudentProfileDao;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.stream.Stream;

@Controller
@Secured({"ROLE_INSTRUCTOR"})
@ResponseBody
@RequestMapping("/grades")
public class GradesController {
    private final GradeDao gradeDao;
    private final StudentProfileDao studentDao;

    public GradesController(GradeDao gradeDao, StudentProfileDao studentDao) {
        this.gradeDao = gradeDao;
        this.studentDao = studentDao;
    }

    @GetMapping
    @PreAuthorize("principal.username == ''")
    public String index() {
        return "grades index";
    }

    @Secured({"ROLE_STUDENT"})
    @GetMapping("/my")
    public Stream myGrades(@AuthenticationPrincipal User user) {
        return gradeDao.findByStudent(user).stream().map(Grade::getGrade);
    }

    @GetMapping("/show-students")
    public Stream showStudents() {
        return studentDao.findAll().stream().map(profile -> Map.of(
            "name", profile.getUser().getUsername(),
            "cohort", profile.getCohort()
        ));
    }
}
