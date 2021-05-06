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

    private final UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login(String login, String password) {
        user = userService.getUser(login);
        if(user == null){
            userService.addUser(new UserEntity(login, password));
            return "redirect:/";
        }
        if (user.getPassword().equals(password)) {
            return "redirect:/connect4";
        }
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout() {
        user = null;
        return "redirect:/";
    }

    public UserEntity getUser() {
        return user;
    }

    public boolean isLogged() {
        return user != null;
    }
}
