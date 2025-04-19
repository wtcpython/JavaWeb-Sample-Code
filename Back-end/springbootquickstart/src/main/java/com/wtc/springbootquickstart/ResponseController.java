package com.wtc.springbootquickstart;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ResponseController {

    @RequestMapping("/response")
    public void response(HttpServletResponse response) throws IOException {
        // 设置响应状态码
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // 设置响应头
        response.setHeader("name", "response");

        // 设置响应体
        response.getWriter().write("<h1>hello response</h1>");
    }

    @RequestMapping("/response2")
    public ResponseEntity<String> response2() {
        return ResponseEntity
                .status(HttpServletResponse.SC_UNAUTHORIZED)
                .header("name", "response2")
                .body("<h1>hello response2</h1>");
    }
}
