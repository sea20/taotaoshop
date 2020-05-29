package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 用户管理service
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;
    @Value("${SSO_SESSION_EXPIRE}")
    private int SSO_SESSION_EXPIRE;
    /**
     *校验信息
     * @param content
     * @param type
     * @return
     */
    @Override
    public TaotaoResult checkData(String content, Integer type) {
        //创建查询条件
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //对数据进行校验:1,2,3分别代表username,phone,email
        if(type==1){
            criteria.andUsernameEqualTo(content);
        }else if(type==2){
            criteria.andPhoneEqualTo(content);
        }else {
            criteria.andEmailEqualTo(content);
        }
        List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
        if(tbUsers==null||tbUsers.size()==0){
            return TaotaoResult.ok(true);
        }
        return TaotaoResult.ok(false);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @Override
    public TaotaoResult creatUser(TbUser user) {
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //md5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        tbUserMapper.insert(user);
        return TaotaoResult.ok();
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    @Override
    public TaotaoResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        //创建查询条件
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        //查询
        List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
        //如果没有此用户名
        if(tbUsers.size()==0||tbUsers==null){
            return TaotaoResult.build(500,"用户名或密码错误");
        }
        TbUser tbUser = tbUsers.get(0);
        //对比密码
        if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(tbUser.getPassword())){
            return TaotaoResult.build(500,"用户名或密码错误");
        }
        //生成token
        String token = UUID.randomUUID().toString();
        //清掉密码（redis不存密码）
        tbUser.setPassword(null);
        //把用户信息写入redis
        jedisClient.set(REDIS_USER_SESSION_KEY+":"+token, JsonUtils.objectToJson(tbUser));
        //设置过期时间
        jedisClient.expire(REDIS_USER_SESSION_KEY+":"+token,SSO_SESSION_EXPIRE);
        //写cookie 有效期：关闭浏览器失效
        CookieUtils.setCookie(request,response,"TT_TOKEN",token);
        //返回token
        return TaotaoResult.ok(token);
    }
    //根据token在redis中查询用户信息
    @Override
    public TaotaoResult getUserByToken(String token) {
        //根据token在redis中查询用户信息
        String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
        if(StringUtils.isBlank(json)){
            return TaotaoResult.build(400,"此session已经过期，请重新登录");
        }else {
            //重置过期时间
            jedisClient.expire(REDIS_USER_SESSION_KEY+":"+token,SSO_SESSION_EXPIRE);
            return TaotaoResult.ok(JsonUtils.jsonToPojo(json,TbUser.class));
        }
    }
//退出（删除redis中的token）
    @Override
    public TaotaoResult userLogout(String token) {
        jedisClient.del(REDIS_USER_SESSION_KEY+":"+token);
        return TaotaoResult.ok();
    }
}
