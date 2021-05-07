package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.UserEntity;
import sk.tuke.gamestudio.service.UserService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class IndexController {
    private UserEntity user;
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login(String login, String password) {
        user = userService.getUser(login);
        if (user == null) {
            user = new UserEntity(login, password);
            UserTransporter.setUser(user);
            userService.addUser(user);
        } else if (user.getPassword().equals(password)) {
            UserTransporter.setUser(user);
            return "redirect:/connect4";
        }
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout() {
        user = null;
        UserTransporter.setUser(null);
        return "redirect:/";
    }

    public UserEntity getUser() {
        return UserTransporter.getUser();
    }

    public boolean isLogged() {
        return UserTransporter.isLogged();
    }
}
