package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 用户管理Service
 */
@Service
public class UserServiceImpl implements UserService {
    @Value("${SSO_BASE_URL}")
    public String SSO_BASE_URL;
    @Value("${SSO_USER_TOKEN_URL}")
    private String SSO_USER_TOKEN;
    @Value("${SSO_PAGE_LOGIN}")
    public String SSO_PAGE_LOGIN;
    @Override
    public TbUser getUserByToken(String token) {
        //调用sso服务从redis中查询用户信息
        String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN + token);
        //把json转换回pojo
        TaotaoResult result = TaotaoResult.formatToPojo(json, TbUser.class);
        if(result.getStatus()==200){
            TbUser user = (TbUser) result.getData();
            return user;
        }
        return null;
    }
}
