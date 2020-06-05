package codedrinker.blog.service;

import codedrinker.blog.po.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);
    Comment save(Comment comment);
}
