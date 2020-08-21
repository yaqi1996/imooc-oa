package com.imooc.oa.dao;

import com.imooc.oa.entity.LeaveForm;
import com.imooc.oa.entity.ProcessFlow;

import java.util.List;

public interface ProcessFlowDao {
    public void insert(ProcessFlow flow);
    public List<ProcessFlow> selectByFormId(Long formId);
    public void update(ProcessFlow flow);
}
