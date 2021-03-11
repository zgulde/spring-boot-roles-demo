package com.zgulde.rolesdemo;

import com.github.javafaker.Faker;
import com.zgulde.rolesdemo.models.Grade;
import com.zgulde.rolesdemo.models.InstructorProfile;
import com.zgulde.rolesdemo.models.StudentProfile;
import com.zgulde.rolesdemo.models.User;
import com.zgulde.rolesdemo.repositories.GradeDao;
import com.zgulde.rolesdemo.repositories.InstructorProfileDao;
import com.zgulde.rolesdemo.repositories.StudentProfileDao;
import com.zgulde.rolesdemo.repositories.UserDao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final UserDao userDao;
    private final PasswordEncoder encoder;
    private final StudentProfileDao studentProfileDao;
    private final InstructorProfileDao instructorProfileDao;
    private final GradeDao gradeDao;

    public DatabaseSeeder(UserDao userDao, PasswordEncoder encoder, StudentProfileDao studentProfileDao, InstructorProfileDao instructorProfileDao, GradeDao gradeDao) {
        this.userDao = userDao;
        this.encoder = encoder;
        this.studentProfileDao = studentProfileDao;
        this.instructorProfileDao = instructorProfileDao;
        this.gradeDao = gradeDao;
    }

    @Override
    public void run(String... args) throws Exception {
        Random random = new Random();
        Faker faker = new Faker();

        User zach = new User();
        zach.setUsername("zach");
        zach.setPassword(encoder.encode("codeup"));
        zach.setEmail("zach@example.com");
        zach.setRole("ROLE_INSTRUCTOR");
        InstructorProfile ip = new InstructorProfile();
        ip.setUser(zach);
        ip.setProgram("Data Science");
        userDao.save(zach);
        instructorProfileDao.save(ip);

        User cody = new User();
        cody.setUsername("cody");
        cody.setPassword(encoder.encode("codeup"));
        cody.setEmail("cody@example.com");
        cody.setRole("ROLE_STUDENT");
        StudentProfile sp = new StudentProfile();
        sp.setUser(cody);
        sp.setCohort("Arches");
        userDao.save(cody);
        studentProfileDao.save(sp);

        IntStream.range(0, 10).mapToObj(i -> {
            User user = new User();
            user.setUsername(faker.funnyName().name());
            user.setPassword(encoder.encode("codeup"));
            user.setEmail(faker.internet().emailAddress());
            StudentProfile profile = new StudentProfile();
            profile.setUser(user);
            profile.setCohort(new String[]{"Arches", "Hampton", "Curie", "Jupiter"}[random.nextInt(4)]);
            userDao.save(user);
            return studentProfileDao.save(profile);
        });

        gradeDao.saveAll(Arrays.asList(
            new Grade(cody, zach, 90),
            new Grade(cody, zach, 80),
            new Grade(cody, zach, 75),
            new Grade(cody, zach, 95),
            new Grade(cody, zach, 100)
        ));
    }
}
