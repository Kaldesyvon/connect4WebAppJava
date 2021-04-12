package sk.tuke.gamestudio.server.webservice;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentException;
import sk.tuke.gamestudio.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("api/comment")
public class CommentServiceRest {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public void addComment(@RequestBody Comment comment){ commentService.addComment(comment); }

    @GetMapping("/{game}")
    public List<Comment> getComments(@PathVariable String game){ return commentService.getComments(game); }

    @PostMapping(value = "/reset")
    public void reset(@RequestBody String body) throws CommentException {
        String password = new JSONObject(body).getString("password");
        if (password.equals("secret")) commentService.reset();
    }
}
