package com.umc.auth.controller;

import com.umc.common.response.JsendCommonResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LogoutController {
    @GetMapping("/kakao_logout")
    public JsendCommonResponse<String> logout(
            @RequestParam("state") String state) {

        // 상태 확인 또는 처리
        System.out.println("Received state: " + state);

        return JsendCommonResponse.success("logout success");
    }
}
