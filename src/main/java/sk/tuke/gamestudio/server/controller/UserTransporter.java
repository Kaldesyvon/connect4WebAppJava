package sk.tuke.gamestudio.server.controller;

import sk.tuke.gamestudio.entity.UserEntity;

public class UserTransporter {

    private static UserEntity user;

    public static boolean isLogged() {
        return user != null;
    }

    public static void setUser(UserEntity user) {
        UserTransporter.user = user;

    }
    public static UserEntity getUser() {
        return user;
    }
}
