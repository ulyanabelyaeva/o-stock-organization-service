package com.optimagrowth.organization.utils;

public class UserContextHolder {

    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();

    public static UserContext getContext() {
        UserContext context = userContext.get();
        if (context == null) {
            context = new UserContext();
            userContext.set(context);
        }
        return userContext.get();
    }
}
