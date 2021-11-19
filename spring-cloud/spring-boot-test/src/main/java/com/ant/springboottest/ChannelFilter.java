package com.ant.springboottest;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
@WebFilter(urlPatterns = "/*",filterName = "channelFilter")
public class ChannelFilter implements Filter {
    public ChannelFilter() {
        System.out.println("channelFilter construct");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if(servletRequest instanceof HttpServletRequest) {
            requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);
        }
        if(requestWrapper == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(requestWrapper, servletResponse);
        }

    }
}
