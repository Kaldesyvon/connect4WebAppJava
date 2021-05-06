package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.*;

public interface UserService {
    void addUser(UserEntity user);

    UserEntity getUser(String name);

    void reset();
}
