package com.carl.test.Dao;

import com.carl.test.domain.User;

import java.util.List;

/**
 * 用户的持久层接口
 */
public interface IUserDao {
    List<User> findAll();
}
