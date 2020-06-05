package codedrinker.blog.controller;

import codedrinker.blog.po.Blog;
import codedrinker.blog.po.Comment;
import codedrinker.blog.po.User;
import codedrinker.blog.service.BlogService;
import codedrinker.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog::commentList";
    }

    @PostMapping("/comments")
    public String postComment(Comment comment, HttpSession session){
        Long blogId = comment.getBlog().getId();
        Blog blog = blogService.getBlog(blogId);
        comment.setBlog(blog);
        User user = (User) session.getAttribute("user");
        if(user != null){
            comment.setAdminComment(true);
            comment.setAvatar(user.getAvatar());
        }else{
            comment.setAvatar(avatar);
        }
        commentService.save(comment);
        return "redirect:/comments/"+blogId;
    }
}
