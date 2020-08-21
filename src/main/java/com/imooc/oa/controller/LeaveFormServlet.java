package com.imooc.oa.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.oa.entity.LeaveForm;
import com.imooc.oa.entity.User;
import com.imooc.oa.service.LeaveFormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "LeaveFormServlet", urlPatterns = "/leave/*")
public class LeaveFormServlet extends HttpServlet {
    private LeaveFormService leaveFormService = new LeaveFormService();
    private Logger logger = LoggerFactory.getLogger(LeaveFormService.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //http://localhost/leave/create
        String uri = request.getRequestURI();
        String methodName = uri.substring(uri.lastIndexOf("/")+1);
        if(methodName.equals("create")){
            this.create(request, response);
        } else if(methodName.equals("list")){
            this.getLeaveFormList(request, response);
        } else if(methodName.equals("audit")){
            this.audit(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     * 创建请假单
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.接收各项请假单数据
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("login_user");
        String formType = request.getParameter("formType");
        String strStartTime = request.getParameter("startTime");
        String strEndTime = request.getParameter("endTime");
        String reason = request.getParameter("reason");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH");

        Map result = new HashMap<>();
        try {
            LeaveForm form = new LeaveForm();
            form.setEmployeeId(user.getEmployeeId());
            form.setFormType(Integer.parseInt(formType));
            form.setStartTime(sdf.parse(strStartTime));
            form.setEndTime(sdf.parse(strEndTime));
            form.setCreateTime(new Date());
            form.setReason(reason);
            //2.调用业务逻辑方法
            leaveFormService.createLeaveForm(form);
            result.put("code", "0");
            result.put("message", "success");
        } catch (Exception e) {
            logger.error("请假申请异常", e);
            result.put("code", e.getClass().getSimpleName());
            result.put("message", e.getMessage());
        }
        //3.组织响应数据
        String json = JSON.toJSONString(result);
        response.getWriter().println(json);
    }

    /**
     * 查询需要审核的请假单列表
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void getLeaveFormList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("login_user");
        List<Map> formList = leaveFormService.getLeaveFormList("process", user.getEmployeeId());
        Map result = new HashMap();
        result.put("code", "0");
        result.put("msg", "");
        result.put("count", formList.size());
        result.put("data", formList);
        String json = JSON.toJSONString(result);
        response.getWriter().println(json);
    }

    private void audit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String formId = request.getParameter("formId");
        String result = request.getParameter("result");
        String reason = request.getParameter("reason");
        User user = (User) request.getSession().getAttribute("login_user");
        Map mapResult = new HashMap();
        try {
            leaveFormService.audit(Long.parseLong(formId), user.getEmployeeId(), result, reason);
            mapResult.put("code", "0");
            mapResult.put("message", "");
        } catch (Exception e){
            mapResult.put("code", e.getClass().getSimpleName());
            mapResult.put("message", e.getMessage());
        }
        String json = JSON.toJSONString(mapResult);
        response.getWriter().println(json);
    }

}
