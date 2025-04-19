package com.wtc.filter;

import java.io.IOException;

import com.wtc.utils.CurrentHolder;
import com.wtc.utils.JwtUtils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

// @WebFilter(urlPatterns = "/*")
@Slf4j
public class TokenFilter implements Filter {

    /**
     * 拦截请求后执行
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 获取请求路径
        String requestURI = request.getRequestURI();

        // 判断是否是登录请求
        if (requestURI.contains("/login")) {
            log.info("登录请求，放行...");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 获取 token
        String token = request.getHeader("token");

        // token 不存在，返回错误信息 401
        if (token == null || token.isEmpty()) {
            log.info("令牌为空，响应 401 状态码");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 校验 token
        try {
            Claims claims = JwtUtils.parseToken(token);
            Integer empId = Integer.valueOf(claims.get("id").toString());
            CurrentHolder.setCurrentId(empId);
            log.info("当前登录员工 ID 为：{}", empId);
        } catch (Exception e) {
            log.info("令牌为非法，响应 401 状态码");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        log.info("令牌合法，放行...");
        filterChain.doFilter(servletRequest, servletResponse);

        // 删除 ThreadLocal 中的数据
        CurrentHolder.remove();
    }
}
