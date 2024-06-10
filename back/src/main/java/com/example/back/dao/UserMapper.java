package com.example.back.dao;


import org.apache.ibatis.annotations.*;
import com.example.back.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT user_id, username, password, email, role, create_time, balance FROM user WHERE username = #{username}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "role", column = "role"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "balance", column = "balance")
    })
    User findByUsername(String username);
    @Select("SELECT user_id, username, password, email, role, create_time, balance FROM user WHERE user_id = #{id}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "role", column = "role"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "balance", column = "balance")
    })
    User findById(long id);
    @Update("UPDATE user SET password = #{password} WHERE user_id = #{userId}")
    void updatePassword(@Param("userId") Long userId, @Param("password") String password);
    @Update("UPDATE user SET  username = #{username}, password = #{password}, email = #{email}, role = #{role}, create_time = #{createTime}  WHERE user_id = #{userId}")
    void updateUser(User user);

    @Insert("INSERT INTO user(username, password, email, role, create_time, balance) VALUES(#{username}, #{password}, #{email}, #{role}, #{createTime}, #{balance})")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    void insertUser(User user);


    // 分页查询
    @Select("SELECT COUNT(*) FROM user")
    public int getUserCount();

    @Select("SELECT * FROM user LIMIT #{start}, #{size}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "role", column = "role"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "balance", column = "balance")
    })
    public List<User> getUserListByPage(@Param("start") int start, @Param("size") int size);

    // 计算模糊查询有多少条记录
    @Select("SELECT COUNT(*) FROM user WHERE username LIKE '%${name}%'")
    public int countUserListByName(@Param("name") String name);

    @Select("SELECT * FROM user WHERE username LIKE '%${name}%' LIMIT #{start}, #{size}")
    public List<User> getUserListByName(@Param("start") int start, @Param("size") int size, @Param("name") String name);

    @Delete("DELETE FROM user WHERE user_id = #{id}")
    void deleteUserById(@Param("id") Long id);
}

