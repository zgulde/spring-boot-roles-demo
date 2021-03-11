package com.zgulde.rolesdemo.controllers;

import com.zgulde.rolesdemo.repositories.StudentProfileDao;
import com.zgulde.rolesdemo.models.User;
import com.zgulde.rolesdemo.repositories.UserDao;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    private final UserDao userDao;
    private final StudentProfileDao studentProfileDao;

    public HomeController(UserDao userDao, StudentProfileDao studentProfileDao) {
        this.userDao = userDao;
        this.studentProfileDao = studentProfileDao;
    }

    @GetMapping("/")
    public String index(@AuthenticationPrincipal User user) {
        return "index";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/secret")
    @ResponseBody
    public String secret() {
        return "Only logged in users can see this!";
    }

    @GetMapping("/quizzes")
    @ResponseBody
    public String quizzes() {
        return "This is the quiz page.";
    }

    @GetMapping("/quizzes/admin")
    @ResponseBody
    public String quizzesAdmin() {
        return "admin quiz page.";
    }

}
