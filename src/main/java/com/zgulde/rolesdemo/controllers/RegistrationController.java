package com.zgulde.rolesdemo.controllers;

import com.zgulde.rolesdemo.models.InstructorProfile;
import com.zgulde.rolesdemo.models.StudentProfile;
import com.zgulde.rolesdemo.models.User;
import com.zgulde.rolesdemo.repositories.InstructorProfileDao;
import com.zgulde.rolesdemo.repositories.StudentProfileDao;
import com.zgulde.rolesdemo.repositories.UserDao;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private final StudentProfileDao studentProfileDao;
    private final InstructorProfileDao instructorProfileDao;
    private final UserDao userDao;
    private final PasswordEncoder encoder;

    public RegistrationController(StudentProfileDao studentProfileDao, InstructorProfileDao instructorProfileDao, UserDao userDao, PasswordEncoder encoder) {
        this.studentProfileDao = studentProfileDao;
        this.instructorProfileDao = instructorProfileDao;
        this.userDao = userDao;
        this.encoder = encoder;
    }

    @GetMapping("/register/student")
    public String showStudentRegistrationForm(Model model) {
        StudentProfile profile = new StudentProfile();
        profile.setUser(new User());
        model.addAttribute("profile", profile);
        return "registration/student";
    }

    @PostMapping("/register/student")
    public String registerStudent(@ModelAttribute StudentProfile profile) {
        profile.getUser().setRole("ROLE_STUDENT");
        profile.getUser().setPassword(encoder.encode(profile.getUser().getPassword()));
        userDao.save(profile.getUser());
        studentProfileDao.save(profile);
        login(profile.getUser());
        return "redirect:/";
    }

    @GetMapping("/register/instructor")
    public String showInstructorRegistrationForm(Model model) {
        InstructorProfile profile = new InstructorProfile();
        profile.setUser(new User());
        model.addAttribute("profile", profile);
        return "registration/instructor";
    }

    @PostMapping("/register/instructor")
    public String registerInstructor(@ModelAttribute InstructorProfile profile) {
        profile.getUser().setRole("ROLE_INSTRUCTOR");
        profile.getUser().setPassword(encoder.encode(profile.getUser().getPassword()));
        userDao.save(profile.getUser());
        instructorProfileDao.save(profile);
        login(profile.getUser());
        return "redirect:/";
    }

    private void login(User user) {
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())
        );
    }
}
