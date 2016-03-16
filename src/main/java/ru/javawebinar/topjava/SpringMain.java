package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.meal.UserMealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {

            System.out.println("--== Beans ==--");
            System.out.println(
            Arrays.asList(appCtx.getBeanDefinitionNames())
                    .stream()
                    .filter(s -> !s.contains("org.springframework.context"))
                    .collect(Collectors.toList()).toString()
            );

            // User Test -----------------------------------------------------------------------------------

            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);

            System.out.println("--== adminUserController.GETALL(newUser) ==--");
            adminUserController.getAll().stream().forEach(System.out::println);

            System.out.println("--== adminUserController.GETBYEMAIL(email) ==--");
            System.out.println(adminUserController.getByMail("alejandro@mail.com"));

            System.out.println("--== adminUserController.CREATE(newUser) ==--");
            System.out.println(adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN)));

            System.out.println("--== adminUserController.GET(6) ==--");
            System.out.println(adminUserController.get(6));

            System.out.println("--== adminUserController.UPDATE(6) ==--");
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(Role.ROLE_ADMIN);
            roleSet.add(Role.ROLE_USER);
            adminUserController.update(new User(null, "userName5", "email5", "password5", 3000, false, roleSet), 6);

            System.out.println("--== adminUserController.GET(6) ==--");
            System.out.println(adminUserController.get(6));

            System.out.println("--== adminUserController.DELETE(6) ==--");
            adminUserController.delete(6);

            System.out.println("--== adminUserController.GET(6) ==--");
            System.out.println(adminUserController.get(6));

            // User Test -----------------------------------------------------------------------------------

            // UserMeal Test -----------------------------------------------------------------------------------

            UserMealRestController userMealRestController = appCtx.getBean(UserMealRestController.class);
            ProfileRestController profileRestController = appCtx.getBean(ProfileRestController.class);

            System.out.println("--== userMealRestController.GETALL(for loggedin user) ==--");
            userMealRestController.getAll(profileRestController.get().getId()).stream().forEach(System.out::println);

            System.out.println("--== userMealRestController.CREATE ==--");
            System.out.println(
                    userMealRestController.create(new UserMeal(LocalDateTime.now(), "Завтрак и Ужин", 1500,
                            new User(null, "userName2", "email2", "password2", Role.ROLE_USER).getId()))
            );

            System.out.println("--== userMealRestController.GET(19) ==--");
            System.out.println(userMealRestController.get(19));

            System.out.println("--== userMealRestController.UPDATE(19) ==--");
                    userMealRestController.update(new UserMeal(LocalDateTime.now(), "Завтрак и Ужин + десерт", 2500,
                            new User(null, "userName3", "email2", "password2", Role.ROLE_USER).getId()), 19);

            System.out.println("--== userMealRestController.GET(19) ==--");
            System.out.println(userMealRestController.get(19));

            System.out.println("--== userMealRestController.DELETE(19) ==--");
            userMealRestController.delete(19);

            System.out.println("--== userMealRestController.GET(19) ==--");
            System.out.println(userMealRestController.get(19));

            // UserMeal Test -----------------------------------------------------------------------------------
        }
    }
}
