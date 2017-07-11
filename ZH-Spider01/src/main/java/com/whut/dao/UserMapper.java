package com.whut.dao;

import com.whut.spider.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by fangjin on 2017/7/6.
 */
public interface UserMapper {

    void insertUser(User user);

    User selectUser(Integer id);

    User selectConcreteUser(Integer id);

    List<User> getAllUsers();
}
