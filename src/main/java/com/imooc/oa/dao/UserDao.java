package com.imooc.oa.dao;

import com.imooc.oa.entity.User;
import com.imooc.oa.utils.MybatisUtils;

public class UserDao {
    /**
     * 按用户名查询用户表
     * @param username
     * @return 用户对象包含用户信息，null则代表不存在
     */
    public User selectByUsername(String username){
        User user = (User) MybatisUtils.executeQuery(sqlSession -> sqlSession.selectOne("usermapper.selectByUsername", username));
        return user;
    }
}
