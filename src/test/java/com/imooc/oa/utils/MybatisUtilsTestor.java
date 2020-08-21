package com.imooc.oa.utils;

import org.junit.Test;

public class MybatisUtilsTestor {
    @Test
    public void testcase1(){
        String res = (String) MybatisUtils.executeQuery(sqlSession -> {
            String out = (String) sqlSession.selectOne("test.sample");
            return out;
        });
        System.out.println(res);
    }
}
