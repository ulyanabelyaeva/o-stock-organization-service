package com.optimagrowth.organization.utils;

public class UserContext {

    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN = "Authorization";
    public static final String USER_ID = "tmx-user-id";
    public static final String ORGANIZATION_ID = "tmx-organization-id";

    private static final ThreadLocal<String> correlationId = new ThreadLocal<>();
    private static final ThreadLocal<String> authToken = new ThreadLocal<>();
    private static final ThreadLocal<String> userId = new ThreadLocal<>();
    private static final ThreadLocal<String> organizationId = new ThreadLocal<>();

    public String getCorrelationId() {
        return correlationId.get();
    }

    public void setCorrelationId(String cid) {
        correlationId.set(cid);
    }

    public String getAuthToken() {
        return authToken.get();
    }

    public void setAuthToken(String aToken) {
        authToken.set(aToken);
    }

    public String getUserId() {
        return userId.get();
    }

    public void setUserId(String aUser) {
        userId.set(aUser);
    }

    public String getOrgId() {
        return organizationId.get();
    }

    public void setOrgId(String aOrg) {
        organizationId.set(aOrg);
    }
}
