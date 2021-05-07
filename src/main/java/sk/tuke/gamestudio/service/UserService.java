package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.UserEntity;

public interface UserService {
    void addUser(UserEntity user);

    UserEntity getUser(String name);

    void reset();
}
