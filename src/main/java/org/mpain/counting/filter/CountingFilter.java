package org.mpain.counting.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CountingFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, final ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpres = (HttpServletResponse) response;
        CountingResponseWrapper counter = new CountingResponseWrapper(httpres);
        HttpServletRequest httpreq = (HttpServletRequest) request;
        httpreq.setAttribute("counter", counter);
        chain.doFilter(request, counter);
        counter.flushBuffer();
    }

    @Override
    public void destroy() {
    }

}
