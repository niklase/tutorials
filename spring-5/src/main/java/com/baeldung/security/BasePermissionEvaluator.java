package com.baeldung.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class BasePermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object model, Object action) {
        boolean hasPermission = false;
        if (model.getClass() == PaymentTransactionCollectionQuery.class && "query".equals(action)) {
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {


                if (grantedAuthority.getAuthority().equals("ROLE_PLAYER") &&
                        ((User) authentication.getPrincipal()).getUsername().equals(((PaymentTransactionCollectionQuery) model).getId())) {
                    hasPermission = true;
                } else if (grantedAuthority.getAuthority().equals("ROLE_SUPPORT")){
                    hasPermission = true;
                }
            }
        }
        return hasPermission;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return true;
    }
}
