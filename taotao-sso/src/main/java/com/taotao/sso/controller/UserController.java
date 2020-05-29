package com.taotao.sso.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户controller
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 校验信息
     * @param param
     * @param type
     * @param callback
     * @return
     */
    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public Object checkData(@PathVariable String param,@PathVariable Integer type,String callback){
        TaotaoResult result =null;
        //检验参数有效性
        if(StringUtils.isBlank(param)){
            result = TaotaoResult.build(400,"校验内容不能为空");
        }
        if(type==null){
            result = TaotaoResult.build(400,"校验类型不能为空");
        }
        if(type!=1 && type!=2 && type!=3){
            result = TaotaoResult.build(400,"校验类型错误");
        }
        if(result !=null){
            if(!StringUtils.isBlank(callback)){
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            }else {
                return result;
            }
        }
        //调用服务
        try {
            result = result = userService.checkData(param, type);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
        if(!StringUtils.isBlank(callback)){
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }else {
            return result;
        }
    }
    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult creatUser(TbUser user){
        TaotaoResult result = null;
        //System.out.println(user.getPassword());
        try {
            result = userService.creatUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return result;
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult userLogin(String username, String password, HttpServletResponse response, HttpServletRequest request){
        try {
            TaotaoResult result = userService.userLogin(username, password,request,response);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
    }
    @RequestMapping("/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token,String callback){
        TaotaoResult result;
        try {
            result = userService.getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
        if(StringUtils.isBlank(callback)){
            return result;
        }else{
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }

    }
    //退出
    @RequestMapping(value = "/logout/{token}")
    public String userLogin(@PathVariable  String token,String callback){
        //System.out.println("logout");
        TaotaoResult result;
        try {
            result = userService.userLogout(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(StringUtils.isBlank(callback)){
            return "redirect:http://localhost:8082";
        }else {
            return "redirect:http://localhost:8082"+"?callback="+callback;
        }
    }
}
