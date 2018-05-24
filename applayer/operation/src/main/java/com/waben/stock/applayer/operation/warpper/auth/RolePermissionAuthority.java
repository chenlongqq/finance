package com.waben.stock.applayer.operation.warpper.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Created by yuyidi on 2017/11/16.
 * @desc
 */
public class RolePermissionAuthority implements GrantedAuthority {

    private final String role;

    private Set<String> permissions;

    public RolePermissionAuthority(String role) {
        this.role = role;
    }

    public String getAuthority() {
        return this.role;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return obj instanceof RolePermissionAuthority ? this.role.equals(((RolePermissionAuthority)obj).role) : false;
        }
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    public String toString() {
        return this.role;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
