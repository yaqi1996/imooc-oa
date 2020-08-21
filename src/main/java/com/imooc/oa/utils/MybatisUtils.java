package com.imooc.oa.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Function;

public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory = null;
    //利用静态块在初始化类时实例化sqlSessionFactory
     static{
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            //初始化错误时，通过抛出ExceptionInInitializerError异常提醒调用者
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * 执行SELECT查询
     * @param func 要执行查询语句的代码块
     * @return 查询结果
     */
    public static Object executeQuery(Function<SqlSession, Object> func){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Object obj = func.apply(sqlSession);
            return obj;
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 执行INSERT DELETE UPDATE写操作SQL
     * @param func 要执行写操作的代码块
     * @return 查询结果
     */
    public static Object executeUpdate(Function<SqlSession, Object> func){
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            Object obj = func.apply(sqlSession);
            sqlSession.commit();
            return obj;
        } catch (RuntimeException e){
            sqlSession.rollback();
            throw e;
        } finally {
            sqlSession.close();
        }
    }

}
