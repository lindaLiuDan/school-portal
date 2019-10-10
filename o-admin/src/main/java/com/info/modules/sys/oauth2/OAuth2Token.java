package com.info.modules.sys.oauth2;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 功能描述: token
 *
 * @Params: * @param null
 * @Author: Gaosx 960889426@qq.com By User
 * @Date: 2019/5/27 18:32
 * @Return:
 */
public class OAuth2Token implements AuthenticationToken {


    private String token;

    public OAuth2Token(String token) {
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
