package sk.tuke.gamestudio.game.connect4.service;

import org.junit.Assert;
import org.junit.Test;
import sk.tuke.gamestudio.game.connect4.entity.Comment;

import java.util.Date;

public class CommentServiceTest {
    CommentService service = CommentServiceJDBC.getInstance();

    public CommentServiceTest() throws CommentException { }

    @Test
    public void addComment() throws CommentException {
        service.reset();

        service.addComment(new Comment("martin", "connect4", "paradna hra", new Date()));
        service.addComment(new Comment("jozin", "connect4", "jedu takhle taborit", new Date()));
        service.addComment(new Comment("zbazin", "connect4", "skodou sto na oravu", new Date()));

        var comments = service.getComments("connect4");

        Assert.assertEquals(3, comments.size());

        Assert.assertEquals("martin", comments.get(0).getPlayer());
        Assert.assertEquals("paradna hra", comments.get(0).getComment());

        Assert.assertEquals("zbazin", comments.get(2).getPlayer());
        Assert.assertEquals("skodou sto na oravu", comments.get(2).getComment());
    }

    @Test
    public void resetComments() throws CommentException {
        service.addComment(new Comment("martin", "connect4", "paradna hra", new Date()));
        service.addComment(new Comment("jozin", "connect4", "jedu takhle taborit", new Date()));
        service.addComment(new Comment("zbazin", "connect4", "skodou sto na oravu", new Date()));

        service.reset();

        var comments = service.getComments("connect4");

        Assert.assertEquals(0, comments.size());
    }
}
