package com.suning.jc.filter;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
/**
 * @author 13120094
 */
@Slf4j
public class AuthFilter implements Filter {

    private List<String> excludedUrlList;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        String excludedUrls = filterConfig.getInitParameter("excludedUrls");
        excludedUrlList = new ArrayList<>(Arrays.asList(excludedUrls.split(",")));
    }

    /**
     * true 有权,false 无权
     * @param uri
     * @return
     */
    private boolean check3role(String uri){
        if(uri.indexOf("/user/")!=-1){
            return false;
        }
        if(uri.indexOf("/add")!=-1||uri.indexOf("/update")!=-1||uri.indexOf("/del")!=-1||uri.indexOf("/download")!=-1||uri.indexOf("/batchAdd")!=-1){
            return false;
        }
        return true;
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String role=String.valueOf(req.getSession(true).getAttribute("role"));
        String uri = req.getRequestURI();
        // 1:管理员 2:普通用户,3:销售
        if ("1".equals(role)) {
            chain.doFilter(request, response);
            return;
        }else if ("2".equals(role)&&uri.indexOf("/user/")==-1) {
            chain.doFilter(request, response);
            return;
        }else if ("3".equals(role)&&check3role(uri)) {
            chain.doFilter(request, response);
            return;
        }
        //其他情况无权限或者未登录

        //2.被排除的url直接过滤掉
        for (String ex : excludedUrlList) {
            ex = ".*" + ex.replace("*", ".*");
            if (uri.matches(ex)) {
                chain.doFilter(request, response);
                return;
            }
        }

        //判断是否为ajax请求，默认不是
        if (!StringUtils.isEmpty(req.getHeader("x-requested-with")) && req.getHeader("x-requested-with").equals("XMLHttpRequest")) {
            ((HttpServletResponse) response).setStatus(401);
            HashMap<String,String> result=new HashMap<>();
            result.put("status","401");
            result.put("msg","登录已失效，请重新登录！");
            printLoginError(response, JSON.toJSONString(result));
        } else {
            ((HttpServletResponse) response).sendRedirect("/login");
        }
        return ;
    }
    private void printLoginError(ServletResponse response, String msg) throws IOException {
        ((HttpServletResponse) response).setHeader("noAuthentication", "true");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(msg);//"登录已失效，请刷新页面或重新登录！"
        writer.close();
        response.flushBuffer();
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
