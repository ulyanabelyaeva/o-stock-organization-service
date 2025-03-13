package com.optimagrowth.organization.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.optimagrowth.organization.utils.UserContext.*;
import static com.optimagrowth.organization.utils.UserContextHolder.getContext;

@Component
public class UserContextFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        getContext().setCorrelationId(httpServletRequest.getHeader(CORRELATION_ID));
        getContext().setUserId(httpServletRequest.getHeader(USER_ID));
        getContext().setAuthToken(httpServletRequest.getHeader(AUTH_TOKEN));
        getContext().setOrgId(httpServletRequest.getHeader(ORGANIZATION_ID));
        logger.debug("UserContextFilter Correlation id: {}", getContext().getCorrelationId());
        logger.debug("UserContextFilter Token: {}", getContext().getAuthToken());
        filterChain.doFilter(httpServletRequest, servletResponse);
    }
}