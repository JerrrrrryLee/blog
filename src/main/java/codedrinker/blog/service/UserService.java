package codedrinker.blog.service;

import codedrinker.blog.po.User;

public interface UserService {
    User checkUser(String username, String password);
}
