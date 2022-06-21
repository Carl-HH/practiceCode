package com.carl.test.Dao;

import com.carl.test.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户的持久层接口
 */
public interface IUserDao {
    List<User> findAll();

    List<User> findByName(@Param("name") String name,@Param("id") Integer id,@Param("sexy") String sexy);
}
