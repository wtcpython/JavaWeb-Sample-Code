package com.wtc.springbootquickstart;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class RequestController {

    @RequestMapping("/request")
    public String request(HttpServletRequest request) {
        // 获取请求方式
        String method = request.getMethod();
        System.out.println("请求方式：" + method);
        // 获取请求 URL 和 URI
        String url = request.getRequestURL().toString();
        System.out.println("请求 URL 地址：" + url);
        String uri = request.getRequestURI();
        System.out.println("请求 URI 地址：" + uri);

        // 获取请求协议
        String protocol = request.getProtocol();
        System.out.println("请求协议：" + protocol);

        // 获取请求参数 - name
        String name = request.getParameter("name");
        System.out.println("name: " + name);

        // 获取请求头 - Accept
        String accept = request.getHeader("Accept");
        System.out.println("Accept: " + accept);

        return "OK";
    }
    
}
