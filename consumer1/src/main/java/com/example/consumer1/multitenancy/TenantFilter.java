package com.example.consumer1.multitenancy;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class TenantFilter implements Filter {

    // @Autowired
    // private TenantIdentifierResolver tenantIdentifierResolver;

    private final TenantIdentifierResolver tenantIdentifierResolver;

    @Autowired
    public TenantFilter(TenantIdentifierResolver tenantIdentifierResolver) {
        this.tenantIdentifierResolver = tenantIdentifierResolver;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String tenant = httpRequest.getParameter("tenant");
        // String user = httpRequest.getParameter("name");


        System.out.println("Setting tenant to " + tenant + " in filter");
        if (tenant != null) {
            tenantIdentifierResolver.setCurrentTenant(tenant);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}
