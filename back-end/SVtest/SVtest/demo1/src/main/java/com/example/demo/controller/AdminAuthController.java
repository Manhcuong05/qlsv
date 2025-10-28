package com.example.demo.controller;

import org.casbin.casdoor.entity.CasdoorUser;
import org.casbin.casdoor.service.CasdoorAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Controller xử lý đăng nhập / callback với Casdoor.
 * Gồm 3 endpoint chính:
 *   - /api/login          → chuyển hướng sang Casdoor
 *   - /api/auth/callback  → Casdoor redirect về với mã token
 *   - /api/register       → tuỳ chọn, chuyển sang trang đăng ký Casdoor
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AdminAuthController {

    private final CasdoorAuthService casdoorAuthService;

    @Value("${casdoor.redirect-url}")
    private String redirectUrl;              // Ví dụ: http://localhost:9089/api/auth/callback

    @Value("${app.frontend-url}")
    private String frontendUrl;              // Ví dụ: http://localhost:5173

    @Value("${app.oauth.state:qlsv-state}")
    private String oauthState;

    public AdminAuthController(CasdoorAuthService casdoorAuthService) {
        this.casdoorAuthService = casdoorAuthService;
    }

    /**
     * Bước 1: Người dùng gọi /api/login → redirect sang Casdoor để đăng nhập.
     */
    @GetMapping("/login")
    public RedirectView login() {
        try {
            // SDK Casdoor mới chỉ nhận 1 tham số redirectUrl
            String signinUrl = casdoorAuthService.getSigninUrl(redirectUrl)
                    + "&state=" + URLEncoder.encode(oauthState, StandardCharsets.UTF_8.toString());

            System.out.println("🔗 Redirecting to Casdoor: " + signinUrl);
            return new RedirectView(signinUrl);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi tạo URL đăng nhập Casdoor: " + e.getMessage());
            return new RedirectView(frontendUrl + "/error.html");
        }
    }

    /**
     * Bước 2: Casdoor redirect về /api/auth/callback?code=&state=
     * Backend đổi code → token (JWT) rồi redirect sang frontend.
     */
    @GetMapping("/auth/callback")
    public RedirectView callback(
            @RequestParam("code") String code,
            @RequestParam(value = "state", required = false) String stateFromCasdoor) {

        try {
            String state = (stateFromCasdoor != null) ? stateFromCasdoor : oauthState;

            // Đổi code sang token JWT
            String token = casdoorAuthService.getOAuthToken(code, state);

            // Parse token để lấy thông tin user (tuỳ chọn)
            CasdoorUser user = casdoorAuthService.parseJwtToken(token);
            if (user != null) {
                System.out.println("✅ Casdoor login: " + user.getName() + " <" + user.getEmail() + ">");
            }

            // Redirect về FE cùng token
            String target = frontendUrl.replaceAll("/+$", "")
                    + "/auth-callback.html#token="
                    + URLEncoder.encode(token, StandardCharsets.UTF_8.toString());

            System.out.println("🔁 Redirecting back to frontend: " + target);
            return new RedirectView(target);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi callback Casdoor: " + e.getMessage());
            String target = frontendUrl.replaceAll("/+$", "") + "/error.html";
            return new RedirectView(target);
        }
    }

    /**
     * (Tuỳ chọn) Đường dẫn đăng ký tài khoản Casdoor
     */
    @GetMapping("/register")
    public RedirectView register() {
        return new RedirectView("http://localhost:8000/signup/oauth/qlsv-app");
    }
}
