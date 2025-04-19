package com.wtc.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wtc.utils.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 令牌校验拦截器
 */
@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 获取请求路径
        String requestURI = request.getRequestURI();

        // 判断是否是登录请求
        if (requestURI.contains("/login")) {
            log.info("登录请求，放行...");
            return true;
        }

        // 获取 token
        String token = request.getHeader("token");

        // token 不存在，返回错误信息 401
        if (token == null || token.isEmpty()) {
            log.info("令牌为空，响应 401 状态码");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 校验 token
        try {
            JwtUtils.parseToken(token);
        } catch (Exception e) {
            log.info("令牌为非法，响应 401 状态码");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        log.info("令牌合法，放行...");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}
