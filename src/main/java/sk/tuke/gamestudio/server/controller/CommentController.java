package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.UserEntity;
import sk.tuke.gamestudio.service.CommentService;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {
    private boolean playerFound;
    private boolean logged;
    @Autowired
    private CommentService commentService;

    @RequestMapping()
    public String showComments(@RequestParam(required = false)String player, Model model) {
        playerFound = true;
        if (player == null || player.equals("")){
            model.addAttribute("comments", commentService.getComments("connect4"));
        }
        else {
            List<Comment> comments = commentService.getCommentsByPlayer("connect4", player);
            model.addAttribute("comments", comments);
            if (comments.isEmpty())
                playerFound = false;
        }
        return "comment";
    }

    @RequestMapping("/add")
    public String addComment(@RequestParam String comment) {
        if (comment != null) {
            commentService.addComment(new Comment(UserTransporter.getUser().getLogin(), "connect4", comment, new Date()));
        }
        return "redirect:/comment";
    }

    public boolean isFound() { return playerFound; }
}
