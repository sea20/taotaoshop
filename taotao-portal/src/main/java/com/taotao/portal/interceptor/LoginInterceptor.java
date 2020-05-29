package com.taotao.portal.interceptor;

import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import com.taotao.portal.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private UserServiceImpl userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断用户是否登录
        //1。从cookie中取token
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        //2。根据token取用户信息
        TbUser user = userService.getUserByToken(token);
        //没有用户信息-》跳转登陆页面，把请求url作为参数传给登录页面
        if(null==user){
            response.sendRedirect(userService.SSO_BASE_URL+userService.SSO_PAGE_LOGIN+"?redirect="+request.getRequestURL());
            return false;
        }
        //有用户信息放行并将用户信息存入request
        request.setAttribute("user",user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
