package com.imooc.oa.dao;

import com.imooc.oa.entity.ProcessFlow;
import com.imooc.oa.utils.MybatisUtils;
import junit.framework.TestCase;

import java.util.Date;

public class ProcessFlowDaoTest extends TestCase {
    public void testInsert(){
        MybatisUtils.executeUpdate(sqlSession -> {
            ProcessFlowDao dao = sqlSession.getMapper(ProcessFlowDao.class);
            ProcessFlow flow = new ProcessFlow();
            flow.setFormId(3l);
            flow.setOperatorId(2l);
            flow.setAction("audit");
            flow.setReason("approved");
            flow.setReason("同意");
            flow.setCreateTime(new Date());
            flow.setAuditTime(new Date());
            flow.setOrderNo(1);
            flow.setState("ready");
            flow.setIsLast(1);
            dao.insert(flow);
            return null;
        });
    }

}