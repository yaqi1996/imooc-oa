package com.imooc.oa.dao;

import com.imooc.oa.entity.Node;
import com.imooc.oa.utils.MybatisUtils;

import java.util.List;

public class RbacDao {
    /**
     * 按用户id查询对应功能
     * @param userId
     * @return 功能对象包含功能信息，null则代表不存在
     */
    public List<Node> selectNodeByUserId(Long userId){
        List<Node> list = (List) MybatisUtils.executeQuery(sqlSession -> sqlSession.selectList("rbacmapper.selectNodeByUserId", userId));
        return list;
    }
}
