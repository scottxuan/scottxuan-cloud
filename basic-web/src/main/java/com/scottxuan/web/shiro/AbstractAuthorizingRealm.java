package com.scottxuan.web.shiro;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;

/**
 * @author : scottxuan
 */
public abstract class AbstractAuthorizingRealm extends AuthorizingRealm {

    /**
     * 授权操作
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(getRoles());
        authorizationInfo.addStringPermissions(getPermissions());
        return authorizationInfo;
    }

    /**
     * 角色
     * @return
     */
    protected abstract List<String> getRoles();

    /**
     * 权限
     * @return
     */
    protected abstract List<String> getPermissions();

}
