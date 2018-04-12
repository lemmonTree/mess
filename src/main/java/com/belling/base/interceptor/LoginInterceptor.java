package com.belling.base.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author admin on 2018/1/16.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private List<String> exceptUrls;

    public List<String> getExceptUrls() {
        return exceptUrls;
    }

    public void setExceptUrls(List<String> exceptUrls) {
        this.exceptUrls = exceptUrls;
    }

    //执行action之前来执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {

        String requestUri = request.getRequestURI();
        if(requestUri.startsWith(request.getContextPath())){
            requestUri = requestUri.substring(request.getContextPath().length(), requestUri.length());
        }
        //系统根目录
        if (StringUtils.equals("/",requestUri)) {
            return true;
        }
        //放行exceptUrls中配置的url
        for (String url:exceptUrls) {
            if(url.endsWith("/**")){
                if (requestUri.startsWith(url.substring(0, url.length() - 3))) {
                    return true;
                }
            } else if (requestUri.startsWith(url)) {
                return true;
            }
        }
        //其他需要登录后才能进行访问的url
        String sessionid = request.getSession().getId();
        Subject currentUser = SecurityUtils.getSubject();
//        UserSessionStatus status = SessionManager.getStatus(sessionid);

        //如果没有登录
        if(!currentUser.isAuthenticated()){
            //返回到登录页面
            return false;
        }else{
            return true;
        }
    }
}
